package org.openmrs.module.nigeriaemr.ndrfactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

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
	
	public Container createContainer(Patient pts, FacilityType facility) throws DatatypeConfigurationException {
		patient = pts;
		this.facility = facility;
		this.encounters = new ArrayList<>();
		Date lastDate = Utils.getLastNDRDate();

		List<Encounter> encs = Context.getEncounterService().getEncountersByPatient(pts);

		//get all encounters that happened after the last run date
		if(lastDate !=null){
			for(Encounter enc : encs){
				if(enc.getEncounterDatetime().after(lastDate)){
					this.encounters.add(enc);
				}
			}
		}
		else{ // assume this is the first time running this report
			this.encounters.addAll(encs);
		}

		if (this.encounters == null || this.encounters.size() == 0) {
			return null;
		}

		Container container = new Container();
		MessageHeaderType header = createMessageHeaderType();
		FacilityType sendingOrganization = Utils.createFacilityType(this.ipName, this.ipCode, "IP");
		header.setMessageSendingOrganization(sendingOrganization);

		container.setMessageHeader(header);
		IndividualReportType individualReportType =  createIndividualReportType();
		if(individualReportType == null){
			return  null;
		}

		container.setIndividualReport(individualReportType);
		return container;
	}
	
	private IndividualReportType createIndividualReportType() throws DatatypeConfigurationException {
		
		try {
			
			//create patient data
			PatientDemographicsType patientDemography = createPatientDemographicsType();
			if (patientDemography == null) { //return null if no valid patient data exist
				return null;
			}
			
			//create hiv condition type with code "86406008"
			ConditionType condition = createHIVCondition();
			if (condition == null) {
				return null; //return null if the condition parameters are empty
			}
			
			IndividualReportType individualReport = new IndividualReportType();
			individualReport.setPatientDemographics(patientDemography);
			individualReport.getCondition().add(condition);
			
			return individualReport;
			
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Create PatientDemographicsType for pts Create CommonQuestionType for pts Create
	 * HIVQuestionsType for pts Get all Pharmacy visits for patients For each Pharmacy visit create
	 * RegimenType Get all Clinical visits for patients // For each Clinical visits create
	 * HIVEncounter // Get all Lab visits for patients // For each of Lab visit create LabReportType
	 */
	private ConditionType createHIVCondition() throws DatatypeConfigurationException {

		try{

			ConditionType condition = new ConditionType();
			condition.setConditionCode("86406008");
			condition.setProgramArea(createProgramArea());
			condition.setPatientAddress(createPatientAddress());
			condition.setCommonQuestions(createCommonQuestionsType());

			//set hivSpecificQuestions obs
			//TODO: add obs transfer form
			List<Obs> conditionSpecificQObs = Utils.getHIVEnrollmentObs(patient);

			EncountersType encType = new EncountersType();
			condition.setEncounters(encType); //the encType will be populated later

			HIVEncounterType hivEncounter;

			/*PregnancyEncounterType pregnancyEncounter;
			HIVTestingEncounterType hivTestingEncounter;
			PMTCTDictionary pmtctDictionary = new PMTCTDictionary();
			HTSDictionary htsDictionary = new HTSDictionary();*/

			ClinicalDictionary clinicalDictionary = new ClinicalDictionary();
			LabDictionary labDictionary = new LabDictionary();

			for(Encounter enc : this.encounters){

				List<Obs> obsList = new ArrayList<>(enc.getAllObs());

				// create the hiv encounter from Adult initials and care card
				if (enc.getEncounterType().getEncounterTypeId() == Utils.Adult_Ped_Initial_Encounter_Type_Id
						|| enc.getEncounterType().getEncounterTypeId() == Utils.Care_card_Encounter_Type_Id) {

					hivEncounter = clinicalDictionary.createHIVEncounterType(patient,enc, obsList);
					if(hivEncounter !=null){
						encType.getHIVEncounter().add(hivEncounter);
					}

					if(conditionSpecificQObs == null){
						conditionSpecificQObs = new ArrayList<>();
					}
					//collect obs from initial evaluation for HIV specific question
					conditionSpecificQObs.addAll(obsList);
				}

				//if it is drug pick up, create regimen tags
				if (enc.getEncounterType().getEncounterTypeId() == Utils.Pharmacy_Encounter_Type_Id) {
					List<RegimenType> regimenData = createRegimens(enc, obsList);
					condition.getRegimen().addAll(regimenData);
				}

				//if it is lab order/result encounter, create the lab order tags
				if(enc.getEncounterType().getEncounterTypeId() == Utils.Laboratory_Encounter_Type_Id) {
					LaboratoryReportType laboratoryReport = labDictionary.createLaboratoryOrderAndResult(patient, enc, obsList);
					//createLaboratoryReportTypes(enc, obsList);
					condition.getLaboratoryReport().add(laboratoryReport);
				}

				//add pregnancy records
				/*if(enc.getEncounterType().getEncounterTypeId() == 0){ // pregnancy registration type
					 pregnancyEncounter =  pmtctDictionary.createPregnancyRecord(patient, enc);
					if(pregnancyEncounter !=null){
						condition.getPregnancyRecord().add(pregnancyEncounter);
					}
				}

				if(enc.getEncounterType().getEncounterTypeId() == Utils.Client_Intake_Form_Encounter_Type_Id){ //change to HTC encounter type
					hivTestingEncounter = htsDictionary.createHIVTestingEncounter(patient, enc);
					if(hivTestingEncounter !=null){
						condition.getHIVTestRecords().add(hivTestingEncounter);
					}
				}*/
			}

			//create the HIV Questions from the conditions specific obs
			ConditionSpecificQuestionsType hivSpecificQuestions = new ConditionSpecificQuestionsType();
			HIVQuestionsType hivQuestionsType = new NDRMainDictionary().createHIVQuestionType(patient, conditionSpecificQObs);
			hivSpecificQuestions.setHIVQuestions(hivQuestionsType);
			//createHIVQuestionsType(conditionSpecificQObs));
			condition.setConditionSpecificQuestions(hivSpecificQuestions);


			return condition;

		}catch (Exception ex ){
			System.out.println(ex.getMessage());
			throw ex;
		}
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
		if (pa != null) {
			p.setLGACode(pa.getCountyDistrict());
			p.setStateCode(pa.getStateProvince());
		}
		return p;
	}
	

	
	private List<RegimenType> createRegimens(Encounter enc,List<Obs> obs) throws DatatypeConfigurationException {

		List<RegimenType> regimenList = new ArrayList<>();
		PharmacyDictionary dict = new PharmacyDictionary();
		regimenList.add(dict.createRegimenType(patient, enc, obs));
		regimenList.addAll(dict.createOITypes(patient, enc, obs));
		return regimenList;
	}
	
	/*

	private HIVQuestionsType createHIVQuestionsType(List<Obs> obs) throws DatatypeConfigurationException {
		return new NDRMainDictionary().createHIVQuestionType(patient, obs);
	}

	private LaboratoryReportType createLaboratoryReportTypes(Encounter enc, List<Obs> obs)
	        throws DatatypeConfigurationException {
		return new LabDictionary().createLaboratoryOrderAndResult(patient, enc, obs);
	}*/
	
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
		
		java.net.URL xsdFilePath = Thread.currentThread().getContextClassLoader().getResource("NDR 1.3.xsd");
		
		assert xsdFilePath != null;
		
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
		
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance("org.openmrs.module.nigeriaemr.model.ndr");
			Marshaller jaxbMarshaller = createMarshaller(jaxbContext);
			
			javax.xml.validation.Validator validator = jaxbMarshaller.getSchema().newValidator();
			jaxbMarshaller.marshal(container, file);
			validator.setErrorHandler(errorHandler);
			validator.validate(new StreamSource(file));
		}
		catch (Exception ex) {
			System.out.println("File " + file.getName() + " throw an exception \n" + ex.getMessage());
			throw ex;
		}
	}
}
