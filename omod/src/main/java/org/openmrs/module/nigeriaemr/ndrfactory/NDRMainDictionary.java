
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.module.nigeriaemr.model.ndr.CodedSimpleType;
import org.openmrs.module.nigeriaemr.model.ndr.CommonQuestionsType;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVQuestionsType;
import org.openmrs.module.nigeriaemr.model.ndr.IdentifierType;
import org.openmrs.module.nigeriaemr.model.ndr.IdentifiersType;
import org.openmrs.module.nigeriaemr.model.ndr.PatientDemographicsType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getXmlDate;

public class NDRMainDictionary {

	public final static int Patient_Deceased_Indicator_Concept_Id =  0;
	public final static int Date_Patient_Died_Concept_Id =  1543;
	public final static int Dead_Concept_Id =  160432;
	public final static int Patient_Primary_Language_Code_Concept_Id =0;
	public final static int Patient_Education_Level_Code_Concept_Id = 0;
	public final static int Patient_Occupation_Code_Concept_Id = 0;
	public final static int Patient_Marital_Status_Code_Concept_Id = 0;
	public final static int HIV_Enrollment_Date_Concept_Id = 160555;
	public final static int Patient_Died_From_Illness_Value_Concept_Id = 0;
	public final static int Patient_Died_From_Illness_Concept_Id = 0;
	public final static int Diagnosis_Date_Concept_Id = 0;
	public final static int Estimated_Delivery_Date_Concept_Id = 0;
	public final static int Patient_Pregnancy_Status_Concept_Id = 0;
	public final static int Care_Entry_Point = 160540;
	public final static int Date_Confirmed_HIV = 160554;
	public final static int Mode_of_HIV_Test = 164947;
	public final static int Date_Transfer_In = 160534;
	public final static int Prior_ART = 165242;
	public final static int Facility_Transferred_From= 160535;
    public final static int CD4_count_at_start_of_ART = 164429;
	public final static int WHO_Clinical_Stage_ART_Start = 160553;
	public final static int Reason_Medically_Eligible =162225;
	public final static int Medically_Eligible_Date =162227;
    public final static int Weight_At_ART_Start_Concept_Id = 165582;
    public final static int Height_At_ART_Start_Concept_Id = 165581;

	private static Map<Integer, String> map = new HashMap<>();

	public NDRMainDictionary(){
		loadDictionary();
	}

	private void loadDictionary() {
		map.put(123, "PatientDeceasedIndicator");
		map.put(124, "DeceasedIndicator");
		map.put(125, "DeceasedIndicator");
		map.put(126, "DeceasedIndicator");
		map.put(122, "P"); //Pregnant
		map.put(127, "NP");
		map.put(128,"NK");
		map.put(129,"PMTCT");
	}
	
	private String getMappedValue(int conceptID) {
		return map.get(conceptID);
	}
	
	public PatientDemographicsType createPatientDemographicsType(Patient pts, FacilityType facility)
	        throws DatatypeConfigurationException {
		PatientDemographicsType demo = new PatientDemographicsType();
		
		//Identifier 4 is Pepfar ID
		PatientIdentifier pepfarid, pidHospital, pidOthers;
		pepfarid = pts.getPatientIdentifier(3);
		pidHospital = pts.getPatientIdentifier(3);
		pidOthers = pts.getPatientIdentifier(3);

		demo.setPatientIdentifier(pts.getPatientIdentifier(3).getIdentifier());
		
		IdentifierType idt;
		IdentifiersType identifiersType = new IdentifiersType();
		if (pidHospital != null) {
			idt = new IdentifierType();
			idt.setIDNumber(pidHospital.getIdentifier());
			idt.setIDTypeCode("PI");
			identifiersType.getIdentifier().add(idt);
		}
		if (pidOthers != null) {
			idt = new IdentifierType();
			idt.setIDNumber(pidOthers.getIdentifier());
			idt.setIDTypeCode("PE");
			identifiersType.getIdentifier().add(idt);
		}
		if (pepfarid != null) {
			idt = new IdentifierType();
			idt.setIDNumber(pepfarid.getIdentifier());
			idt.setIDTypeCode("PN");
			identifiersType.getIdentifier().add(idt);
		}
		demo.setOtherPatientIdentifiers(identifiersType);
		demo.setTreatmentFacility(facility);

		String gender = pts.getGender();
		if (gender.equals("M") || gender.equalsIgnoreCase("Male")) {
			demo.setPatientSexCode("M");
		} else if (gender.equals("F") || gender.equalsIgnoreCase("Female")) {
			demo.setPatientSexCode("F");
		}
		demo.setPatientDateOfBirth(getXmlDate(pts.getBirthdate()));

		String ndrCodedValue;
		//get all hiv enrollment observations
		List<Obs> enrollmentObs = Utils.getHIVEnrollmentObs(pts);

		if(enrollmentObs ==null){
			return demo;
		}

		//check for disease indicator
		Obs obs = Utils.extractObs(Patient_Deceased_Indicator_Concept_Id, enrollmentObs);
		if(obs !=null){
			if (obs.getValueCoded().getConceptId() == Dead_Concept_Id) {
				demo.setPatientDeceasedIndicator(true);
				obs = Utils.extractObs(Date_Patient_Died_Concept_Id, enrollmentObs);
				//set date
				if(obs !=null){
					demo.setPatientDeceasedDate(getXmlDate(obs.getObsDatetime()));
				}
			} else {
				demo.setPatientDeceasedIndicator(false);
			}
		}
		//check Educational level
		obs = Utils.extractObs(Patient_Education_Level_Code_Concept_Id, enrollmentObs);
		if(obs !=null){
			ndrCodedValue = getMappedValue(obs.getValueCoded().getConceptId());
			demo.setPatientEducationLevelCode(ndrCodedValue);
		}
		//check primary Concept Id
		obs = Utils.extractObs(Patient_Primary_Language_Code_Concept_Id, enrollmentObs);
		if(obs !=null){
			ndrCodedValue = getMappedValue(obs.getValueCoded().getConceptId());
			demo.setPatientPrimaryLanguageCode(ndrCodedValue);
		}
		//check Occupational Code
		obs = Utils.extractObs(Patient_Occupation_Code_Concept_Id, enrollmentObs);
		if(obs !=null){
			ndrCodedValue = getMappedValue(obs.getValueCoded().getConceptId());
			demo.setPatientOccupationCode(ndrCodedValue);
		}
		//check Marital Status Code
		obs = Utils.extractObs(Patient_Marital_Status_Code_Concept_Id, enrollmentObs);
		if(obs !=null){
			ndrCodedValue = getMappedValue(obs.getValueCoded().getConceptId());
			demo.setPatientMaritalStatusCode(ndrCodedValue);
		}
		return demo;
	}
	
	public HIVQuestionsType createHIVQuestionType(Patient patient, List<Obs> obsList) throws DatatypeConfigurationException {

		HIVQuestionsType hiv = new HIVQuestionsType();
		boolean onART = false;

		//get first regimen
		Obs FirstRegimen = Utils.getFirstRegimen(patient);

		if (FirstRegimen != null && FirstRegimen.getValueCoded() != null) {
			String regimenCode = new PharmacyDictionary().getRegimenMapValue(FirstRegimen.getValueCoded().getConceptId());
			String regimenName = FirstRegimen.getValueCoded().getName().getName();
			CodedSimpleType codedValue = new CodedSimpleType();
			if (regimenCode != null) {
				codedValue.setCode(regimenCode);
			}
			if (regimenName != null) {
				codedValue.setCodeDescTxt(regimenName);
			}
			hiv.setFirstARTRegimen(codedValue);
		}

		if(FirstRegimen !=null) {
			onART = true;
			hiv.setARTStartDate(getXmlDate(FirstRegimen.getObsDatetime()));
		}

		int conceptID;
		double value_numeric;
		int value_coded;
		String value_text;
		Date value_datetime;
		FacilityType ft;

		if(obsList ==null || obsList.size() ==0){
			return  hiv;
		}

		for (Obs obs : obsList) {
			conceptID = obs.getConcept().getConceptId();
			switch (conceptID) {
				case HIV_Enrollment_Date_Concept_Id:
					value_datetime = obs.getValueDatetime();
					if (value_datetime != null) {
						hiv.setEnrolledInHIVCareDate(getXmlDate(obs.getValueDatetime()));
					}
					break;
				case Care_Entry_Point:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setCareEntryPoint(getMappedValue(value_coded));
					break;
				case Date_Confirmed_HIV:
					value_datetime = obs.getValueDate();
					if (value_datetime != null) {
						hiv.setFirstConfirmedHIVTestDate(getXmlDate(value_datetime));
					}
					break;
				case Mode_of_HIV_Test:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setFirstHIVTestMode(getMappedValue(value_coded));
					break;
				case 7778238:
					value_text = obs.getValueText();
					hiv.setWhereFirstHIVTest(value_text);
					break;
				case Prior_ART:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setPriorArt(getMappedValue(value_coded));
					break;
				case Medically_Eligible_Date:
					value_datetime = obs.getValueDatetime();
					hiv.setMedicallyEligibleDate(getXmlDate(value_datetime));
					break;
				case Reason_Medically_Eligible:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setReasonMedicallyEligible(getMappedValue(value_coded));
					break;
				case 7777862:
					value_datetime = obs.getValueDate();
					hiv.setInitialAdherenceCounselingCompletedDate(getXmlDate(value_datetime));
					break;
				case Date_Transfer_In: //TODO: check transfer form in case it is not filled in the hiv enrollment form but documented in the transfer form
					value_datetime = obs.getValueDate();
					if (value_datetime != null) {
						hiv.setTransferredInDate(getXmlDate(value_datetime));
					}
					break;
				case Facility_Transferred_From: //TODO: look up the facility and extract the DATIM Code
					value_text = obs.getValueText();
					ft = Utils.createFacilityType(value_text, value_text, "FAC");
					hiv.setTransferredInFrom(ft);
					break;
				case WHO_Clinical_Stage_ART_Start:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setWHOClinicalStageARTStart(getMappedValue(value_coded));
					break;
				case Weight_At_ART_Start_Concept_Id:
					value_numeric = obs.getValueNumeric();
					hiv.setWeightAtARTStart((int) value_numeric);
					break;
				case Height_At_ART_Start_Concept_Id:
					value_numeric = obs.getValueNumeric();
					hiv.setChildHeightAtARTStart((int) value_numeric);
					break;
				case 7778530:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setFunctionalStatusStartART(getMappedValue(value_coded));
					break;
				case CD4_count_at_start_of_ART:
					value_numeric = obs.getValueNumeric();
					hiv.setCD4AtStartOfART(String.valueOf(value_numeric));
					break;
				case 977: //TODO: transfer out status is outstanding
					value_coded = obs.getValueCoded().getConceptId();
					if (value_coded == 211) {
						hiv.setPatientTransferredOut(true);
						if (onART) {
							hiv.setTransferredOutStatus("A");
						}
					} else if (value_coded == 975) {
						hiv.setPatientHasDied(true);
						if (onART) {
							hiv.setStatusAtDeath("A");
						}
					}
					break;
				case 980:
					value_text = obs.getValueText();
					hiv.setSourceOfDeathInformation(value_text);
					break;
				
				default:
					break;
			}
		}
		return hiv;
	}
	
	public CommonQuestionsType createCommonQuestionType(Patient pts) throws DatatypeConfigurationException {

		CommonQuestionsType common = new CommonQuestionsType();

		/*  Assuming Hospital No is 3*/
		common.setHospitalNumber(pts.getPatientIdentifier(3).getIdentifier());

		List<Obs> enrollmentObs = Utils.getHIVEnrollmentObs(pts);
		if(enrollmentObs !=null){
			Obs enrolmentDateObs = Utils.extractObs(HIV_Enrollment_Date_Concept_Id, enrollmentObs);
			if (enrolmentDateObs != null) {
				common.setDateOfFirstReport(getXmlDate(enrolmentDateObs.getValueDatetime()));
			}
		}

		Encounter lastEncounterDate = Utils.getLastEncounter(pts);
		if (lastEncounterDate != null) {
			common.setDateOfLastReport(getXmlDate(lastEncounterDate.getEncounterDatetime()));
		}

		List<Obs> hivEnrollmentObs = Utils.getHIVEnrollmentObs(pts);
		if(hivEnrollmentObs == null){
			return common;
		}

		Obs obs;

		//set the patient pregnancy status code
		if (pts.getGender().equalsIgnoreCase("F")) {
			obs = Utils.extractObs(Patient_Pregnancy_Status_Concept_Id, hivEnrollmentObs);
			if(obs !=null && obs.getValueCoded() !=null){
				String pregnancyStatus = getMappedValue(obs.getValueCoded().getConceptId());
				common.setPatientPregnancyStatusCode(pregnancyStatus);
			}

			//set estimated delivery date concept id
			obs = Utils.extractObs(Estimated_Delivery_Date_Concept_Id, hivEnrollmentObs);
			if (obs != null) {
				common.setEstimatedDeliveryDate(getXmlDate(obs.getObsDatetime()));
			}
		}

		//set diagnosis date
		obs = Utils.extractObs(Diagnosis_Date_Concept_Id, hivEnrollmentObs);
		if (obs != null) {
			common.setDiagnosisDate(getXmlDate(obs.getValueDatetime()));
		}

		//set Patient Die From This Illness tag
		obs = Utils.extractObs(Patient_Died_From_Illness_Concept_Id, hivEnrollmentObs);
		if (obs != null && obs.getValueCoded().getConceptId() == Patient_Died_From_Illness_Value_Concept_Id) {
			common.setPatientDieFromThisIllness(true);
		}

		common.setPatientDieFromThisIllness(pts.isDead());
		common.setPatientAge(pts.getAge());

		return common;
	}
	
	/*static HIVQuestionsType createHIVQuestionType(Obs firstRegimenObs, Date ARTStartDate, Date EnrollmentDate, List<Obs> obs) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}*/
	
}
