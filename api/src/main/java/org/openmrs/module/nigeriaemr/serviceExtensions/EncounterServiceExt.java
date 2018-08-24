package org.openmrs.module.nigeriaemr.serviceExtensions;

//import org.openmrs.Encounter;
//import org.openmrs.EncounterType;
//import org.openmrs.Patient;
import org.openmrs.api.db.EncounterDAO;
import org.openmrs.api.impl.EncounterServiceImpl;
//import org.springframework.transaction.annotation.Transactional;
//import org.openmrs.parameter.EncounterSearchCriteriaBuilder;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;

/*
* this class extends the Encounter class
* to
*
*/

public class EncounterServiceExt extends EncounterServiceImpl {

	private EncounterDAO dao;

	@Override
	public void setEncounterDAO(EncounterDAO dao) {
		this.dao = dao;
	}



}
