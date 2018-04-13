package org.openmrs.module.nigeriaemr.ndrUtils;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.FacilityType;
import org.openmrs.module.nigeriaemr.util.ZipUtil;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public static FacilityType createFacilityType(String facilityName, String facilityID, String facilityTypeCode) {
		FacilityType facilityType = new FacilityType();
		facilityType.setFacilityName(facilityName);
		facilityType.setFacilityID(facilityID);
		facilityType.setFacilityTypeCode(facilityTypeCode);
		return facilityType;
	}
	
	public String ensureDownloadFolderExist(HttpServletRequest request) {
		//create report download folder at the server. skip if already exist
		String folder = new File(request.getRealPath(request.getContextPath())).getParentFile().toString() + "\\downloads"; //request.getRealPath(request.getContextPath()) + "\\reports";
		File dir = new File(folder);
		Boolean b = dir.mkdir();
		System.out.println("Creating download folder : " + folder + "was successful : " + b);
		return folder;
	}

	
	public String ensureReportFolderExist(HttpServletRequest request, String reportType) {
		String downloadFolder = ensureDownloadFolderExist(request);
		String reportFolder = downloadFolder + "/" + reportType;
		File dir = new File(reportFolder);
		dir.mkdir();
		System.out.println(reportType + " folder exist ? : " + dir.exists());
		
		//create today's folder
		String dateString = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String todayFolders = reportFolder + "/" + dateString;
		dir = new File(todayFolders);
		if (dir.exists()) {
			dir.delete();
		}
		dir.mkdir();
		System.out.println(todayFolders + " folder exist : " + dir.exists());
		
		return todayFolders;
	}
	
	public String ZipFolder(HttpServletRequest request, String folderToZip, String reportType) {
		
		//Zip today's folder and name it with today's date
		String zipFileName = new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".zip";
		ZipUtil appZip = new ZipUtil(folderToZip);
		appZip.generateFileList(new File(folderToZip));
		appZip.zipIt(new File(folderToZip).getParent() + "/" + zipFileName);
		
		String fileUrl = request.getContextPath() + "/downloads/" + reportType + "/" + zipFileName;
		return fileUrl;
	}

	public void writeDataToExcel(String fullFilePath){



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

	public String ensureTodayDownloadFolderExist(String parentFolder, HttpServletRequest request) {
		//create today's folder
		String dateString = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String todayFolders = parentFolder + "/" + dateString;
		File dir = new File(todayFolders);
		Boolean b = dir.mkdir();
		System.out.println("creating folder : " + todayFolders + "was successful : " + b);
		return todayFolders;
	}
	
}
