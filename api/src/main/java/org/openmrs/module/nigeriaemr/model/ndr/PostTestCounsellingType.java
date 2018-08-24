package org.openmrs.module.nigeriaemr.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for PostTestCounsellingType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostTestCounsellingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TestedForHIVBeforeWithinThisYear" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}CodeType">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HIVRequestAndResultFormSignedByTester" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="HIVRequestAndResultFormFilledWithCTIForm" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ClientRecievedHIVTestResult" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PostTestCounsellingDone" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RiskReductionPlanDeveloped" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PostTestDisclosurePlanDeveloped" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="WillBringPartnerForHIVTesting" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="WillBringOwnChildrenForHIVTesting" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ProvidedWithInformationOnFPandDualContraception" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ClientOrPartnerUseFPMethodsOtherThanCondoms" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ClientOrPartnerUseCondomsAsOneFPMethods" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CorrectCondomUseDemonstrated" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CondomsProvidedToClient" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ClientReferredToOtherServices" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostTestCounsellingType", propOrder = { "testedForHIVBeforeWithinThisYear",
        "hivRequestAndResultFormSignedByTester", "hivRequestAndResultFormFilledWithCTIForm", "clientRecievedHIVTestResult",
        "postTestCounsellingDone", "riskReductionPlanDeveloped", "postTestDisclosurePlanDeveloped",
        "willBringPartnerForHIVTesting", "willBringOwnChildrenForHIVTesting",
        "providedWithInformationOnFPandDualContraception", "clientOrPartnerUseFPMethodsOtherThanCondoms",
        "clientOrPartnerUseCondomsAsOneFPMethods", "correctCondomUseDemonstrated", "condomsProvidedToClient",
        "clientReferredToOtherServices" })
public class PostTestCounsellingType {
	
	@XmlElement(name = "TestedForHIVBeforeWithinThisYear")
	protected String testedForHIVBeforeWithinThisYear;
	
	@XmlElement(name = "HIVRequestAndResultFormSignedByTester")
	protected Boolean hivRequestAndResultFormSignedByTester;
	
	@XmlElement(name = "HIVRequestAndResultFormFilledWithCTIForm")
	protected Boolean hivRequestAndResultFormFilledWithCTIForm;
	
	@XmlElement(name = "ClientRecievedHIVTestResult")
	protected Boolean clientRecievedHIVTestResult;
	
	@XmlElement(name = "PostTestCounsellingDone")
	protected Boolean postTestCounsellingDone;
	
	@XmlElement(name = "RiskReductionPlanDeveloped")
	protected Boolean riskReductionPlanDeveloped;
	
	@XmlElement(name = "PostTestDisclosurePlanDeveloped")
	protected Boolean postTestDisclosurePlanDeveloped;
	
	@XmlElement(name = "WillBringPartnerForHIVTesting")
	protected Boolean willBringPartnerForHIVTesting;
	
	@XmlElement(name = "WillBringOwnChildrenForHIVTesting")
	protected Boolean willBringOwnChildrenForHIVTesting;
	
	@XmlElement(name = "ProvidedWithInformationOnFPandDualContraception")
	protected Boolean providedWithInformationOnFPandDualContraception;
	
	@XmlElement(name = "ClientOrPartnerUseFPMethodsOtherThanCondoms")
	protected Boolean clientOrPartnerUseFPMethodsOtherThanCondoms;
	
	@XmlElement(name = "ClientOrPartnerUseCondomsAsOneFPMethods")
	protected Boolean clientOrPartnerUseCondomsAsOneFPMethods;
	
	@XmlElement(name = "CorrectCondomUseDemonstrated")
	protected Boolean correctCondomUseDemonstrated;
	
	@XmlElement(name = "CondomsProvidedToClient")
	protected Boolean condomsProvidedToClient;
	
	@XmlElement(name = "ClientReferredToOtherServices")
	protected Boolean clientReferredToOtherServices;
	
	/**
	 * Gets the value of the testedForHIVBeforeWithinThisYear property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getTestedForHIVBeforeWithinThisYear() {
		return testedForHIVBeforeWithinThisYear;
	}
	
	/**
	 * Sets the value of the testedForHIVBeforeWithinThisYear property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setTestedForHIVBeforeWithinThisYear(String value) {
		this.testedForHIVBeforeWithinThisYear = value;
	}
	
	/**
	 * Gets the value of the hivRequestAndResultFormSignedByTester property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isHIVRequestAndResultFormSignedByTester() {
		return hivRequestAndResultFormSignedByTester;
	}
	
	/**
	 * Sets the value of the hivRequestAndResultFormSignedByTester property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setHIVRequestAndResultFormSignedByTester(Boolean value) {
		this.hivRequestAndResultFormSignedByTester = value;
	}
	
	/**
	 * Gets the value of the hivRequestAndResultFormFilledWithCTIForm property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isHIVRequestAndResultFormFilledWithCTIForm() {
		return hivRequestAndResultFormFilledWithCTIForm;
	}
	
	/**
	 * Sets the value of the hivRequestAndResultFormFilledWithCTIForm property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setHIVRequestAndResultFormFilledWithCTIForm(Boolean value) {
		this.hivRequestAndResultFormFilledWithCTIForm = value;
	}
	
	/**
	 * Gets the value of the clientRecievedHIVTestResult property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isClientRecievedHIVTestResult() {
		return clientRecievedHIVTestResult;
	}
	
	/**
	 * Sets the value of the clientRecievedHIVTestResult property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setClientRecievedHIVTestResult(Boolean value) {
		this.clientRecievedHIVTestResult = value;
	}
	
	/**
	 * Gets the value of the postTestCounsellingDone property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isPostTestCounsellingDone() {
		return postTestCounsellingDone;
	}
	
	/**
	 * Sets the value of the postTestCounsellingDone property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setPostTestCounsellingDone(Boolean value) {
		this.postTestCounsellingDone = value;
	}
	
	/**
	 * Gets the value of the riskReductionPlanDeveloped property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isRiskReductionPlanDeveloped() {
		return riskReductionPlanDeveloped;
	}
	
	/**
	 * Sets the value of the riskReductionPlanDeveloped property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setRiskReductionPlanDeveloped(Boolean value) {
		this.riskReductionPlanDeveloped = value;
	}
	
	/**
	 * Gets the value of the postTestDisclosurePlanDeveloped property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isPostTestDisclosurePlanDeveloped() {
		return postTestDisclosurePlanDeveloped;
	}
	
	/**
	 * Sets the value of the postTestDisclosurePlanDeveloped property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setPostTestDisclosurePlanDeveloped(Boolean value) {
		this.postTestDisclosurePlanDeveloped = value;
	}
	
	/**
	 * Gets the value of the willBringPartnerForHIVTesting property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isWillBringPartnerForHIVTesting() {
		return willBringPartnerForHIVTesting;
	}
	
	/**
	 * Sets the value of the willBringPartnerForHIVTesting property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setWillBringPartnerForHIVTesting(Boolean value) {
		this.willBringPartnerForHIVTesting = value;
	}
	
	/**
	 * Gets the value of the willBringOwnChildrenForHIVTesting property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isWillBringOwnChildrenForHIVTesting() {
		return willBringOwnChildrenForHIVTesting;
	}
	
	/**
	 * Sets the value of the willBringOwnChildrenForHIVTesting property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setWillBringOwnChildrenForHIVTesting(Boolean value) {
		this.willBringOwnChildrenForHIVTesting = value;
	}
	
	/**
	 * Gets the value of the providedWithInformationOnFPandDualContraception property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isProvidedWithInformationOnFPandDualContraception() {
		return providedWithInformationOnFPandDualContraception;
	}
	
	/**
	 * Sets the value of the providedWithInformationOnFPandDualContraception property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setProvidedWithInformationOnFPandDualContraception(Boolean value) {
		this.providedWithInformationOnFPandDualContraception = value;
	}
	
	/**
	 * Gets the value of the clientOrPartnerUseFPMethodsOtherThanCondoms property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isClientOrPartnerUseFPMethodsOtherThanCondoms() {
		return clientOrPartnerUseFPMethodsOtherThanCondoms;
	}
	
	/**
	 * Sets the value of the clientOrPartnerUseFPMethodsOtherThanCondoms property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setClientOrPartnerUseFPMethodsOtherThanCondoms(Boolean value) {
		this.clientOrPartnerUseFPMethodsOtherThanCondoms = value;
	}
	
	/**
	 * Gets the value of the clientOrPartnerUseCondomsAsOneFPMethods property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isClientOrPartnerUseCondomsAsOneFPMethods() {
		return clientOrPartnerUseCondomsAsOneFPMethods;
	}
	
	/**
	 * Sets the value of the clientOrPartnerUseCondomsAsOneFPMethods property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setClientOrPartnerUseCondomsAsOneFPMethods(Boolean value) {
		this.clientOrPartnerUseCondomsAsOneFPMethods = value;
	}
	
	/**
	 * Gets the value of the correctCondomUseDemonstrated property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isCorrectCondomUseDemonstrated() {
		return correctCondomUseDemonstrated;
	}
	
	/**
	 * Sets the value of the correctCondomUseDemonstrated property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setCorrectCondomUseDemonstrated(Boolean value) {
		this.correctCondomUseDemonstrated = value;
	}
	
	/**
	 * Gets the value of the condomsProvidedToClient property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isCondomsProvidedToClient() {
		return condomsProvidedToClient;
	}
	
	/**
	 * Sets the value of the condomsProvidedToClient property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setCondomsProvidedToClient(Boolean value) {
		this.condomsProvidedToClient = value;
	}
	
	/**
	 * Gets the value of the clientReferredToOtherServices property.
	 * 
	 * @return possible object is {@link Boolean }
	 */
	public Boolean isClientReferredToOtherServices() {
		return clientReferredToOtherServices;
	}
	
	/**
	 * Sets the value of the clientReferredToOtherServices property.
	 * 
	 * @param value allowed object is {@link Boolean }
	 */
	public void setClientReferredToOtherServices(Boolean value) {
		this.clientReferredToOtherServices = value;
	}
	
}
