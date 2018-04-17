/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.CodedSimpleType;
import org.openmrs.module.nigeriaemr.model.ndr.RegimenType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getXmlDate;

public class PharmacyDictionary {

	private static Map<Integer, String> regimenMap = new HashMap<Integer, String>();

	private static List<Integer> arvList = new ArrayList<Integer>();


	/*
		Concept ID for regimen to be gotten from
	 */
	public static void loadRegimenDictionary() {
		//regimenMap.put(1, "123");
		//key is concept id, value is NDR coded value
		regimenMap.put(1, "1k");//"3TC-D4T-EFV"
		regimenMap.put(2, "1a");//"AZT-3TC-EFV"
		regimenMap.put(3, "1b");//"AZT-3TC-NVP"
		regimenMap.put(4, "1c");//"TDF-FTC-EFV"
		regimenMap.put(5, "1d");//"TDF-FTC-NVP"
		regimenMap.put(6, "1e"); //"TDF-3TC-EFV"
		regimenMap.put(7, "1f");//"TDF-3TC-NVP"
		regimenMap.put(8, "1g"); //"AZT-3TC-ABC"
		regimenMap.put(9, "1h"); //"AZT-3TC-TDF"
		regimenMap.put(10, "2a"); //"TDF-FTC-LPV/r"
		regimenMap.put(11, "2b");//"TDF-3TC-LPV/r"
		regimenMap.put(12, "2c"); //"TDF-FTC-ATV/r"
		regimenMap.put(13, "2d");//"TDF-3TC-ATV/r"
		regimenMap.put(14, "2e");//"AZT-3TC-LPV/r"
		regimenMap.put(15, "2f");//"AZT-3TC-ATV/r"
		regimenMap.put(16, "4a");//"AZT-3TC-EFV"
		regimenMap.put(17, "4b");//"AZT-3TC-NVP"
		regimenMap.put(18, "4c");//"ABC-3TC-EFV"
		regimenMap.put(19, "4d");//"ABC-3TC-NVP"
		regimenMap.put(20, "4e");//"AZT-3TC-ABC"
		regimenMap.put(21, "4f");//"d4T-3TC-NVP"
		regimenMap.put(22, "5a");//"ABC-3TC-LPV/r"
		regimenMap.put(23, "5b");//"AZT-3TC-LPV/r"
		regimenMap.put(24, "5c");//"d4T-3TC-LPV/r"
		regimenMap.put(25, "5e");//"ABC-3TC-ddi"
		regimenMap.put(26, "9a");//"AZT"
		regimenMap.put(27, "9b");//"3TC"
		regimenMap.put(28, "9c");//"NVP"
		regimenMap.put(29, "9d");//"AZT-3TC"
		regimenMap.put(30, "9e");//"AZT-NVP"
		regimenMap.put(31, "9f");//"FTC-TDF"
		regimenMap.put(32, "9g");//"3TC-d4T"
		regimenMap.put(33, "9h"); //"3TC-d4T"
		regimenMap.put(34, "2g");//"AZT-FTC-LPV/r-TDF"
		regimenMap.put(35, "2g");//"3TC-AZT-LPV/r-TDF"
		regimenMap.put(36, "2g");//"IDV/r-AZT-3TC"
		regimenMap.put(37, "2g");//"IDV/r-AZT-FTC"
		regimenMap.put(38, "2g");//"IDV/r-AZT-3TC"
		regimenMap.put(39, "2g");//"IDV/r-3TC-D4T"
		regimenMap.put(40, "2g");//"AZT-FTC-ATV/r"
		regimenMap.put(41, "2g");//"AZT-FTC-LPV/r"
		regimenMap.put(42, "2g");//"ABC-AZT-3TC-LPV/r"
		regimenMap.put(43, "2g"); //"3TC-ABC-AZT-ATV/r"
		regimenMap.put(46, "2g"); //"ATV/r-AZT-3TC-TDF"
		regimenMap.put(47, "2g");//"ATV/r-AZT-FTC-TDF"
		regimenMap.put(48, "2g");//"3TC-ABC-ATV/r"
		regimenMap.put(49, "2g");//"3TC-D4T-SQV/r"
		regimenMap.put(50, "2g");//"ABC-SQV/r-TDF"
		regimenMap.put(51, "2g");//"ABC-DDI-LPV/r"
		regimenMap.put(52, "2g");//"TDF-FTC-SQV/r"
		regimenMap.put(53, "2g");//"3TC-AZT-SQV/r"
		regimenMap.put(54, "2g");//"3TC-IDV/r-TDF"
		regimenMap.put(55, "2g");//"FTC-IDV/r-TDF"
		regimenMap.put(56, "2g");//"3TC-AZT-SQV/r-TDF"
		regimenMap.put(57, "2g");//"3TC-SQV/r-TDF"
		regimenMap.put(58, "2g");//"ABC-AZT-LPV/r"
		regimenMap.put(59, "2g");//"DDI-IDV/r-TDF"
		regimenMap.put(60, "2g");//"DDI-SQV/r-TDF"
	}

	public static void loadARVList() {
		arvList.add(1230);
		arvList.add(1231);
	}

	public static boolean isARV(int valueCoded) {
		boolean ans = false;
		if (arvList.contains(valueCoded)) {
			ans = true;
		}
		return ans;
	}

	public static String getRegimenMapValue(int value_coded) {
		return regimenMap.get(value_coded);
	}


	/*
		   This method creates a RegimenType from an observation group
		*/
	public static RegimenType createRegimenTypeARV(Patient pts, List<Obs> pharmacyDrugObsList, List<Obs> pharmFormObsList)
			throws DatatypeConfigurationException {
		RegimenType regimenType = null;

		int conceptID = 123;// ConceptID for Regimen name
		Obs obs = extractObs(conceptID, pharmFormObsList);

		Visit visit;
		String visitID;
		Date visitDate;

		CodedSimpleType cst;
		Date nextAppointmentDate;
		int drugDurationDays;

		if (obs != null) {
			visit = obs.getEncounter().getVisit();
			visitID = visit.getVisitId().toString();
			visitDate = visit.getStartDatetime();

			regimenType = new RegimenType();
			regimenType.setVisitID(visitID);
			regimenType.setVisitDate(Utils.getXmlDate(visitDate));

			cst = new CodedSimpleType();
			cst.setCode(getRegimenMapValue(obs.getValueCoded().getConceptId()));
			cst.setCodeDescTxt(obs.getValueCoded().getName().getName());
			regimenType.setPrescribedRegimen(cst);

			regimenType.setPrescribedRegimenTypeCode("ARV");

			conceptID = 123; // NextVisitDate ConceptID
			obs = extractObs(conceptID, pharmFormObsList);
			nextAppointmentDate = obs.getValueDate();

			//TODO: recalculate regimen duration
			//TODO: set regimen line code
			if (nextAppointmentDate != null && (nextAppointmentDate.after(visitDate) || visitDate.equals(nextAppointmentDate))) {
				drugDurationDays = visitDate.getDay() - nextAppointmentDate.getDay();
				regimenType.setPrescribedRegimenDuration(String.valueOf(drugDurationDays));
			}
			regimenType.setPrescribedRegimenDispensedDate(Utils.getXmlDate(visitDate));
			Calendar cal = Calendar.getInstance();
			Date startDate = visitDate;
			cal.setTime(startDate);
			String month = "", year = "", day = "";
			month = StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, "0");
			year = String.valueOf(cal.get(Calendar.YEAR));
			day = StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0");
			regimenType.setDateRegimenStarted(getXmlDate(startDate));
			regimenType.setDateRegimenStartedDD(day);
			regimenType.setDateRegimenStartedMM(month);
			regimenType.setDateRegimenStartedYYYY(year);

			//TODO: change the regimen date change implementation
			Date endDate =  nextAppointmentDate;

			if (endDate != null){
				regimenType.setDateRegimenEnded(getXmlDate(endDate));

				cal.setTime(endDate);

				month = StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, "0");
				year = String.valueOf(cal.get(Calendar.YEAR));
				day = StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0");
				regimenType.setDateRegimenEndedDD(day);
				regimenType.setDateRegimenEndedMM(month);
				regimenType.setDateRegimenEndedYYYY(year);
			}
		}
		return regimenType;

	}

	public static RegimenType createRegimenTypeOI(String visitID, Date visitDate, Patient pts, List<Obs> pharmacyDrugObsList,List<Obs> pharmFormObsList) throws DatatypeConfigurationException {
		RegimenType regimenType = null;
		int conceptID = 123;// ConceptID for Drug name
		Obs obs = extractObs(conceptID, pharmacyDrugObsList);
		CodedSimpleType cst;
		String prescribedRegimenCodeType = "";
		Date nextAppointmentDate = null;
		int drugDurationDays = 0;
		if (obs != null) {
			regimenType = new RegimenType();
			regimenType.setVisitID(visitID);
			regimenType.setVisitDate(Utils.getXmlDate(visitDate));
			cst = new CodedSimpleType();
			cst.setCode(getRegimenMapValue(obs.getValueCoded().getConceptId()));

			cst.setCodeDescTxt(obs.getValueCoded().getName().getName());
			regimenType.setPrescribedRegimen(cst);
			prescribedRegimenCodeType = "OI";
			regimenType.setPrescribedRegimenTypeCode(prescribedRegimenCodeType);
			conceptID = 123; // NextVisitDate ConceptID
			obs = extractObs(conceptID, pharmFormObsList);
			nextAppointmentDate = obs.getValueDate();
			if (nextAppointmentDate != null && (nextAppointmentDate.after(visitDate) || visitDate.equals(nextAppointmentDate))) {
				drugDurationDays =  visitDate.getDay() - nextAppointmentDate.getDay();
				regimenType.setPrescribedRegimenDuration(String.valueOf(drugDurationDays));
			}
			regimenType.setPrescribedRegimenDispensedDate(Utils.getXmlDate(visitDate));
			Calendar cal = Calendar.getInstance();
			Date startDate = visitDate;
			cal.setTime(startDate);
			String month = "", year = "", day = "";
			month = StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, "0");
			year = String.valueOf(cal.get(Calendar.YEAR));
			day = StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0");
			regimenType.setDateRegimenStarted(getXmlDate(startDate));
			regimenType.setDateRegimenStartedDD(day);
			regimenType.setDateRegimenStartedMM(month);
			regimenType.setDateRegimenStartedYYYY(year);
			Date endDate = nextAppointmentDate;
			if (endDate != null){
				regimenType.setDateRegimenEnded(getXmlDate(endDate));
				cal.setTime(endDate);
				month = StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, "0");
				year = String.valueOf(cal.get(Calendar.YEAR));
				day = StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0");
				regimenType.setDateRegimenEndedDD(day);
				regimenType.setDateRegimenEndedMM(month);
				regimenType.setDateRegimenEndedYYYY(year);
			}
		}
		return regimenType;

	}


	public static List<RegimenType> constructRegimenTypeList(Patient pts,List<Obs> allObsFromPharmacyForm) throws DatatypeConfigurationException{
		List<RegimenType> regimenTypeList=new ArrayList<RegimenType>();
		Set<Obs> groupObsSet=new HashSet<Obs>();
		Set<Obs> drugObsSet=null;
		List<Obs> drugObsList=new ArrayList<Obs>();
		RegimenType regimenType=null;
		Obs obs=null;
		for(Obs ele: allObsFromPharmacyForm){
			obs=ele.getObsGroup();
			if(obs!=null){
				groupObsSet.add(obs);
			}
		}
		String visitID;
		Date visitDate;
		for(Obs ele: groupObsSet){
			drugObsSet=ele.getGroupMembers();
			drugObsList.addAll(drugObsSet);

			Visit visit = ele.getEncounter().getVisit();
			visitID = visit.getVisitId().toString();
			visitDate = visit.getStartDatetime();

			regimenType=createRegimenTypeARV(pts, drugObsList, allObsFromPharmacyForm);
			regimenTypeList.add(regimenType);

			regimenType=createRegimenTypeOI(visitID, visitDate, pts, drugObsList, allObsFromPharmacyForm);
			regimenTypeList.add(regimenType);

			drugObsList.clear();
		}
		return regimenTypeList;
	}


	public static Obs extractObs(int conceptID, List<Obs> obsList) {
		Obs obs = null;
		for (Obs ele : obsList) {
			if (ele.getConcept().getConceptId() == conceptID) {
				obs = ele;
			}
		}
		return obs;
	}
}
