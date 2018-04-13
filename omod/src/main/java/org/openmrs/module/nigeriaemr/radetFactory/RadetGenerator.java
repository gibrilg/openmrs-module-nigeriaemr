package org.openmrs.module.nigeriaemr.radetFactory;

import org.openmrs.api.context.Context;

public class RadetGenerator {
	
	public String generatePatientListing() {
		
		Integer patientCount = Context.getPatientService().getAllPatients().size();
		return "Total patient in the database is " + patientCount;
	}
}
