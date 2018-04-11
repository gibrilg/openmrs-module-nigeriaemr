/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.ndrfactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.Obs;
import org.openmrs.module.nigeriaemr.model.ndr.AnswerType;
import org.openmrs.module.nigeriaemr.model.ndr.CodedSimpleType;
import org.openmrs.module.nigeriaemr.model.ndr.CodedType;
import org.openmrs.module.nigeriaemr.model.ndr.LaboratoryOrderAndResult;
import org.openmrs.module.nigeriaemr.model.ndr.LaboratoryReportType;
import org.openmrs.module.nigeriaemr.model.ndr.NumericType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

import static org.openmrs.module.nigeriaemr.ndrUtils.Utils.getXmlDate;

/**
 * @author The Bright
 */
public class LabDictionary {
	
	private static Map<Integer, String> labTestDictionary = new HashMap<Integer, String>();
	
	private static Map<Integer, String> labTestUnits = new HashMap<Integer, String>();
	
	private static Map<String, String> labTestUnitDescription = new HashMap<String, String>();
	
	public static void loadDictionary() {
		//Map OpenMRS concepts to corresponding NDR values
		labTestDictionary.put(123, "12");
		
		// load lab unit Map
	}
	
	public static String getMappedValue(int conceptID) {
		return labTestDictionary.get(conceptID);
	}
	
	public static boolean isValidLabTest(int conceptID) {
		return labTestDictionary.keySet().contains(conceptID);
	}
	
	public static LaboratoryReportType createLaboratoryOrderAndResult(Date visitDate, String visitID, Date artStartDate,
	        List<Obs> labObsList) throws DatatypeConfigurationException {
		LaboratoryReportType labReportType = new LaboratoryReportType();
		labReportType.setVisitID(visitID);
		labReportType.setVisitDate(getXmlDate(visitDate));
		Date orderedDate = null;
		Obs obs = null;
		int conceptID = 123; // Collection Date
		obs = extractObs(conceptID, labObsList);
		labReportType.setCollectionDate(getXmlDate(obs.getValueDate()));
		conceptID = 123;// Visit Type
		obs = extractObs(conceptID, labObsList);
		labReportType.setBaselineRepeatCode(getMappedValue(obs.getValueCoded().getConceptId()));
		if (artStartDate != null) {
			labReportType.setARTStatusCode("A");
		} else {
			// Non ART status code
		}
		conceptID = 123; // Ordered By
		obs = extractObs(conceptID, labObsList);
		if (obs != null) {
			labReportType.setClinician(obs.getValueText());
		}
		conceptID = 123;// Checked By
		obs = extractObs(conceptID, labObsList);
		if (obs != null) {
			labReportType.setCheckedBy(obs.getValueText());
		}
		conceptID = 123;// OrderedDete
		obs = extractObs(conceptID, labObsList);
		if (obs != null) {
			orderedDate = obs.getValueDate();
		}
		return labReportType;
	}
	
	public static List<LaboratoryOrderAndResult> createLaboratoryOrderAndResult(Date visitDate, Date orderedDate,
	        List<Obs> obsList) throws DatatypeConfigurationException {
		List<LaboratoryOrderAndResult> labResultList = new ArrayList<LaboratoryOrderAndResult>();
		LaboratoryOrderAndResult labOrderAndResult = new LaboratoryOrderAndResult();
		int conceptID = 0;
		int dataType = 0;
		int value_coded = 0;
		
		for (Obs obs : obsList) {
			//c = conceptDictionary.get(conceptID);
			CodedSimpleType cst;
			LaboratoryOrderAndResult result;
			AnswerType answer;
			NumericType numeric;
			dataType = obs.getConcept().getDatatype().getConceptDatatypeId();
			if (dataType == 1 && isValidLabTest(conceptID)) {
				cst = new CodedSimpleType();
				cst.setCode(getMappedValue(conceptID));
				cst.setCodeDescTxt(obs.getConcept().getName().getName());
				result = new LaboratoryOrderAndResult();
				result.setLaboratoryResultedTest(cst);
				answer = new AnswerType();
				numeric = new NumericType();
				if (orderedDate != null) {
					result.setOrderedTestDate(getXmlDate(orderedDate));
				}
				if (visitDate != null) {
					result.setResultedTestDate(getXmlDate(visitDate));
				}
				numeric.setValue1((int) Math.round(obs.getValueNumeric()));
				if (labTestUnits.containsKey(conceptID)) {
					String[] descriptionText = null;
					CodedType ct = new CodedType();
					ct.setCode(labTestUnits.get(conceptID));
					descriptionText = StringUtils.split(labTestUnitDescription.get(ct.getCode()), ",");
					if (descriptionText != null) {
						ct.setCodeDescTxt(descriptionText[0]);
						ct.setCodeSystemCode(StringEscapeUtils.escapeXml(descriptionText[1]));
					}
					numeric.setUnit(ct);
				}
				answer.setAnswerNumeric(numeric);
				result.setLaboratoryResult(answer);
				labResultList.add(result);
			} else if (dataType == 2 && labTestDictionary.containsKey(conceptID)) {
				cst = new CodedSimpleType();
				value_coded = obs.getValueCoded().getConceptId();
				cst.setCode(labTestDictionary.get(conceptID));
				cst.setCodeDescTxt(obs.getValueCoded().getName().getName());
				result = new LaboratoryOrderAndResult();
				result.setLaboratoryResultedTest(cst);
				answer = new AnswerType();
				CodedType ct = new CodedType();
				ct.setCode(labTestDictionary.get(obs.getValueCoded().getConceptId()));
				ct.setCodeDescTxt(obs.getValueCoded().getName().getName());
				ct.setCodeSystemCode(obs.getValueCoded().getName().getName());
				answer.setAnswerCode(ct);
				result.setLaboratoryResult(answer);
				if (orderedDate != null) {
					result.setOrderedTestDate(getXmlDate(orderedDate));
				}
				if (visitDate != null) {
					result.setResultedTestDate(getXmlDate(visitDate));
				}
				labResultList.add(result);
			}
			
		}
		
		labResultList.add(labOrderAndResult);
		return labResultList;
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
