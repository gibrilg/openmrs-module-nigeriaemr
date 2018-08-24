package org.openmrs.module.nigeriaemr.model.ndr;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for ChildFollowUpType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChildFollowUpType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="InfantARVType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TimingofARVProphylaxis" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AgeAtCTXInitiation" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="InfantOutcomeAt18Months" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *               &lt;enumeration value="5"/>
 *               &lt;enumeration value="6"/>
 *               &lt;enumeration value="7"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DateLinkedtoARTClinic" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ARTEnrollmentNumber" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="HealthFacilityVisits" type="{}HealthFacilityVisitType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PCRTests" type="{}InfantPCRTestingType" maxOccurs="3" minOccurs="0"/>
 *         &lt;element name="RapidTests" type="{}InfantRapidTestingType" maxOccurs="2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChildFollowUpType", propOrder = { "infantARVType", "timingofARVProphylaxis", "ageAtCTXInitiation",
        "infantOutcomeAt18Months", "dateLinkedtoARTClinic", "artEnrollmentNumber", "healthFacilityVisits", "pcrTests",
        "rapidTests" })
public class ChildFollowUpType {
	
	@XmlElement(name = "InfantARVType")
	protected String infantARVType;
	
	@XmlElement(name = "TimingofARVProphylaxis")
	protected String timingofARVProphylaxis;
	
	@XmlElement(name = "AgeAtCTXInitiation")
	protected Integer ageAtCTXInitiation;
	
	@XmlElement(name = "InfantOutcomeAt18Months")
	protected String infantOutcomeAt18Months;
	
	@XmlElement(name = "DateLinkedtoARTClinic")
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar dateLinkedtoARTClinic;
	
	@XmlElement(name = "ARTEnrollmentNumber")
	protected String artEnrollmentNumber;
	
	@XmlElement(name = "HealthFacilityVisits")
	protected List<HealthFacilityVisitType> healthFacilityVisits;
	
	@XmlElement(name = "PCRTests")
	protected List<InfantPCRTestingType> pcrTests;
	
	@XmlElement(name = "RapidTests")
	protected List<InfantRapidTestingType> rapidTests;
	
	/**
	 * Gets the value of the infantARVType property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getInfantARVType() {
		return infantARVType;
	}
	
	/**
	 * Sets the value of the infantARVType property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setInfantARVType(String value) {
		this.infantARVType = value;
	}
	
	/**
	 * Gets the value of the timingofARVProphylaxis property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getTimingofARVProphylaxis() {
		return timingofARVProphylaxis;
	}
	
	/**
	 * Sets the value of the timingofARVProphylaxis property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setTimingofARVProphylaxis(String value) {
		this.timingofARVProphylaxis = value;
	}
	
	/**
	 * Gets the value of the ageAtCTXInitiation property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public Integer getAgeAtCTXInitiation() {
		return ageAtCTXInitiation;
	}
	
	/**
	 * Sets the value of the ageAtCTXInitiation property.
	 * 
	 * @param value allowed object is {@link Integer }
	 */
	public void setAgeAtCTXInitiation(Integer value) {
		this.ageAtCTXInitiation = value;
	}
	
	/**
	 * Gets the value of the infantOutcomeAt18Months property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getInfantOutcomeAt18Months() {
		return infantOutcomeAt18Months;
	}
	
	/**
	 * Sets the value of the infantOutcomeAt18Months property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setInfantOutcomeAt18Months(String value) {
		this.infantOutcomeAt18Months = value;
	}
	
	/**
	 * Gets the value of the dateLinkedtoARTClinic property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 */
	public XMLGregorianCalendar getDateLinkedtoARTClinic() {
		return dateLinkedtoARTClinic;
	}
	
	/**
	 * Sets the value of the dateLinkedtoARTClinic property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 */
	public void setDateLinkedtoARTClinic(XMLGregorianCalendar value) {
		this.dateLinkedtoARTClinic = value;
	}
	
	/**
	 * Gets the value of the artEnrollmentNumber property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getARTEnrollmentNumber() {
		return artEnrollmentNumber;
	}
	
	/**
	 * Sets the value of the artEnrollmentNumber property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setARTEnrollmentNumber(String value) {
		this.artEnrollmentNumber = value;
	}
	
	/**
	 * Gets the value of the healthFacilityVisits property.
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any
	 * modification you make to the returned list will be present inside the JAXB object. This is
	 * why there is not a <CODE>set</CODE> method for the healthFacilityVisits property.
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
     *    getHealthFacilityVisits().add(newItem);
     * </pre>
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link HealthFacilityVisitType }
	 */
	public List<HealthFacilityVisitType> getHealthFacilityVisits() {
		if (healthFacilityVisits == null) {
			healthFacilityVisits = new ArrayList<HealthFacilityVisitType>();
		}
		return this.healthFacilityVisits;
	}
	
	/**
	 * Gets the value of the pcrTests property.
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any
	 * modification you make to the returned list will be present inside the JAXB object. This is
	 * why there is not a <CODE>set</CODE> method for the pcrTests property.
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
     *    getPCRTests().add(newItem);
     * </pre>
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link InfantPCRTestingType }
	 */
	public List<InfantPCRTestingType> getPCRTests() {
		if (pcrTests == null) {
			pcrTests = new ArrayList<InfantPCRTestingType>();
		}
		return this.pcrTests;
	}
	
	/**
	 * Gets the value of the rapidTests property.
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any
	 * modification you make to the returned list will be present inside the JAXB object. This is
	 * why there is not a <CODE>set</CODE> method for the rapidTests property.
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
     *    getRapidTests().add(newItem);
     * </pre>
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link InfantRapidTestingType }
	 */
	public List<InfantRapidTestingType> getRapidTests() {
		if (rapidTests == null) {
			rapidTests = new ArrayList<InfantRapidTestingType>();
		}
		return this.rapidTests;
	}
	
}
