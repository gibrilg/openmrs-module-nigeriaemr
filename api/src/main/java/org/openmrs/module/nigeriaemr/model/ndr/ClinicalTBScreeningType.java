package org.openmrs.module.nigeriaemr.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ClinicalTBScreeningType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClinicalTBScreeningType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CurrentCough" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="WeigthLoss" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Fever" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="NightSweats" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClinicalTBScreeningType", propOrder = { "currentCough", "weigthLoss", "fever", "nightSweats" })
public class ClinicalTBScreeningType {
	
	@XmlElement(name = "CurrentCough")
	protected Boolean currentCough;
	
	@XmlElement(name = "WeigthLoss")
	protected Boolean weigthLoss;
	
	@XmlElement(name = "Fever")
	protected Boolean fever;
	
	@XmlElement(name = "NightSweats")
	protected Boolean nightSweats;
	
	/**
	 * Gets the value of the currentCough property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isCurrentCough() {
		return currentCough;
	}
	
	/**
	 * Sets the value of the currentCough property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setCurrentCough(Boolean value) {
		this.currentCough = value;
	}
	
	/**
	 * Gets the value of the weigthLoss property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isWeigthLoss() {
		return weigthLoss;
	}
	
	/**
	 * Sets the value of the weigthLoss property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setWeigthLoss(Boolean value) {
		this.weigthLoss = value;
	}
	
	/**
	 * Gets the value of the fever property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isFever() {
		return fever;
	}
	
	/**
	 * Sets the value of the fever property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setFever(Boolean value) {
		this.fever = value;
	}
	
	/**
	 * Gets the value of the nightSweats property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isNightSweats() {
		return nightSweats;
	}
	
	/**
	 * Sets the value of the nightSweats property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setNightSweats(Boolean value) {
		this.nightSweats = value;
	}
	
}
