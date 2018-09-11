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
	public final static int Pick_Up_Reason_Concept_Id = 165774;
	public final static int switch_Indicator_Concept_Id = 165772;
	public final static int substitution_Indicator_Concept_Id =165665;


	public PharmacyDictionary() {
		loadDictionary();
	}
	private Map<Integer, String> regimenMap = new HashMap<>();

	/*
		Concept ID for regimen to be gotten from
	 */
	public void loadDictionary() {
		//key is concept id, value is NDR coded value

		regimenMap.put(160124, "1a");//"AZT-3TC-EFV"
		regimenMap.put(1652, "1b");//"AZT-3TC-NVP"
		regimenMap.put(104565, "1c");//"TDF-FTC-EFV"
		regimenMap.put(164854, "1d");//"TDF-FTC-NVP"
		regimenMap.put(164505, "1e"); //"TDF-3TC-EFV"
		regimenMap.put(162565, "1f");//"TDF-3TC-NVP"
		regimenMap.put(817, "1g"); //"AZT-3TC-ABC"
		regimenMap.put(165522, "1h"); //"AZT-3TC-TDF”
		regimenMap.put(162563, "1l"); //"ABC-3TC-EFV"
		regimenMap.put(165681, "1m"); //"TDF-3TC-DTG"
		regimenMap.put(165686, "1n"); //"TDF-3TC-EFV400"
		regimenMap.put(165682, "1o"); //"TDF-FTC-DTG"
		regimenMap.put(165687, "1p"); //"TDF-FTC-EFV400"
		regimenMap.put(165523, "2a"); //"TDF-FTC-LPV/r"
		regimenMap.put(162201, "2b");//"TDF-3TC-LPV/r"
		regimenMap.put(165524, "2c"); //"TDF-FTC-ATV/r"
		regimenMap.put(164512, "2d");//"TDF-3TC-ATV/r"
		regimenMap.put(162561, "2e");//"AZT-3TC-LPV/r"
		regimenMap.put(164511, "2f");//"AZT-3TC-ATV/r"
		regimenMap.put(165530, "2h");//"AZT-TDF-3TC-LPV/r"
		regimenMap.put(165537, "2i");//"TDF-AZT-3TC-ATV/r"
		regimenMap.put(165688, "3a ");//"DRV/r-DTG + 1-2 NRTIs"
		regimenMap.put(160124, "4a");//"AZT-3TC-EFV"
		regimenMap.put(1652, "4b");//"AZT-3TC-NVP"
		regimenMap.put(162563, "4c");//"ABC-3TC-EFV"
		regimenMap.put(162199, "4d");//"ABC-3TC-NVP"
		regimenMap.put(817, "4e");//"AZT-3TC-ABC"
		regimenMap.put(792, "4f");//"d4T-3TC-NVP"
		regimenMap.put(165691,"4h"); //ABC-3TC-DTG
		regimenMap.put(165693, "4i"); //ABC-3TC-EFV400
		regimenMap.put(162200, "4j"); //ABC-3TC-LPV/r
		regimenMap.put(165692, "4k"); //ABC-FTC-DTG
		regimenMap.put(165694, "4l" ); //ABC-FTC-EFV400
		regimenMap.put(165690, "4m"); //ABC-FTC-NVP
		regimenMap.put(162561 ,"4n"); //AZT-3TC-LPV/r
		regimenMap.put(165695,"4o");//AZT-3TC-RAL
		regimenMap.put(165681, "4p"); //TDF-3TC-DTG
		regimenMap.put(164505, "4q"); //TDF-3TC-EFV
		regimenMap.put(165686, "4r"); //TDF-3TC-EFV400
		regimenMap.put(162565, "4s"); // TDF-3TC-NVP
		regimenMap.put(165682,"4t"); // TDF-FTC-DTG
		regimenMap.put(104565,"4u"); //TDF-FTC-EFV
		regimenMap.put(165687, "4v"); // TDF-FTC-EFV400
		regimenMap.put(164854, "4w");// TDF-FTC-NVP
		regimenMap.put(162200, "5a");;//"ABC-3TC-LPV/r"
		regimenMap.put(162561, "5b");;//"AZT-3TC-LPV/r"
		regimenMap.put(162560, "5c");;//"d4T-3TC-LPV/r"
		regimenMap.put(165526, "5e");;//"ABC-3TC-ddi"
		regimenMap.put(165696,"5g");//ABC-3TC-RAL
		regimenMap.put(164511,"5h"); // AZT-3TC-ATV/r
		regimenMap.put (165695,"5i");  //AZT-3TC-RAL
		regimenMap.put(164512,"5j"); //TDF-3TC-ATV/r
		regimenMap.put(162201,"5k");//TDF-3TC-LPV/r
		regimenMap.put(165698,"6a"); //DRV/r + 2 NRTIs + 2 NNRTI
		regimenMap.put(165700,"6b"); //DRV/r +2NRTIs
		regimenMap.put(165688,"6c"); //DRV/r-DTG + 1-2 NRTIs
		regimenMap.put(165701,"6d"); //DRV/r-RAL + 1-2NRTIs
		regimenMap.put(165697, "6e"); //DTG+2 NRTIs
		regimenMap.put(165699, "6f"); //RAL + 2 NRTIs
		regimenMap.put(26, "9a");//"AZT"
		regimenMap.put(27, "9b");//"3TC"
		regimenMap.put(28, "9c");//"NVP"
		regimenMap.put(29, "9d");//"AZT-3TC"
		regimenMap.put(30, "9e");//"AZT-NVP"
		regimenMap.put(31, "9f");//"FTC-TDF"
		regimenMap.put(32, "9g");//"3TC-d4T"
		regimenMap.put(33, "9h"); //"3TC-d4T"
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
		if(obs ==null){
			return null;
		}
		regimenType.setPrescribedRegimenLineCode(getRegimenMapValue(obs.getValueCoded().getConceptId()));

		//set regimen code
		obs = Utils.extractObs(obs.getValueCoded().getConceptId(), pharmacyObsList);
		if(obs ==null || obs.getValueCoded() ==null) { //if no prescribed regimen, discard the entire regimen object
			return null;
		}
		cst = new CodedSimpleType();
		cst.setCode(getRegimenMapValue(obs.getValueCoded().getConceptId()));
		regimenType.setPrescribedRegimen(cst);


		//set duration
		obs = Utils.extractObs(Medication_Duration_Concept_Id, pharmacyObsList);
		if(obs ==null){
			return null;
		}
		int drugDuration = (int) Math.round(obs.getValueNumeric());
		regimenType.setPrescribedRegimenDuration(String.valueOf(drugDuration));
		//set dispensed date
		regimenType.setPrescribedRegimenDispensedDate(Utils.getXmlDate(enc.getEncounterDatetime()));

		//set type code
		regimenType.setPrescribedRegimenTypeCode("ARV");

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

		// drug pick up reason
		obs = Utils.extractObs(Pick_Up_Reason_Concept_Id, pharmacyObsList);
		if(obs !=null){
			obs = Utils.extractObs(obs.getValueCoded().getConceptId(), pharmacyObsList);
			if(obs !=null && obs.getValueCoded() !=null){
				if(obs.getValueCoded().getConceptId() == substitution_Indicator_Concept_Id){
					regimenType.setSubstitutionIndicator(true);
				}
				//TODO: set switch reason
				if(obs.getValueCoded().getConceptId() == switch_Indicator_Concept_Id){
					regimenType.setSwitchIndicator(true);
					regimenType.setReasonForRegimenSwitchSubs("");
				}
			}
		}
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
			if(regimenType !=null && StringUtils.isNotEmpty(regimenType.getPrescribedRegimenDuration())
					&& regimenType.getPrescribedRegimen() != null ){
				regimenTypeList.add(regimenType);
			}

			drugObsList.clear();
		}
		return regimenTypeList;
	}
}
