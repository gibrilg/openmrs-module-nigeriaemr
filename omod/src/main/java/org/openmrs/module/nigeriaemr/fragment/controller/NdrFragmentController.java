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
import javax.servlet.http.HttpServletRequest;
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
	        @RequestParam(value = "end", required = false) Date endDate,
	        @SpringBean("encounterService") EncounterService service, HttpServletRequest request)
	        throws DatatypeConfigurationException {
		
		//create report download folder at the server. skip if already exist
		String folder = new File(request.getRealPath(request.getContextPath())).getParentFile().toString() + "\\downloads"; //request.getRealPath(request.getContextPath()) + "\\reports";
		File dir = new File(folder);
		Boolean b = dir.mkdir();
		System.out.println("Creating folder : " + folder + "was successful : " + b);
		
		//create today's folder
		String dateString = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String todayFolders = folder + "/" + dateString;
		dir = new File(todayFolders);
		
		if (dir.exists()) {
			dir.delete();
		}
		
		b = dir.mkdir();
		System.out.println("creating folder : " + todayFolders + "was successful : " + b);
		
		//delete folder content in case it exist before
		
		//Create an xml file and save in today's folder
		NDRConverter generator = new NDRConverter(new Date(), "Initial","TestIP","TestCode");
		List<Patient> patients = Context.getPatientService().getAllPatients();

		for (Patient patient:patients){
			Container cnt = generator.createContainer(patient);
			writeContainerXMLToFile(todayFolders + "\\" + patient.getPatientId().toString().replaceAll("[^a-zA-Z0-9]", "") + ".xml", cnt);
		}

		
		//Zip today's folder and name it with today's date
		String zipFileName = new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".zip";
		
		ZipUtil appZip = new ZipUtil(todayFolders);
		appZip.generateFileList(new File(todayFolders));
		appZip.zipIt(folder + "/" + zipFileName);
		
		String fileUrl = request.getContextPath() + "/downloads/" + zipFileName;
		
		return fileUrl;
	}



	public void writeContainerXMLToFile(String filePath, Container xmlContainer) {

		XMLEncoder encoder = null;
		try {
			encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filePath)));
		}
		catch (FileNotFoundException fileNotFound) {
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(xmlContainer);
		encoder.close();

	}


	public Container generateXMLObject() throws DatatypeConfigurationException {
		
		FacilityType IPInfo = new FacilityType();
		IPInfo.setFacilityID("ABCD");
		IPInfo.setFacilityName("this is a long name of IP");
		IPInfo.setFacilityTypeCode("IP");
		
		MessageHeaderType msgHeader = new MessageHeaderType();
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar xdate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		
		msgHeader.setMessageCreationDateTime(xdate);
		msgHeader.setMessageSchemaVersion(new BigDecimal(1.2));
		msgHeader.setMessageSendingOrganization(IPInfo);
		msgHeader.setMessageStatusCode("UPDATED");
		
		Container xmlContainer = new Container();
		xmlContainer.setMessageHeader(msgHeader);
		
		FacilityType facilityType = new FacilityType();
		facilityType.setFacilityID("1000002");
		facilityType.setFacilityName("this is a facilityName");
		facilityType.setFacilityTypeCode("FAC");
		
		PatientDemographicsType patientDemographicsType = new PatientDemographicsType();
		patientDemographicsType.setTreatmentFacility(facilityType);
		
		c.set(1980, 9, 29);
		XMLGregorianCalendar birthDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		patientDemographicsType.setPatientDateOfBirth(birthDate);
		patientDemographicsType.setPatientSexCode("M");
		patientDemographicsType.setPatientMaritalStatusCode("M");
		patientDemographicsType.setPatientIdentifier("A0029");
		
		IndividualReportType individualReportType = new IndividualReportType();
		individualReportType.setPatientDemographics(patientDemographicsType);
		
		xmlContainer.setIndividualReport(individualReportType);
		
		return xmlContainer;
	}
	

	
	public void writeToFile(String filePath, String content) {
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
			
			fw = new FileWriter(filePath);
			bw = new BufferedWriter(fw);
			bw.write(content);
			System.out.println("Done");
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			
			try {
				if (bw != null)
					bw.close();
				
				if (fw != null)
					fw.close();
				
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
