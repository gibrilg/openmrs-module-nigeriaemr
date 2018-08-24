package org.openmrs.module.nigeriaemr.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for PartnerNotificationServicesType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartnerNotificationServicesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PartnerName" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="PartnerGender" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="F"/>
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="A"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="O"/>
 *               &lt;enumeration value="U"/>
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
@XmlType(name = "PartnerNotificationServicesType", propOrder = { "partnerName", "partnerGender" })
public class PartnerNotificationServicesType {
	
	@XmlElement(name = "PartnerName")
	protected String partnerName;
	
	@XmlElement(name = "PartnerGender")
	protected String partnerGender;
	
	/**
	 * Gets the value of the partnerName property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getPartnerName() {
		return partnerName;
	}
	
	/**
	 * Sets the value of the partnerName property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setPartnerName(String value) {
		this.partnerName = value;
	}
	
	/**
	 * Gets the value of the partnerGender property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getPartnerGender() {
		return partnerGender;
	}
	
	/**
	 * Sets the value of the partnerGender property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setPartnerGender(String value) {
		this.partnerGender = value;
	}
	
}
