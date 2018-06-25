/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.hibernate.validator.constraints.URL;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.AddressType;
import org.openmrs.module.nigeriaemr.model.ndr.CommonQuestionsType;
import org.openmrs.module.nigeriaemr.model.ndr.ConditionSpecificQuestionsType;
import org.openmrs.module.nigeriaemr.model.ndr.ConditionType;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.EncountersType;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVEncounterType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVQuestionsType;
import org.openmrs.module.nigeriaemr.model.ndr.IndividualReportType;
import org.openmrs.module.nigeriaemr.model.ndr.LaboratoryReportType;
import org.openmrs.module.nigeriaemr.model.ndr.MessageHeaderType;
import org.openmrs.module.nigeriaemr.model.ndr.PatientDemographicsType;
import org.openmrs.module.nigeriaemr.model.ndr.ProgramAreaType;
import org.openmrs.module.nigeriaemr.model.ndr.RegimenType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrUtils.Validator;
import org.openmrs.module.nigeriaemr.ndrUtils.CustomErrorHandler;
import org.xml.sax.SAXException;

public class NDRConverter {
	
	private Patient patient;
	
	private FacilityType facility;
	
	private String ipName;
	
	private String ipCode;
	
	private List<Encounter> encounters;
	
	public NDRConverter(String _ipName, String _ipCode) {
		this.ipName = _ipName;
		this.ipCode = _ipCode;
	}
	
	/*private Date getLastNDRDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(Calendar.YEAR, Calendar.MARCH, 1, 0, 0, 0);
		return cal.getTime();
	}*/
	
	public Container createContainer(Patient pts, FacilityType facility) throws DatatypeConfigurationException {
		patient = pts;
		this.facility = facility;
		Date lastDate = Utils.getLastNDRDate();
		this.encounters = Context.getEncounterService().getEncountersByPatient(pts);
		
		if (this.encounters == null || this.encounters.size() == 0) {
			return null;
		}
		
		Container container = new Container();
		MessageHeaderType header = createMessageHeaderType();
		FacilityType sendingOrganization = Utils.createFacilityType(this.ipName, this.ipCode, "IP");
		header.setMessageSendingOrganization(sendingOrganization);
		
		container.setMessageHeader(header);
		container.setIndividualReport(createIndividualReportType());
		
		return container;
	}
	
	/**
	 * Create PatientDemographicsType for pts Create CommonQuestionType for pts Create
	 * HIVQuestionsType for pts Get all Pharmacy visits for patients For each Pharmacy visit create
	 * RegimenType Get all Clinical visits for patients // For each Clinical visits create
	 * HIVEncounter // Get all Lab visits for patients // For each of Lab visit create LabReportType
	 */
	private IndividualReportType createIndividualReportType() throws DatatypeConfigurationException {

		IndividualReportType individualReport = new IndividualReportType();
		individualReport.setPatientDemographics(createPatientDemographicsType());
		
		ConditionType condition = new ConditionType();
		condition.setConditionCode("86406008");

		condition.setProgramArea(createProgramArea());

		condition.setPatientAddress(createPatientAddress());

		condition.setCommonQuestions(createCommonQuestionsType());

		//set hivSpecificQuestions obs
		//TODO: add obs transfer form
		List<Obs> conditionSpecificQObs = Utils.getHIVEnrollmentObs(patient);

		HIVEncounterType hivEncounter;
		List<HIVEncounterType> hivEncounterList = new ArrayList<>();
		ClinicalDictionary clinicalDictionary = new ClinicalDictionary();

		for(Encounter enc : this.encounters){

			List<Obs> obsList = new ArrayList<>(enc.getAllObs());

			//if it a clinical obs, create the hiv encounter
			if (enc.getEncounterType().getEncounterTypeId() == Utils.Adult_Ped_Initial_Encounter_Type_Id
			|| enc.getEncounterType().getEncounterTypeId() == Utils.Care_card_Encounter_Type_Id) {
				hivEncounter = clinicalDictionary.createHIVEncounterType(patient,enc, obsList);
				hivEncounterList.add(hivEncounter);

				//add initial evaluation for HIV specific question
				if(conditionSpecificQObs == null){
					conditionSpecificQObs = new ArrayList<>();
				}
				conditionSpecificQObs.addAll(obsList);
			}

			//if it is drug pick up, create regimen tags
			if (enc.getEncounterType().getEncounterTypeId() == Utils.Pharmacy_Encounter_Type_Id) {
				condition.getRegimen().addAll(createRegimens(enc, obsList));
			}

			//if it is lab order/result encounter, create the lab order tags
			if(enc.getEncounterType().getEncounterTypeId() == Utils.Laboratory_Encounter_Type_Id) {
				condition.getLaboratoryReport().add(createLaboratoryReportTypes(enc, obsList));
			}
		}
		
		EncountersType encType = new EncountersType();
		encType.getHIVEncounter().addAll(hivEncounterList);
		condition.setEncounters(encType);


		ConditionSpecificQuestionsType hivSpecificQuestions = new ConditionSpecificQuestionsType();
		hivSpecificQuestions.setHIVQuestions(createHIVQuestionsType(conditionSpecificQObs));
		condition.setConditionSpecificQuestions(hivSpecificQuestions);

		individualReport.getCondition().add(condition);
		return individualReport;
	}
	
	private ProgramAreaType createProgramArea() {
		ProgramAreaType p = new ProgramAreaType();
		p.setProgramAreaCode("HIV");
		return p;
	}
	
	private AddressType createPatientAddress() {
		AddressType p = new AddressType();
		p.setAddressTypeCode("H");
		p.setCountryCode("NGA");
		PersonAddress pa = patient.getPersonAddress();
		p.setLGACode(pa.getCountyDistrict());
		p.setStateCode(pa.getStateProvince());
		return p;
	}
	
	private HIVQuestionsType createHIVQuestionsType(List<Obs> obs) throws DatatypeConfigurationException {
		return new NDRMainDictionary().createHIVQuestionType(patient, obs);
	}
	
	private List<RegimenType> createRegimens(Encounter enc,List<Obs> obs) throws DatatypeConfigurationException {

		List<RegimenType> regimenList = new ArrayList<>();
		PharmacyDictionary dict = new PharmacyDictionary();
		regimenList.add(dict.createRegimenType(patient, enc, obs));
		regimenList.addAll(dict.createOITypes(patient, enc, obs));
		return regimenList;
	}
	
	private LaboratoryReportType createLaboratoryReportTypes(Encounter enc, List<Obs> obs)
	        throws DatatypeConfigurationException {
		
		return new LabDictionary().createLaboratoryOrderAndResult(patient, enc, obs);
	}
	
	private PatientDemographicsType createPatientDemographicsType() throws DatatypeConfigurationException {
		
		return new NDRMainDictionary().createPatientDemographicsType(patient, facility);
	}
	
	private CommonQuestionsType createCommonQuestionsType() throws DatatypeConfigurationException {
		
		return new NDRMainDictionary().createCommonQuestionType(patient);
	}
	
	private MessageHeaderType createMessageHeaderType() throws DatatypeConfigurationException {
		MessageHeaderType header = new MessageHeaderType();
		
		Calendar cal = Calendar.getInstance();
		
		header.setMessageCreationDateTime(Utils.getXmlDateTime(cal.getTime()));
		header.setMessageStatusCode("INITIAL");
		header.setMessageSchemaVersion(new BigDecimal("1.2"));
		header.setMessageUniqueID(UUID.randomUUID().toString());
		return header;
	}
	
	private Marshaller createMarshaller(JAXBContext jaxbContext) throws JAXBException, SAXException {
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		java.net.URL xsdFilePath = Thread.currentThread().getContextClassLoader().getResource("NDR 1.2.xsd");
		
		assert xsdFilePath != null;
		//File schemeFile = new File(xsdFilePath.getFile());
		//new File();
		//"C:\\MGIC\\Project\\JavaProjects\\OpenMRS\\nigeriaemr\\omod\\src\\main\\java\\org\\openmrs\\module\\nigeriaemr\\ndrfactory\\NDR 1.2.xsd");
		
		Schema schema = sf.newSchema(xsdFilePath);
		
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		
		jaxbMarshaller.setSchema(schema);
		
		//Call Validator class to perform the validation
		jaxbMarshaller.setEventHandler(new Validator());
		return jaxbMarshaller;
	}
	
	public void writeFile(Container container, File file) throws SAXException, JAXBException, IOException {
		
		CustomErrorHandler errorHandler = new CustomErrorHandler();
		JAXBContext jaxbContext = JAXBContext.newInstance("org.openmrs.module.nigeriaemr.model.ndr");
		Marshaller jaxbMarshaller = createMarshaller(jaxbContext);
		
		javax.xml.validation.Validator validator = jaxbMarshaller.getSchema().newValidator();
		
		jaxbMarshaller.marshal(container, file);
		validator.setErrorHandler(errorHandler);
		validator.validate(new StreamSource(file));
		
	}
}
