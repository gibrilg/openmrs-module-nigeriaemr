/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.Visit;
import org.openmrs.api.EncounterService;
import org.openmrs.api.VisitService;
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
import org.xml.sax.SAXParseException;

public class NDRConverter {
	
	Patient patient;
	
	FacilityType facility;
	
	Date creationDate;
	
	String statusCode;
	
	String ipName;
	
	String ipCode;
	
	public NDRConverter(Date _creationDate, String _statusCode, String _ipName, String _ipCode) {
		this.creationDate = _creationDate;
		this.statusCode = _statusCode;
		this.ipName = _ipName;
		this.ipCode = _ipCode;
	}
	
	private List<Obs> getObservations(Set<Integer> encounterTypeIds) {
		
		List<Obs> Obs = new ArrayList<Obs>();
		
		List<Encounter> encounterList;
		List<Visit> visitList;
		
		//demographyObs
		EncounterService encSrv = Context.getEncounterService();
		encounterList = encSrv.getEncountersByPatient(patient);
		
		for (Encounter enc : encounterList) {
			if (encounterTypeIds.contains(enc.getEncounterType().getEncounterTypeId())) {
				Obs.addAll(new ArrayList<Obs>(enc.getAllObs()));
			}
		}
		
		VisitService visitSrv = Context.getVisitService();
		visitList = visitSrv.getVisitsByPatient(patient);
		for (Visit vst : visitList) {
			
		}
		
		return Obs;
	}
	
	public Container createContainer(Patient pts, FacilityType facility) throws DatatypeConfigurationException {
		patient = pts;
		Container container = new Container();
		MessageHeaderType header = createMessageHeaderType();
		FacilityType sendingOrganization = Utils.createFacilityType(this.ipName, this.ipCode, "IP");
		header.setMessageSendingOrganization(sendingOrganization);
		
		container.setMessageHeader(header);
		container.setIndividualReport(createIndividualReportType());
		return container;
	}
	
	private IndividualReportType createIndividualReportType() throws DatatypeConfigurationException {
		// Create PatientDemographicsType for pts
		// Create CommonQuestionType for pts
		// Create HIVQuestionsType for pts
		// Get all Pharmacy visits for patients
		// For each Pharmacy visit create RegimenType
		// Get all Clinical visits for patients
		// For each Clinical visits create HIVEncounter
		// Get all Lab visits for patients
		// For each of Lab visit create LabReportType
		
		Set<Integer> demographyFormIds = new HashSet<Integer>();
		Set<Integer> labFormIds = new HashSet<Integer>();
		Set<Integer> clinicalFormIds = new HashSet<Integer>();
		Set<Integer> pharmFormIds = new HashSet<Integer>();
		Set<Integer> conditionSpecifiQuestionFormIds = new HashSet<Integer>();
		
		List<Obs> pharmObs = getObservations(pharmFormIds);
		List<Obs> labObs = getObservations(labFormIds);
		List<Obs> clinicalObs = getObservations(clinicalFormIds);
		List<Obs> conditionSpecifiQObs = getObservations(conditionSpecifiQuestionFormIds);
		
		IndividualReportType individualReport = new IndividualReportType();
		
		List<Obs> demographyObs = getObservations(demographyFormIds);
		individualReport.setPatientDemographics(createPatientDemographicsType(demographyObs));
		
		ConditionType condition = new ConditionType();
		condition.setConditionCode("86406008");
		condition.setProgramArea(createProgramArea());
		condition.setPatientAddress(createPatientAddress());
		condition.setCommonQuestions(createCommonQuestionsType(demographyObs));
		
		ConditionSpecificQuestionsType specificQ = new ConditionSpecificQuestionsType();
		specificQ.setHIVQuestions(createHIVQuestionsType(conditionSpecifiQObs));
		condition.setConditionSpecificQuestions(specificQ);
		
		//create HIV ENCounter
		Date artStartDate = new Date();
		VisitService vs = Context.getVisitService();
		
		List<Visit> visitList = vs.getVisitsByPatient(patient);
		List<Obs> pharmacyObs = null;
		List<Obs> clinicalObsList = new ArrayList<Obs>();
		String visitID = "";
		Set<Encounter> encounterSet = null;
		HIVEncounterType hivEncounnter = null;
		List<HIVEncounterType> hivEncounterList = new ArrayList<HIVEncounterType>();
		for (Visit vst : visitList) {
			encounterSet = vst.getEncounters();
			for (Encounter enc : encounterSet) {
				if (enc.getEncounterType().getEncounterTypeId() == 7) {
					clinicalObsList.addAll(enc.getAllObs());
				}
			}
			visitID = patient.getPatientIdentifier(3).getIdentifier() + String.valueOf(vst.getStartDatetime());
			hivEncounnter = ClinicalDictionary.createHIVEncounterType(vst.getStartDatetime(), visitID, artStartDate,
			    clinicalObsList);
			hivEncounterList.add(hivEncounnter);
		}
		
		EncountersType encType = new EncountersType();
		encType.getHIVEncounter().addAll(hivEncounterList);
		
		condition.setEncounters(encType);

		condition.getRegimen().addAll(createAllRegimens(clinicalObs));

		condition.getLaboratoryReport().addAll(createLaboratoryReportTypes(clinicalObs));

		//condition.getImmunization().add(null);
		
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
		Obs firstRegimenObs = null;
		Date ARTStartDate = new Date();
		Date EnrollmentDate = new Date();
		return NDRMainDictionary.createHIVQuestionType(firstRegimenObs, ARTStartDate, EnrollmentDate, false, obs);
	}
	
	private List<RegimenType> createAllRegimens(List<Obs> obs) {
		
		return new ArrayList<RegimenType>();
	}
	
	private List<LaboratoryReportType> createLaboratoryReportTypes(List<Obs> obs) {
		
		return new ArrayList<LaboratoryReportType>();
	}
	
	private PatientDemographicsType createPatientDemographicsType(List<Obs> demographyObs)
	        throws DatatypeConfigurationException {
		
		return NDRMainDictionary.createPatientDemographicsType(patient, demographyObs, facility);
	}
	
	private CommonQuestionsType createCommonQuestionsType(List<Obs> obs) throws DatatypeConfigurationException {
		Date firstVistDate = Context.getVisitService().getAllVisits().get(0).getStartDatetime();
		Integer totalVisit = Context.getVisitService().getAllVisits().size();
		Date lastVisitDate = Context.getVisitService().getAllVisits().get(totalVisit - 1).getStartDatetime();
		
		return NDRMainDictionary.createCommonQuestionType(patient, obs, firstVistDate, lastVisitDate);
	}
	
	private MessageHeaderType createMessageHeaderType() throws DatatypeConfigurationException {
		MessageHeaderType header = new MessageHeaderType();
		header.setMessageCreationDateTime(Utils.getXmlDate(creationDate));
		header.setMessageStatusCode(statusCode);
		header.setMessageSchemaVersion(new BigDecimal("1.2"));
		header.setMessageUniqueID(UUID.randomUUID().toString());
		return header;
	}
	
	private Marshaller createMarshaller(JAXBContext jaxbContext) throws JAXBException, SAXException {
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File schemeFile = new File(
		        "C:\\MGIC\\Project\\JavaProjects\\OpenMRS\\nigeriaemr\\omod\\src\\main\\java\\org\\openmrs\\module\\nigeriaemr\\ndrfactory\\NDR 1.2.xsd");
		Schema schema = sf.newSchema(schemeFile);
		
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
