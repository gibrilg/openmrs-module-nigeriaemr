package org.openmrs.module.nigeriaemr.ndrUtils;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
	
	public static XMLGregorianCalendar getXmlDate(Date date) throws DatatypeConfigurationException {
		XMLGregorianCalendar cal = null;
		if (date != null) {
			cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd").format(date));
		}
		return cal;
	}
	
	public static XMLGregorianCalendar getXmlDateTime(Date date) throws DatatypeConfigurationException {
		XMLGregorianCalendar cal = null;
		if (date != null) {
			cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(
			    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date));
		}
		return cal;
	}
	
	public static FacilityType createFacilityType(String facilityName, String facilityID, String facilityTypeCode) {
		FacilityType facilityType = new FacilityType();
		facilityType.setFacilityName(facilityName);
		facilityType.setFacilityID(facilityID);
		facilityType.setFacilityTypeCode(facilityTypeCode);
		return facilityType;
	}
	
	public static Obs getFirstRegimen(Patient patient) {
		Obs obs = null;
		EncounterService encSrv = Context.getEncounterService();
		List<Encounter> encounterList = encSrv.getEncountersByPatient(patient);
		
		for (Encounter enc : encounterList) {
			if (enc.getEncounterType().getEncounterTypeId() == 4) {
				obs = enc.getAllObs().iterator().next();
			}
		}
		return obs;
	}
	
}
