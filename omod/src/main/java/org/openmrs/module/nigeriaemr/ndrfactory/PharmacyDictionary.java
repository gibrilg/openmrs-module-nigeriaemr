/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.module.nigeriaemr.model.ndr.CodedSimpleType;
import org.openmrs.module.nigeriaemr.model.ndr.RegimenType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getXmlDate;

public class PharmacyDictionary {

	public final static int Medication_Duration_Concept_Id = 159368;

	public final static int TB_regimen_Concept_set = 165728;
	public final static int OI_regimen_Concept_set = 165726;
	public final static int OI_Drug_Concept_Id = 165727;
	public final static int Prescribed_Regimen_Line_Concept_Id=165708;
	public final static int Adult_Ist_Regimen_Line_Concept_Id=164506;
	public final static int Pediatric_Ist_Regimen_Line_Concept_Id=164507;
	public final static int Adult_2nd_Regimen_Line_Concept_Id=164513;
	public final static int Pediatric_2nd_Regimen_Line_Concept_Id=164514;
	public final static int Adult_3rd_Regimen_Line_Concept_Id=165702;
	public final static int Pediatric_3rd_Regimen_Line_Concept_Id=165703;
	public final static int Pick_Up_Reason_Concept_Id = 0;
	public final static int switch_Indicator_Concept_Id = 0;
	public final static int substitution_Indicator_Concept_Id =0;


	public PharmacyDictionary() {
		loadDictionary();
	}
	private Map<Integer, String> regimenMap = new HashMap<>();

	/*
		Concept ID for regimen to be gotten from
	 */
	public void loadDictionary() {
		//key is concept id, value is NDR coded value

		regimenMap.put(165691,"10a");
		regimenMap.put(165693,"10b");
		regimenMap.put(165696,"10c");
		regimenMap.put(165692,"10d");
		regimenMap.put(165694,"10e");
		regimenMap.put(165690,"10f");
		regimenMap.put(165695,"10g");
		regimenMap.put(165530,"10i");
		regimenMap.put(165698,"10j");
		regimenMap.put(165700,"10k");
		regimenMap.put(165689,"10l");
		regimenMap.put(165688,"10m");
		regimenMap.put(165701,"10o");
		regimenMap.put(165697,"10p");
		regimenMap.put(165699,"10q");
		regimenMap.put(165681,"10r");
		regimenMap.put(165686,"10t");
		regimenMap.put(165537,"10v");
		regimenMap.put(165682,"10w");
		regimenMap.put(165687,"10y");
		regimenMap.put(162563,"4c");
		regimenMap.put(162199,"4d");
		regimenMap.put(162200,"5a");
		regimenMap.put(164511,"2f");
		regimenMap.put(160124,"1a");
		regimenMap.put(162561,"2e");
		regimenMap.put(1652,"1b");
		regimenMap.put(164512,"2d");
		regimenMap.put(164505,"1e");
		regimenMap.put(162201,"2b");
		regimenMap.put(162565,"1f");
		regimenMap.put(104565,"1c");
		regimenMap.put(164854,"1d");


		//OI drug
		/*regimenMap.put("Cotrimoxazole 960mg", "CTX960");
		regimenMap.put("Cotrimoxazole 800mg", "CTX800");
		regimenMap.put("Cotrimoxazole 480mg", "CTX480");
		regimenMap.put("Cotrimoxazole 400mg", "CTX400");
		regimenMap.put("Cotrimoxazole 240mg/5ml", "CTX240");
		regimenMap.put("Fluconazole", "FLUC");
		regimenMap.put("Dapsone", "DDS");
		regimenMap.put("Isoniazid-Pyridoxine", "INHB6");*/

		//regimen line
		regimenMap.put(Adult_Ist_Regimen_Line_Concept_Id, "10");
		regimenMap.put(Adult_2nd_Regimen_Line_Concept_Id,"20");
		regimenMap.put(Adult_3rd_Regimen_Line_Concept_Id, "30");
		regimenMap.put(Pediatric_Ist_Regimen_Line_Concept_Id, "10");
		regimenMap.put(Pediatric_2nd_Regimen_Line_Concept_Id, "20");
		regimenMap.put(Pediatric_3rd_Regimen_Line_Concept_Id, "30");
	}

	public String getRegimenMapValue(int value_coded) {
		return regimenMap.get(value_coded);
	}

	public RegimenType createRegimenType(Patient pts,Encounter enc, List<Obs> pharmacyObsList)
			throws DatatypeConfigurationException {
		RegimenType regimenType = new RegimenType();
		CodedSimpleType cst;

		regimenType.setVisitID(Utils.getVisitId(pts,enc));
		regimenType.setVisitDate(Utils.getXmlDate(enc.getEncounterDatetime()));

		//set regimen line
		Obs obs = Utils.extractObs(Prescribed_Regimen_Line_Concept_Id, pharmacyObsList);
		if(obs !=null){
			regimenType.setPrescribedRegimenLineCode(getRegimenMapValue(obs.getValueCoded().getConceptId()));

			//set regimen
			obs = Utils.extractObs(obs.getValueCoded().getConceptId(), pharmacyObsList);
			if(obs !=null && obs.getValueCoded() !=null){
				cst = new CodedSimpleType();
				cst.setCode(getRegimenMapValue(obs.getValueCoded().getConceptId()));
				regimenType.setPrescribedRegimen(cst);
			}
		}

		//set type code
		regimenType.setPrescribedRegimenTypeCode("ARV");

		//set duration
		obs = Utils.extractObs(Medication_Duration_Concept_Id, pharmacyObsList);
		int drugDuration=0;
		if(obs !=null){
			drugDuration = (int) Math.round(obs.getValueNumeric());
		}
		regimenType.setPrescribedRegimenDuration(String.valueOf(drugDuration));

		//set dispensed date
		regimenType.setPrescribedRegimenDispensedDate(Utils.getXmlDate(enc.getEncounterDatetime()));

		//set regimen start date
		Calendar cal = Calendar.getInstance();
		cal.setTime(enc.getEncounterDatetime());
		regimenType.setDateRegimenStarted(getXmlDate(enc.getEncounterDatetime()));
		regimenType.setDateRegimenStartedDD(StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0"));
		regimenType.setDateRegimenStartedMM(StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, "0"));
		regimenType.setDateRegimenStartedYYYY(String.valueOf(cal.get(Calendar.YEAR)));

		// set regimen end date
		cal.add(Calendar.DATE, drugDuration);
		regimenType.setDateRegimenEndedDD(StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0"));
		regimenType.setDateRegimenEndedMM(String.valueOf(cal.get(Calendar.MONTH) + 1));
		regimenType.setDateRegimenEndedYYYY(String.valueOf(cal.get(Calendar.YEAR)));

		//TODO: set switch and substitution values

		return regimenType;
	}



	private RegimenType createOIType(Patient pts,Encounter enc, List<Obs> OIDrugObsList)
			throws DatatypeConfigurationException {
		RegimenType regimenType = new RegimenType();
		CodedSimpleType cst;

		regimenType.setVisitID(Utils.getVisitId(pts,enc));
		regimenType.setVisitDate(Utils.getXmlDate(enc.getEncounterDatetime()));

		//set regimen
		Obs obs = Utils.extractObs(OI_Drug_Concept_Id, OIDrugObsList);

		if(obs !=null && obs.getValueCoded() !=null){
			cst = new CodedSimpleType();
			cst.setCode(getRegimenMapValue(obs.getValueCoded().getConceptId()));
			regimenType.setPrescribedRegimen(cst);
		}
		//set type code
		if(obs !=null && obs.getObsGroup().getConcept().getConceptId() == OI_regimen_Concept_set){
			regimenType.setPrescribedRegimenTypeCode("CTX");
		}
		else if(obs !=null && obs.getObsGroup().getConcept().getConceptId() == TB_regimen_Concept_set){
			regimenType.setPrescribedRegimenTypeCode("TB");
		}


		//set duration
		int drugDuration = 0;
		obs = Utils.extractObs(Medication_Duration_Concept_Id, OIDrugObsList);
		if(obs !=null){
			drugDuration = (int) Math.round(obs.getValueNumeric());
			regimenType.setPrescribedRegimenDuration(String.valueOf(drugDuration));
		}


		//set dispensed date
		regimenType.setPrescribedRegimenDispensedDate(Utils.getXmlDate(enc.getEncounterDatetime()));

		//set regimen start date
		Calendar cal = Calendar.getInstance();
		cal.setTime(enc.getEncounterDatetime());
		regimenType.setDateRegimenStarted(getXmlDate(enc.getEncounterDatetime()));
		regimenType.setDateRegimenStartedDD(StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0"));
		regimenType.setDateRegimenStartedMM(StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, "0"));
		regimenType.setDateRegimenStartedYYYY(String.valueOf(cal.get(Calendar.YEAR)));

		// set regimen end date
		if(drugDuration !=0){
			cal.add(Calendar.DATE, drugDuration);
			regimenType.setDateRegimenEndedDD(StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0"));
			regimenType.setDateRegimenEndedMM(String.valueOf(cal.get(Calendar.MONTH) + 1));
			regimenType.setDateRegimenEndedYYYY(String.valueOf(cal.get(Calendar.YEAR)));
		}

		return regimenType;
	}

	public List<RegimenType> createOITypes(Patient pts,Encounter enc, List<Obs> pharmacyObsList)
			throws DatatypeConfigurationException {

		List<RegimenType> regimenTypeList = new ArrayList<>();
		RegimenType regimenType;

		Set<Obs> groupObsSet = new HashSet<>();
		Set<Obs> drugObsSet;
		List<Obs> drugObsList = new ArrayList<>();

		for (Obs ele : pharmacyObsList) {
			if (ele.getObsGroup() != null &&
					(ele.getObsGroup().getConcept().getConceptId() == OI_regimen_Concept_set
					|| ele.getObsGroup().getConcept().getConceptId() == TB_regimen_Concept_set)) {
				groupObsSet.add(ele.getObsGroup());
			}
		}
		for (Obs ele : groupObsSet) {
			drugObsSet = ele.getGroupMembers();
			drugObsList.addAll(drugObsSet);

			regimenType = createOIType(pts,enc, drugObsList);
			regimenTypeList.add(regimenType);

			drugObsList.clear();
		}
		return regimenTypeList;
	}
}
