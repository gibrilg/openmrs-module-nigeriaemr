package org.openmrs.module.nigeriaemr.ndrfactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.module.nigeriaemr.model.ndr.CodedSimpleType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVEncounterType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

public class ClinicalDictionary {

	public final static int Weight_Concept_Id= 5089,
	Child_Height_Concept_Id = 5090,
	Patient_Family_Planning_Method_Code_Concept_Id=4,
	Noted_Side_Effects_Concept_Id=159935,
	//CD4_Concept_Id=6,
	//CD4_Test_Date_Concept_Id=7,
	Next_Appointment_Date_Concept_Id=5096,
	Systolic_Blood_Pressure_Concept_Id=5085,
	Dystolic_Blood_Pressure_Concept_Id =5086,
	Pregnancy_Breastfeeding_Status_Concept_Id=165050,
	Patient_Family_Planning_Concept_Id = 5271,
	Functional_Status_Concept_Id = 165039,
	WHO_Clinical_Stage_Concept_Id =5356,
	TB_Status_Concept_Id=1659,
	Other_OI_Other_Problem_Concept_Id = 1728,
	ARV_Drug_Adherence_Concept_Id = 165290,
	Why_Poor_Fair_ARV_Drug_Adherence_Concept_Id=19,
	Cotrimoxazole_Dose_Concept_Id=20,
	Cotrimoxazole_Adherence_Concept_Id=161652,
	Why_Poor_Fair_Cotrimoxazole_Adherence_Concept_Id=22,
	INH_Dose_Concept_Id = 23,
	INH_Adherence_Concept_Id=161653,
	Why_Poor_Fair_INH_Drug_Adherence_Concept_Id = 25;

	private Map<Integer, String> map = new HashMap<>();

		public ClinicalDictionary() {
			loadDictionary();
		}

	private void loadDictionary() {
		//Map OpenMRS concepts to corresponding NDR values
		map.put(123, "12");
	}
	
	private String getMappedValue(int conceptID) {
		return map.get(conceptID);
	}
	
	public HIVEncounterType createHIVEncounterType(Patient patient, Encounter enc, List<Obs> obsList)
			throws DatatypeConfigurationException {
		
		HIVEncounterType hivEncounter = new HIVEncounterType();

		Date artStartDate = Utils.getARTStartDate(patient);
		
		String visitID = Utils.getVisitId(patient, enc);
		hivEncounter.setVisitID(visitID);
		hivEncounter.setVisitDate(Utils.getXmlDate(enc.getEncounterDatetime()));

		if (artStartDate != null && (enc.getEncounterDatetime().after(artStartDate) || enc.getEncounterDatetime().equals(artStartDate))) {
			int	monthOnART = enc.getEncounterDatetime().getMonth() - artStartDate.getMonth(); // Months.monthsBetween(d1, d2).getMonths();
			hivEncounter.setDurationOnArt(monthOnART);
		}

		int conceptID;
		int value_numeric;
		int value_coded;
		Date value_datetime;
		String SystolicBloodPressure="";
		String DistolicBloodPressure="";
		CodedSimpleType cst;

		for (Obs obs : obsList) {
			conceptID = obs.getConcept().getConceptId();
			switch (conceptID) {
				case Child_Height_Concept_Id:
					value_numeric = (int) Math.round(obs.getValueNumeric());
					hivEncounter.setChildHeight(value_numeric);
					break;
				case Weight_Concept_Id:
					value_numeric = (int) Math.round(obs.getValueNumeric());
					hivEncounter.setWeight(value_numeric);
					break;
				case Systolic_Blood_Pressure_Concept_Id:
					SystolicBloodPressure = String.valueOf((int) Math.round(obs.getValueNumeric()));
					break;
				case Dystolic_Blood_Pressure_Concept_Id:
					DistolicBloodPressure = String.valueOf((int) Math.round(obs.getValueNumeric()));
					break;
				case Pregnancy_Breastfeeding_Status_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setEDDandPMTCTLink(getMappedValue(value_coded));
					break;
				case Patient_Family_Planning_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setPatientFamilyPlanningCode(getMappedValue(value_coded));
					break;
				case Patient_Family_Planning_Method_Code_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setPatientFamilyPlanningMethodCode(getMappedValue(value_coded));
					break;
				case Functional_Status_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setFunctionalStatus(getMappedValue(value_coded));
					break;
				case WHO_Clinical_Stage_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setWHOClinicalStage(getMappedValue(value_coded));
					break;
				case TB_Status_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setTBStatus(getMappedValue(value_coded));
					break;
				case Other_OI_Other_Problem_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setOtherOIOtherProblems(getMappedValue(value_coded));
					break;
				case Noted_Side_Effects_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setNotedSideEffects(getMappedValue(value_coded));
					break;
				/*case ARV_Drug_Regimen_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					cst = new CodedSimpleType();
					cst.setCode(getMappedValue(value_coded));
					cst.setCodeDescTxt(obs.getValueCodedName().getName());
					hivEncounter.setARVDrugRegimen(cst);
					break;*/
				case ARV_Drug_Adherence_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setARVDrugAdherence(getMappedValue(value_coded));
					break;
				case Why_Poor_Fair_ARV_Drug_Adherence_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setWhyPoorFairARVDrugAdherence(getMappedValue(value_coded));
					break;
				case Cotrimoxazole_Dose_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					cst = new CodedSimpleType();
					cst.setCode(getMappedValue(value_coded));
					cst.setCodeDescTxt(obs.getValueCoded().getName().getName());
					hivEncounter.setCotrimoxazoleDose(cst);
					break;
				case Cotrimoxazole_Adherence_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setCotrimoxazoleAdherence(getMappedValue(value_coded));
					break;
				case Why_Poor_Fair_Cotrimoxazole_Adherence_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setWhyPoorFairCotrimoxazoleDrugAdherence(getMappedValue(value_coded));
					break;
				case INH_Dose_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					cst = new CodedSimpleType();
					cst.setCode(getMappedValue(value_coded));
					cst.setCodeDescTxt(obs.getValueCoded().getName().getName());
					hivEncounter.setINHDose(cst);
					break;
				case INH_Adherence_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setINHAdherence(getMappedValue(value_coded));
					break;
				case Why_Poor_Fair_INH_Drug_Adherence_Concept_Id:
					value_coded = obs.getValueCoded().getConceptId();
					hivEncounter.setWhyPoorFairINHDrugAdherence(getMappedValue(value_coded));
					break;
				/*case CD4_Concept_Id:
					value_numeric = (int) Math.round(obs.getValueNumeric());
					hivEncounter.setCD4(value_numeric);
					break;
				case CD4_Test_Date_Concept_Id:
					value_datetime = obs.getValueDatetime();
					hivEncounter.setCD4TestDate(Utils.getXmlDate(value_datetime));
					break;*/
				case Next_Appointment_Date_Concept_Id:
					value_datetime = obs.getValueDate();
					hivEncounter.setNextAppointmentDate(Utils.getXmlDate(value_datetime));
					break;
				default:
					break;
			}
		}

		if (StringUtils.isNotEmpty(SystolicBloodPressure) && StringUtils.isNotEmpty(DistolicBloodPressure)) {
			String bp = SystolicBloodPressure + "/" + DistolicBloodPressure;
			hivEncounter.setBloodPressure(bp);
		}
		return hivEncounter;
	}
	
}
