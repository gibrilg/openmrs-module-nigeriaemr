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

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.model.ndr.IndividualReportType;
import org.openmrs.module.nigeriaemr.model.ndr.MessageHeaderType;
import org.openmrs.module.nigeriaemr.model.ndr.PatientDemographicsType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverter;
import org.openmrs.module.nigeriaemr.util.ZipUtil;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.openmrs.api.EncounterService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.util.List;

/**
 *  * Controller for a fragment that shows all users  
 */
public class NdrFragmentController {
	
	public void controller(FragmentModel model, @SpringBean("encounterService") EncounterService service,
	        @FragmentParam(value = "start", required = false) Date startDate,
	        @FragmentParam(value = "end", required = false) Date endDate) {
		
		model.addAttribute("encounters", service.getEncounters("", null, null, false));
	}
	
	public String generateNDRFile(@RequestParam(value = "start", required = false) Date startDate,
	        @RequestParam(value = "end", required = false) Date endDate, HttpServletRequest request)
	        throws DatatypeConfigurationException, IOException, SAXException, JAXBException {
		
		//create report download folder at the server. skip if already exist
		Utils util = new Utils();
		String reportType = "RADET";
		String reportFolder = util.ensureReportFolderExist(request, reportType);

		
		//Create an xml file and save in today's folder
		NDRConverter generator = new NDRConverter(new Date(), "Initial", "TestIP", "TestCode");
		List<Patient> patients = Context.getPatientService().getAllPatients();
		FacilityType facility = Utils.createFacilityType("ABC HOSPITAL", "as231errt", "FAC");
		
		Container cnt = null;
		
		for (Patient patient : patients) {
			cnt = generator.createContainer(patient, facility);
			String xmlfile = reportFolder + "\\" + patient.getPatientId().toString().replaceAll("[^a-zA-Z0-9]", "") + ".xml";
			File aXMLFile = new File(xmlfile);
			if (aXMLFile.exists()) {
				aXMLFile.delete();
			}
			Boolean b = aXMLFile.createNewFile();
			System.out.println("creating file : " + xmlfile + "was successful : " + b);
			generator.writeFile(cnt, aXMLFile);
		}
		

		String fileUrl =util.ZipFolder(request, reportFolder, reportType);  //request.getContextPath() + "/downloads/" + zipFileName;
		
		return fileUrl;
	}


	

}
