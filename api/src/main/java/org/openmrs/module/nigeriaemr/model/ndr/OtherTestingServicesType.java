package org.openmrs.module.nigeriaemr.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for OtherTestingServicesType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OtherTestingServicesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SyphilisTestResult" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="NR"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HepBTestResult" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HepCTestResult" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="Pos"/>
 *               &lt;enumeration value="Neg"/>
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
@XmlType(name = "OtherTestingServicesType", propOrder = { "syphilisTestResult", "hepBTestResult", "hepCTestResult" })
public class OtherTestingServicesType {
	
	@XmlElement(name = "SyphilisTestResult")
	protected String syphilisTestResult;
	
	@XmlElement(name = "HepBTestResult")
	protected String hepBTestResult;
	
	@XmlElement(name = "HepCTestResult")
	protected String hepCTestResult;
	
	/**
	 * Gets the value of the syphilisTestResult property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getSyphilisTestResult() {
		return syphilisTestResult;
	}
	
	/**
	 * Sets the value of the syphilisTestResult property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setSyphilisTestResult(String value) {
		this.syphilisTestResult = value;
	}
	
	/**
	 * Gets the value of the hepBTestResult property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHepBTestResult() {
		return hepBTestResult;
	}
	
	/**
	 * Sets the value of the hepBTestResult property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHepBTestResult(String value) {
		this.hepBTestResult = value;
	}
	
	/**
	 * Gets the value of the hepCTestResult property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHepCTestResult() {
		return hepCTestResult;
	}
	
	/**
	 * Sets the value of the hepCTestResult property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHepCTestResult(String value) {
		this.hepCTestResult = value;
	}
	
}
