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
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.module.nigeriaemr.model.ndr.CodedSimpleType;
import org.openmrs.module.nigeriaemr.model.ndr.RegimenType;

import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getXmlDate;

public class PharmacyDictionary {
	
	private static Map<Integer, String> map = new HashMap<Integer, String>();
	
	private static Map<Integer, String> regimenMap = new HashMap<Integer, String>();
	
	private static List<Integer> arvList = new ArrayList<Integer>();
	
	public static void loadDictionary() {
		map.put(123, "123");
	}
	
	public static void loadRegimenDictionary() {
		regimenMap.put(1, "123");
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
	
	public static String getMappedValue(int value_coded) {
		return map.get(value_coded);
	}
	
	public static RegimenType createRegimenType(String visitID, Date visitDate, Patient pts, List<Obs> pharmacyDrugObsList)
	        throws DatatypeConfigurationException {
		RegimenType regimenType = new RegimenType();
		regimenType.setVisitID(visitID);
		regimenType.setVisitDate(getXmlDate(visitDate));
		CodedSimpleType cst = new CodedSimpleType();
		int conceptID = 123;// ConceptID for DrugName
		Obs obs = extractObs(conceptID, pharmacyDrugObsList);
		cst.setCode(getMappedValue(obs.getValueCoded().getConceptId()));
		cst.setCodeDescTxt(obs.getValueCoded().getName().getName());
		regimenType.setPrescribedRegimen(cst);
		String prescribedRegimenCodeType = "";
		if (isARV(obs.getValueCoded().getConceptId())) {
			prescribedRegimenCodeType = "ARV";
		} else {
			prescribedRegimenCodeType = "CTX";
		}
		regimenType.setPrescribedRegimenTypeCode(prescribedRegimenCodeType);
		
		conceptID = 123; // NextVisitDate ConceptID
		obs = extractObs(conceptID, pharmacyDrugObsList);
		Date nextAppointmentDate = null;
		if (obs != null) {
			nextAppointmentDate = obs.getValueDate();
		}
		int drugDurationDays = 0;
		if (nextAppointmentDate != null && (visitDate.after(nextAppointmentDate) || visitDate.equals(nextAppointmentDate))) {
			drugDurationDays = nextAppointmentDate.getDay() - visitDate.getDay();
			regimenType.setPrescribedRegimenDuration(String.valueOf(drugDurationDays));
		}
		regimenType.setPrescribedRegimenDispensedDate(getXmlDate(visitDate));
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
		if (endDate != null) {
			regimenType.setDateRegimenEnded(getXmlDate(endDate));
			cal.setTime(endDate);
			month = StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, "0");
			year = String.valueOf(cal.get(Calendar.YEAR));
			day = StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, "0");
			regimenType.setDateRegimenEndedDD(day);
			regimenType.setDateRegimenEndedMM(month);
			regimenType.setDateRegimenEndedYYYY(year);
			//DateTime startDateTime = new DateTime(startDate);
			//DateTime endDateTime = new DateTime(endDate);
			//Weeks wks = Weeks.weeksBetween(startDateTime, endDateTime);
			//int wkVal = wks.getWeeks();
			//Days days=Days.daysBetween(startDateTime, endDateTime);
			//int daysVal=days.getDays();
			//String regimenDuration = String.valueOf(daysVal);
			//regimenType.setPrescribedRegimenDuration(regimenDuration);
			
		}
		
		return regimenType;
		
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
