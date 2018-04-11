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
import org.openmrs.module.nigeriaemr.model.ndr.CodedSimpleType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVEncounterType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

public class ClinicalDictionary {
	
	private static Map<Integer, String> map = new HashMap<Integer, String>();
	
	public static void loadDictionary() {
		//Map OpenMRS concepts to corresponding NDR values
		map.put(123, "12");
	}
	
	public static String getMappedValue(int conceptID) {
		return map.get(conceptID);
	}
	
	public static HIVEncounterType createHIVEncounterType(Date visitDate, String visitID, Date artStartDate,
	        List<Obs> obsList) throws DatatypeConfigurationException {
		HIVEncounterType hivEncounter = new HIVEncounterType();
		hivEncounter.setVisitDate(Utils.getXmlDate(visitDate));
		hivEncounter.setVisitID(visitID);
		
		int monthOnART = 0;
		if (artStartDate != null && (visitDate.after(artStartDate) || visitDate.equals(artStartDate))) {
			monthOnART = visitDate.getMonth() - artStartDate.getMonth(); // Months.monthsBetween(d1, d2).getMonths();
			hivEncounter.setDurationOnArt(monthOnART);
		}
		int conceptID = 0;
		int value_numeric = 0;
		int value_coded = 0;
		Date value_datetime = null;
		String SystolicBloodPressure = "";
		String DistolicBloodPressure = "";
		CodedSimpleType cst = null;
		for (Obs obs : obsList) {
			conceptID = obs.getConcept().getConceptId();
			switch (conceptID) {
				case 5090: // CEIL concept ID for Height
					value_numeric = (int) Math.round(obs.getValueNumeric());
					hivEncounter.setChildHeight(value_numeric);
					break;
				case 5089:
					value_numeric = (int) Math.round(obs.getValueNumeric());
					hivEncounter.setWeight(value_numeric);
					break;
				case 5085: // Systolic
					SystolicBloodPressure = String.valueOf((int) Math.round(obs.getValueNumeric()));
					//hivEncounter.setBloodPressure(visitID);
					break;
				case 5086: //diastolic
					DistolicBloodPressure = String.valueOf((int) Math.round(obs.getValueNumeric()));
					break;
				case 5: // EDDandPMTCTLink
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setEDDandPMTCTLink(getMappedValue(value_coded));
					break;
				case 6:// PatientFamilyPlanningCode
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setPatientFamilyPlanningCode(getMappedValue(value_coded));
					break;
				case 7:// FamilyPlanningMethod
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setPatientFamilyPlanningMethodCode(getMappedValue(value_coded));
					break;
				case 8:// Functional Status
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setFunctionalStatus(getMappedValue(value_coded));
					break;
				case 9:// WHOClinicalStage
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setWHOClinicalStage(getMappedValue(value_coded));
					break;
				case 10:// TBStatus
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setTBStatus(getMappedValue(value_coded));
					break;
				case 11: // OtherOIOtherProblem
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setOtherOIOtherProblems(getMappedValue(value_coded));
					break;
				case 12: // NotedSideEffects
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setNotedSideEffects(getMappedValue(value_coded));
					break;
				case 13: // ARVDrugRegimen
					value_coded = obs.getValueCoded().getConceptId();
					cst = new CodedSimpleType();
					cst.setCode(getMappedValue(value_coded));
					cst.setCodeDescTxt(obs.getValueCodedName().getName());
					hivEncounter.setARVDrugRegimen(cst);
					break;
				case 14: //ARVDrugAdherence
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setARVDrugAdherence(getMappedValue(value_coded));
					break;
				case 15: //WhyPoorFairARVDrugAdherence
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setWhyPoorFairARVDrugAdherence(getMappedValue(value_coded));
					break;
				case 16: //CotrimoxazoleDose
					value_coded = obs.getValueCoded().getConceptId();
					cst = new CodedSimpleType();
					cst.setCode(getMappedValue(value_coded));
					cst.setCodeDescTxt(obs.getValueCoded().getName().getName());
					hivEncounter.setCotrimoxazoleDose(cst);
					break;
				case 17:// CotrimoxazoleAdherence
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setCotrimoxazoleAdherence(getMappedValue(value_coded));
					break;
				case 18: // WhyPoorFairCotrimoxazoleAdherence
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setWhyPoorFairCotrimoxazoleDrugAdherence(getMappedValue(value_coded));
					break;
				case 19: // INHDose
					value_coded = obs.getValueCoded().getConceptId();
					cst = new CodedSimpleType();
					cst.setCode(getMappedValue(value_coded));
					cst.setCodeDescTxt(obs.getValueCoded().getName().getName());
					hivEncounter.setINHDose(cst);
					break;
				case 20: // INHAdherence
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setINHAdherence(getMappedValue(value_coded));
					break;
				case 30: // WhyPoorFairINHDrugAdherence
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setWhyPoorFairINHDrugAdherence(getMappedValue(value_coded));
					break;
				case 31: //CD4 Count
					value_numeric = (int) Math.round(obs.getValueNumeric());
					hivEncounter.setCD4(value_numeric);
					break;
				case 32: // CD4TestDate
					value_datetime = obs.getValueDatetime();
					hivEncounter.setCD4TestDate(Utils.getXmlDate(value_datetime));
					break;
				case 33: //NextAppointmentDate
					value_datetime = obs.getValueDate();
					hivEncounter.setNextAppointmentDate(Utils.getXmlDate(value_datetime));
					break;
				default:
					break;
			}
			String bp = "";
			if (StringUtils.isNotEmpty(SystolicBloodPressure) && StringUtils.isNotEmpty(DistolicBloodPressure)) {
				bp = SystolicBloodPressure + "/" + DistolicBloodPressure;
				hivEncounter.setBloodPressure(bp);
			}
		}
		return hivEncounter;
	}
	
}
