package org.openmrs.module.nigeriaemr.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for InfantPCRTestingType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfantPCRTestingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgeAtTest" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DateSampleCollected" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="DateSampleSent" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DateResultRecievedAtFacility" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DateCaregiverGivenResult" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="PCRTestResult">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *               &lt;enumeration value="NP"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfantPCRTestingType", propOrder = { "ageAtTest", "dateSampleCollected", "dateSampleSent",
        "dateResultRecievedAtFacility", "dateCaregiverGivenResult", "pcrTestResult" })
public class InfantPCRTestingType {
	
	@XmlElement(name = "AgeAtTest")
	protected int ageAtTest;
	
	@XmlElement(name = "DateSampleCollected", required = true)
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar dateSampleCollected;
	
	@XmlElement(name = "DateSampleSent")
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar dateSampleSent;
	
	@XmlElement(name = "DateResultRecievedAtFacility")
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar dateResultRecievedAtFacility;
	
	@XmlElement(name = "DateCaregiverGivenResult")
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar dateCaregiverGivenResult;
	
	@XmlElement(name = "PCRTestResult", required = true)
	protected String pcrTestResult;
	
	/**
	 * Gets the value of the ageAtTest property.
	 */
	public int getAgeAtTest() {
		return ageAtTest;
	}
	
	/**
	 * Sets the value of the ageAtTest property.
	 */
	public void setAgeAtTest(int value) {
		this.ageAtTest = value;
	}
	
	/**
	 * Gets the value of the dateSampleCollected property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 */
	public XMLGregorianCalendar getDateSampleCollected() {
		return dateSampleCollected;
	}
	
	/**
	 * Sets the value of the dateSampleCollected property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 */
	public void setDateSampleCollected(XMLGregorianCalendar value) {
		this.dateSampleCollected = value;
	}
	
	/**
	 * Gets the value of the dateSampleSent property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 */
	public XMLGregorianCalendar getDateSampleSent() {
		return dateSampleSent;
	}
	
	/**
	 * Sets the value of the dateSampleSent property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 */
	public void setDateSampleSent(XMLGregorianCalendar value) {
		this.dateSampleSent = value;
	}
	
	/**
	 * Gets the value of the dateResultRecievedAtFacility property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 */
	public XMLGregorianCalendar getDateResultRecievedAtFacility() {
		return dateResultRecievedAtFacility;
	}
	
	/**
	 * Sets the value of the dateResultRecievedAtFacility property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 */
	public void setDateResultRecievedAtFacility(XMLGregorianCalendar value) {
		this.dateResultRecievedAtFacility = value;
	}
	
	/**
	 * Gets the value of the dateCaregiverGivenResult property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 */
	public XMLGregorianCalendar getDateCaregiverGivenResult() {
		return dateCaregiverGivenResult;
	}
	
	/**
	 * Sets the value of the dateCaregiverGivenResult property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 */
	public void setDateCaregiverGivenResult(XMLGregorianCalendar value) {
		this.dateCaregiverGivenResult = value;
	}
	
	/**
	 * Gets the value of the pcrTestResult property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getPCRTestResult() {
		return pcrTestResult;
	}
	
	/**
	 * Sets the value of the pcrTestResult property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setPCRTestResult(String value) {
		this.pcrTestResult = value;
	}
	
}
