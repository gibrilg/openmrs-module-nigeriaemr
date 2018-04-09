/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.nigeriaemr.fragment.controller;

import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.SimpleObject;
import org.springframework.web.bind.annotation.RequestParam;
import org.openmrs.ui.framework.UiUtils;

/**
 *  * Controller for a fragment that shows all users  
 */
public class NdrFragmentController {
	
	private Date defaultStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	private Date defaultEndDate(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MILLISECOND, -1);
		return cal.getTime();
	}
	
	public void controller(FragmentModel model, @SpringBean("encounterService") EncounterService service,
	        @FragmentParam(value = "start", required = false) Date startDate,
	        @FragmentParam(value = "end", required = false) Date endDate) {
		
		if (startDate == null)
			startDate = defaultStartDate();
		if (endDate == null)
			endDate = defaultEndDate(startDate);
		
		model.addAttribute("encounters", service.getEncounters("", null, null, false));
	}
	
	public List<SimpleObject> getEncounters(@RequestParam(value = "start", required = false) Date startDate,
	        @RequestParam(value = "end", required = false) Date endDate,
	        @RequestParam(value = "properties", required = false) String[] properties,
	        @SpringBean("encounterService") EncounterService service, UiUtils ui) {
		
		if (startDate == null)
			startDate = defaultStartDate();
		if (endDate == null)
			endDate = defaultEndDate(startDate);
		
		if (properties == null) {
			properties = new String[] { "encounterType", "encounterDatetime", "location", "provider" };
		}
		
		List<Encounter> encs = service.getEncounters("", null, null, null, false);
		return SimpleObject.fromCollection(encs, ui, properties);
	}
	
}
