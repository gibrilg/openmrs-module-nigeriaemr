package org.openmrs.module.nigeriaemr.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.Container;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.ndrfactory.NDRConverter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.xml.sax.SAXException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.util.List;

public class NdrFragmentController {
	
	public void controller() {
	}
	
	public String generateNDRFile(HttpServletRequest request) throws DatatypeConfigurationException, IOException,
	        SAXException, JAXBException {
		
		//create report download folder at the server. skip if already exist
		Utils util = new Utils();
		String reportType = "NDR";
		String reportFolder = util.ensureReportFolderExist(request, reportType);
		
		//Create an xml file and save in today's folder
		NDRConverter generator = new NDRConverter(Utils.getIPFullName(), Utils.getIPShortName());
		
		List<Patient> patients = Context.getPatientService().getAllPatients();
		
		String facilityName = Utils.getFacilityName();
		String DATIMID = Utils.getFacilityDATIMId();
		String FacilityType = "FAC";
		String IPShortName = Utils.getIPShortName();
		String formattedDate = new SimpleDateFormat("ddMMyy").format(new Date());
		
		FacilityType facility = Utils.createFacilityType(facilityName, DATIMID, FacilityType);
		
		try {
			Container cnt;
			for (Patient patient : patients) {
				cnt = generator.createContainer(patient, facility);
				if (cnt != null) {
					String fileName = IPShortName + "_" + DATIMID + "_" + formattedDate + "_"
					        + Utils.getPatientPEPFARId(patient);
					
					String xmlFile = reportFolder + "\\" + fileName + ".xml";
					File aXMLFile = new File(xmlFile);
					Boolean b;
					if (aXMLFile.exists()) {
						b = aXMLFile.delete();
						System.out.println("deleting file : " + xmlFile + "was successful : " + b);
					}
					b = aXMLFile.createNewFile();
					
					System.out.println("creating xml file : " + xmlFile + "was successful : " + b);
					generator.writeFile(cnt, aXMLFile);
				}
			}
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			throw ex;
		}
		
		//Update ndr last run date
		Utils.updateLast_NDR_Run_Date(new Date());
		
		String zipFileName = IPShortName + "_" + DATIMID + "_" + formattedDate + ".zip";
		return util.ZipFolder(request, reportFolder, zipFileName, reportType);
		//request.getContextPath() + "/downloads/" + zipFileName;
	}
}
