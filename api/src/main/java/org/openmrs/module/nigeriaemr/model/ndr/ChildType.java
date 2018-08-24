package org.openmrs.module.nigeriaemr.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ChildType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChildType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="childBirthDetails" type="{}ChildBirthDetailsType" minOccurs="0"/>
 *         &lt;element name="childFollowUp" type="{}ChildFollowUpType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChildType", propOrder = { "childBirthDetails", "childFollowUp" })
public class ChildType {
	
	protected ChildBirthDetailsType childBirthDetails;
	
	protected ChildFollowUpType childFollowUp;
	
	/**
	 * Gets the value of the childBirthDetails property.
	 * 
	 * @return possible object is {@link ChildBirthDetailsType }
	 */
	public ChildBirthDetailsType getChildBirthDetails() {
		return childBirthDetails;
	}
	
	/**
	 * Sets the value of the childBirthDetails property.
	 * 
	 * @param value allowed object is {@link ChildBirthDetailsType }
	 */
	public void setChildBirthDetails(ChildBirthDetailsType value) {
		this.childBirthDetails = value;
	}
	
	/**
	 * Gets the value of the childFollowUp property.
	 * 
	 * @return possible object is {@link ChildFollowUpType }
	 */
	public ChildFollowUpType getChildFollowUp() {
		return childFollowUp;
	}
	
	/**
	 * Sets the value of the childFollowUp property.
	 * 
	 * @param value allowed object is {@link ChildFollowUpType }
	 */
	public void setChildFollowUp(ChildFollowUpType value) {
		this.childFollowUp = value;
	}
	
}
