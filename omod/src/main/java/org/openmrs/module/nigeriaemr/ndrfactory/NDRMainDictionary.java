/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang3.StringUtils;
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

/**
 * @author The Bright
 */
public class NDRMainDictionary {
	
	private static Map<Integer, String> map = new HashMap<Integer, String>();
	
	public static void loadDictionary() {
		map.put(123, "PatientDeceasedIndicator");
		map.put(124, "DeceasedIndicator");
		map.put(125, "DeceasedIndicator");
		map.put(126, "DeceasedIndicator");
	}
	
	public static String getMappedValue(int conceptID) {
		return map.get(conceptID);
	}
	
	public static PatientDemographicsType createPatientDemographicsType(Patient pts, List<Obs> obsList, FacilityType facility)
	        throws DatatypeConfigurationException {
		PatientDemographicsType demo = new PatientDemographicsType();
		
		//Identifier 4 is Pepfar ID
		PatientIdentifier pidPepfar, pidHospital, pidOthers;
		pidPepfar = pts.getPatientIdentifier(3);
		pidHospital = pts.getPatientIdentifier(3);
		pidOthers = pts.getPatientIdentifier(3);
		
		//Identifier 4 is Pepfar ID
		//pidPepfar=pts.getPatientIdentifier(4);
		demo.setPatientIdentifier(pts.getPatientIdentifier(3).getIdentifier());
		
		IdentifierType idt = null;
		IdentifiersType idtss = new IdentifiersType();
		//String hospID = pts.getPatientIdentifier(3).getIdentifier();
		//String otherID = pts.getPatientIdentifier(2).getIdentifier();
		//String pepfarID = pts.getPatientIdentifier(4).getIdentifier();
		if (pidHospital != null) {//StringUtils.isNotEmpty(hospID)) {
			idt = new IdentifierType();
			idt.setIDNumber(pidHospital.getIdentifier());
			idt.setIDTypeCode("PI");
			idtss.getIdentifier().add(idt);
		}
		if (pidOthers != null) {//StringUtils.isNotEmpty(otherID)) {
			idt = new IdentifierType();
			idt.setIDNumber(pidOthers.getIdentifier());
			idt.setIDTypeCode("PE");
			idtss.getIdentifier().add(idt);
		}
		if (pidPepfar != null) {//StringUtils.isNotBlank(pepfarID)) {
			idt = new IdentifierType();
			idt.setIDNumber(pidPepfar.getIdentifier());//pepfarID.toUpperCase());
			idt.setIDTypeCode("PN");
			idtss.getIdentifier().add(idt);
		}
		demo.setOtherPatientIdentifiers(idtss);
		
		FacilityType treatmentFacility = facility;
		demo.setTreatmentFacility(treatmentFacility);
		String gender = pts.getGender();
		if (gender.equals("M") || gender.equalsIgnoreCase("Male")) {
			demo.setPatientSexCode("M");
		} else if (gender.equals("F") || gender.equalsIgnoreCase("Female")) {
			demo.setPatientSexCode("F");
		}
		demo.setPatientDateOfBirth(getXmlDate(pts.getBirthdate()));
		
		int conceptID = 0;
		int value_coded = 0;
		String value_text = "";
		double value_numeric = 0.0;
		
		for (Obs obs : obsList) {
			conceptID = obs.getConcept().getConceptId();
			switch (conceptID) {
				case 977: // Termination Concept ID
					value_coded = obs.getValueCoded().getConceptId();
					if (value_coded == 975) {
						demo.setPatientDeceasedIndicator(true);
						demo.setPatientDeceasedDate(getXmlDate(obs.getObsDatetime()));
					} else {
						demo.setPatientDeceasedIndicator(false);
					}
					break;
				case 1083: // Primary Language Concept ID
					value_coded = obs.getValueCoded().getConceptId();
					demo.setPatientPrimaryLanguageCode(getMappedValue(value_coded));
					break;
				case 1079: // Educational level
					value_coded = obs.getValueCoded().getConceptId();
					if (value_coded != 789) {
						demo.setPatientEducationLevelCode(getMappedValue(value_coded));
					}
					break;
				case 915: // Occupational Code
					value_coded = obs.getValueCoded().getConceptId();
					demo.setPatientOccupationCode(getMappedValue(value_coded));
					break;
				case 352: // Marrital status
					value_coded = obs.getValueCoded().getConceptId();
					demo.setPatientMaritalStatusCode(getMappedValue(value_coded));
					break;
			}
		}
		return demo;
	}
	
	public static HIVQuestionsType createHIVQuestionType(Obs obsFirstRegimen, Date artStartDate, Date enrollmentDt,
	        boolean onART, List<Obs> obsList) throws DatatypeConfigurationException {
		HIVQuestionsType hiv = new HIVQuestionsType();
		if (enrollmentDt != null) {
			hiv.setEnrolledInHIVCareDate(getXmlDate(enrollmentDt));
		}
		String regimenCode = null;
		String regimenName = null;
		if (obsFirstRegimen != null) {
			regimenName = obsFirstRegimen.getValueCoded().getName().getName();
			regimenCode = PharmacyDictionary.getRegimenMapValue(obsFirstRegimen.getValueCoded().getConceptId());
		}
		CodedSimpleType cst1 = null;
		int conceptID = 0;
		int form_id = 0;
		double value_numeric = 0.0;
		int value_coded = 0;
		String value_text = "";
		Date value_datetime = null;
		FacilityType ft = null;
		if (regimenCode != null && regimenName != null) {
			cst1 = new CodedSimpleType();
			cst1.setCode(regimenCode);
			cst1.setCodeDescTxt(regimenName);
			hiv.setFirstARTRegimen(cst1);
		}
		if (artStartDate != null) {
			hiv.setARTStartDate(getXmlDate(artStartDate));
		} else if (regimenCode != null && obsFirstRegimen != null) {
			hiv.setARTStartDate(getXmlDate(obsFirstRegimen.getObsDatetime()));
		}
		for (Obs obs : obsList) {
			conceptID = obs.getConcept().getConceptId();
			switch (conceptID) {
				case 1052:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setCareEntryPoint(NDRMainDictionary.getMappedValue(value_coded));
					break;
				case 859:
					value_datetime = obs.getValueDate();
					if (value_datetime != null) {
						hiv.setFirstConfirmedHIVTestDate(getXmlDate(value_datetime));
					}
					break;
				case 7778053:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setFirstHIVTestMode(NDRMainDictionary.getMappedValue(value_coded));
					break;
				case 7778238:
					value_text = obs.getValueText();
					hiv.setWhereFirstHIVTest(value_text);
					break;
				case 7777768:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setPriorArt(NDRMainDictionary.getMappedValue(value_coded));
					break;
				case 1703:
					value_datetime = obs.getValueDate();
					if (value_datetime != null) {
						hiv.setMedicallyEligibleDate(getXmlDate(value_datetime));
					}
					break;
				case 1731:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setReasonMedicallyEligible(NDRMainDictionary.getMappedValue(value_coded));
					break;
				case 7777862:
					value_datetime = obs.getValueDate();
					hiv.setInitialAdherenceCounselingCompletedDate(getXmlDate(value_datetime));
					break;
				case 978:
					form_id = obs.getEncounter().getForm().getFormId();
					if (form_id == 1) {
						value_datetime = obs.getValueDate();
						if (value_datetime != null) {
							hiv.setTransferredInDate(getXmlDate(value_datetime));
						}
					}
					break;
				case 1732:
					value_text = obs.getValueText();
					FacilityType ft2 = Utils.createFacilityType(value_text, value_text, "FAC");
					hiv.setTransferredInFrom(ft2);
					break;
				case 7778529:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setWHOClinicalStageARTStart(NDRMainDictionary.getMappedValue(value_coded));
					break;
				case 1734:
					value_numeric = obs.getValueNumeric();
					hiv.setWeightAtARTStart((int) value_numeric);
					break;
				case 1735:
					value_numeric = obs.getValueNumeric();
					hiv.setChildHeightAtARTStart((int) value_numeric);
					break;
				case 7778530:
					value_coded = obs.getValueCoded().getConceptId();
					hiv.setFunctionalStatusStartART(NDRMainDictionary.getMappedValue(value_coded));
					break;
				case 1733:
					value_numeric = obs.getValueNumeric();
					hiv.setCD4AtStartOfART(String.valueOf(value_numeric));
					break;
				case 977:
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
	
	public static CommonQuestionsType createCommonQuestionType(Patient pts, List<Obs> obsList, Date firstVisitDate,
	        Date lastVisitDate) throws DatatypeConfigurationException {
		CommonQuestionsType common = new CommonQuestionsType();
		common.setPatientDieFromThisIllness(pts.isDead());
		common.setPatientAge(pts.getAge());
		/*  Assuming Hospital No is 3*/
		common.setHospitalNumber(pts.getPatientIdentifier(3).getIdentifier());
		if (firstVisitDate != null) {
			common.setDateOfFirstReport(getXmlDate(firstVisitDate));
		}
		if (lastVisitDate != null) {
			common.setDateOfLastReport(getXmlDate(lastVisitDate));
		}
		int value_coded = 0;
		Date value_datetime = null;
		int conceptID = 0;
		String gender = pts.getGender();
		for (Obs obs : obsList) {
			conceptID = obs.getConcept().getConceptId();
			switch (conceptID) {
				case 7777871:
					value_coded = obs.getValueCoded().getConceptId();
					if (gender.equals("F")) {
						switch (value_coded) {
							case 47:
								common.setPatientPregnancyStatusCode("P");
								break;
							case 7777870:
								common.setPatientPregnancyStatusCode("NP");
								break;
							case 13:
								common.setPatientPregnancyStatusCode("NK");
								break;
							case 1259:
								common.setPatientPregnancyStatusCode("PMTCT");
								break;
							default:
								break;
						}
					}
					break;
				case 577:
					value_datetime = obs.getValueDate();
					if (gender.equals("F")) {
						common.setEstimatedDeliveryDate(getXmlDate(value_datetime));
					}
					break;
				case 859:
					value_datetime = obs.getValueDate();
					common.setDiagnosisDate(getXmlDate(value_datetime));
					break;
				case 977:
					value_coded = obs.getValueCoded().getConceptId();
					if (value_coded == 975) {
						common.setPatientDieFromThisIllness(true);
					}
					break;
				default:
					break;
			}
		}
		return common;
	}
	
	static HIVQuestionsType createHIVQuestionType(Obs firstRegimenObs, Date ARTStartDate, Date EnrollmentDate, List<Obs> obs) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
