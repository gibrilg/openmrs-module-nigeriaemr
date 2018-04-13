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

import org.openmrs.Encounter;
import org.openmrs.api.EncounterService;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.radetFactory.RadetGenerator;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  * Controller for a fragment that shows all users  
 */
public class RadetFragmentController {
	
	private Date defaultStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, -3);
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
	
	public String getRADETReport(@RequestParam(value = "start", required = false) Date startDate,
	        @RequestParam(value = "end", required = false) Date endDate, HttpServletRequest request) {
		
		if (startDate == null)
			startDate = defaultStartDate();
		if (endDate == null)
			endDate = defaultEndDate(startDate);
		
		Utils util = new Utils();
		String reportType = "NDR";
		String reportFolder = util.ensureReportFolderExist(request, reportType);
		
		//RadetGenerator generator = new RadetGenerator();
		//String count = generator.generatePatientListing();
		
		String fileUrl = util.ZipFolder(request, reportFolder, reportType);
		return fileUrl;
	}
	
}
