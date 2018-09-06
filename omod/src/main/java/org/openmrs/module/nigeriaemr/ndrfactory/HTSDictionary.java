package org.openmrs.module.nigeriaemr.ndrfactory;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.module.nigeriaemr.model.ndr.ClinicalTBScreeningType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVPreTestCounsellingType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVRecencyTestResultType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVRiskAssessmentType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVTestDetailsType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVTestResultsType;
import org.openmrs.module.nigeriaemr.model.ndr.HIVTestingEncounterType;
import org.openmrs.module.nigeriaemr.model.ndr.KnowledgeAssessmentType;
import org.openmrs.module.nigeriaemr.model.ndr.OtherTestingServicesType;
import org.openmrs.module.nigeriaemr.model.ndr.PostTestCounsellingType;
import org.openmrs.module.nigeriaemr.model.ndr.SyndromicSTIScreeningType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTSDictionary {

	private Map<Integer, String> valueCodedDictionary = new HashMap<>();
	private Patient patient;
	private Encounter encounter;
	private List<Obs> obs;

	public final int client_Code_concept_Id = 0;
	public final int Date_Of_Visit_concept_Id = 165844;
	public final int Setting_concept_Id = 165966;
	public final int Referred_From_concept_Id =165480;
	public final int First_Time_Visit_concept_Id =	165790;
	public final int Type_Of_Session_concept_Id =	165793;
	public final int Marital_Status_concept_Id =	1054;
	public final int Number_Of_Children_Less_Than_Five_concept_Id =	160312;
	public final int Number_Of_Wives_Or_Co_wives_concept_Id =	5557;
	public final int Client_Identified_From_Index_concept_Id =	165794;
	public final int Index_Type_concept_Id =	165798;
	public final int Index_Client_ID_concept_Id =	165859;
	public final int Vaginal_Discharge_Or_Burning_When_Urinating_concept_Id =	165809 ;
	public final int Lower_Abdominal_Pains_With_Or_Without_Vaginal_Discharge_concept_Id =	165810 ;
	public final int Urethral_Discharge_Or_Burning_When_Urinating_concept_Id =	165811;
	public final int Scrotal_Swelling_And_Pain_concept_Id =	165812;
	public final int Genital_Sore_Or_Swollen_Inguinal_LymphNodes_concept_Id =	165813;

	// KNOWLEDGE ASSESSMENT
	public final int PreviouslyTestedHIVNegative_concept_Id = 165799;
	public final int ClientPregnant_concept_Id = 1434;
	public final int ClientInformedAboutHIVTransmissionRoutes_concept_Id = 165801;
	public final int ClientInformedAboutRiskFactors_concept_Id = 165802;
	public final int ClientInformedAboutPreventingHIV_concept_Id = 165804;
	public final int ClientInformedAboutPossibleTestResults_concept_Id = 165884;
	public final int InformedConsentForHIVTestingGiven_concept_Id = 1710;

	//HIVRISKASSESSMENT
	public final int EverHadSexualIntercourse_concept_Id = 165800;
	public final int BloodTransfussionInLast3Months_concept_Id = 1063;
	public final int UnprotectedSexWithCasualPartnerinLast3Months_concept_Id = 159218;
	public final int UnprotectedSexWithRegularPartnerInLast3Months_concept_Id = 165803;
	public final int STIInLast3Months_concept_Id = 164809;
	public final int MoreThan1SexPartnerDuringLast3Months_concept_Id = 165806;

	//CLINICALTBSCREENING
	public final int CurrentCough_concept_Id = 143264;
	public final int WeigthLoss_concept_Id = 823;
	public final int Fever_concept_Id = 140238;
	public final int NightSweats_concept_Id = 133027;

	//POST-TEST COUNSELLING
	public final int TestedForHIVBeforeWithinThisYear_concept_Id = 165881;
	public final int HIVRequestAndResultFormSignedByTester_concept_Id = 165818;
	public final int HIVRequestAndResultFormFilledWithCTIForm_concept_Id = 165819;
	public final int ClientRecievedHIVTestResult_concept_Id = 164848;
	public final int PostTestCounsellingDone_concept_Id = 159382;
	public final int RiskReductionPlanDeveloped_concept_Id = 165820;
	public final int PostTestDisclosurePlanDeveloped_concept_Id = 165821;
	public final int WillBringPartnerForHIVTesting_concept_Id = 165822;
	public final int WillBringOwnChildrenForHIVTesting_concept_Id = 165823;
	public final int ProvidedWithInformationOnFPandDualContraception_concept_Id = 1382;
	public final int ClientOrPartnerUseFPMethodsOtherThanCondoms_concept_Id = 165883;
	public final int ClientOrPartnerUseCondomsAsOneFPMethods_concept_Id = 5571;
	public final int CorrectCondomUseDemonstrated_concept_Id = 165823;
	public final int CondomsProvidedToClient_concept_Id = 159777;
	public final int ClientReferredToOtherServices_concept_Id = 1648;

	//HIV TEST RESULT
	public final int ScreeningTestDate_concept_Id = 165844;
	public final int ScreeningTestResult_concept_Id = 165840;
	public final int ConfirmatoryTestDate_concept_Id = 165845;
	public final int ConfirmatoryTestResult_concept_Id = 165841;
	public final int TieBreakerTestDate_concept_Id = 165846;
	public final int TieBreakerTestResult_concept_Id = 165842;
	public final int FinalHIVTestResult_concept_Id = 165843;

	//HIV RECENCY TEST RESULT
	public final int RecencyTestName_concept_Id = 165849;
	public final int RecencyTestDate_concept_Id = 165850;
	public final int RapidRecencyAssay_concept_Id = 165853;
	public final int ViralLoadTestDate_concept_Id = 165854;
	public final int ViralLoadTestResult_concept_Id = 165855;
	public final int FinalHIVRecentTestinResult_concept_Id = 165856;

	//OTHER TESTING SERVICES
	public final int SyphilisTestResult_concept_Id = 299;
	public final int HepBTestResult_concept_Id = 159430;
	public final int HepCTestResult_concept_Id = 161471;

	//PARTNER NOTIFICATION SERVICES
	public final int PartnerName_concept_Id = 161135;
	public final int PartnerGender_concept_Id = 165857;

	public HTSDictionary(){
		valueCodedDictionary.put(978,"Self");
		valueCodedDictionary.put(160529,"Tuberculosis Visit");
		valueCodedDictionary.put(160546,"STI");
		valueCodedDictionary.put(5271,"FP");
		valueCodedDictionary.put(160542,"OPD");
		valueCodedDictionary.put(161629,"Ward");
		valueCodedDictionary.put(165788,"Blood Bank");
		valueCodedDictionary.put(5622,"Others");

		valueCodedDictionary.put(1065,"Yes");
		valueCodedDictionary.put(1066,"No");

		valueCodedDictionary.put(165792,"Individual");
		valueCodedDictionary.put(165789,"Couple");
		valueCodedDictionary.put(165885,"Previously Self Tested");
		valueCodedDictionary.put(1057,"Single");
		valueCodedDictionary.put(5555,"Married");
		valueCodedDictionary.put(1058,"Divorced");
		valueCodedDictionary.put(1056,"Separated");
		valueCodedDictionary.put(1060,"Cohabiting");
		valueCodedDictionary.put(1059,"Widowed");
		valueCodedDictionary.put(165796,"Biological");
		valueCodedDictionary.put(165797,"Sexual");
		valueCodedDictionary.put(165795,"Social");
		valueCodedDictionary.put(1067,"Unknown");
		valueCodedDictionary.put(165815,"Not previously tested");
		valueCodedDictionary.put(165816,"Previously tested negative");
		valueCodedDictionary.put(165817,"Previously tested positive in HIV Care");
		valueCodedDictionary.put(165882,"Previously tested positive not in HIVCare");
		valueCodedDictionary.put(1228,"REACTIVE");
		valueCodedDictionary.put(1229,"NON-REACTIVE");
		valueCodedDictionary.put(703,"POSITIVE");
		valueCodedDictionary.put(664,"NEGATIVE");
		valueCodedDictionary.put(165851,"Long Term");
		valueCodedDictionary.put(165852,"Recent");
	}


	public HIVTestingEncounterType createHIVTestingEncounter(Patient pts, Encounter enc)
			throws DatatypeConfigurationException {

		this.patient = pts;
		this.encounter = enc;
		this.obs = new ArrayList<>(enc.getAllObs(false));

		HIVTestingEncounterType hivTestingEncounterType = new HIVTestingEncounterType();

		HIVRecencyTestResultType hivRecencyTestResultType = createHIVRecencyTestResult();
		hivTestingEncounterType.setHIVRecencyTestResult(hivRecencyTestResultType);

		HIVTestResultsType hivTestResultsType = createHIVTestResult();
		hivTestingEncounterType.setHIVTestResult(hivTestResultsType);

		OtherTestingServicesType otherTestingServicesType = createOtherTestingService();
		hivTestingEncounterType.setOtherTestingServices(otherTestingServicesType);

		PostTestCounsellingType postTestCounselling = createPostTestCounselling();
		hivTestingEncounterType.setPostTestCounselling(postTestCounselling);

		HIVPreTestCounsellingType hivPreTestCounsellingType = createPreTestCounselling();
		hivTestingEncounterType.setPreTestCounselling(hivPreTestCounsellingType);

		HIVTestDetailsType testDetailType = createTestDetail();
		hivTestingEncounterType.setTestDetail(testDetailType);
		return hivTestingEncounterType;
	}

	private HIVTestDetailsType createTestDetail() throws DatatypeConfigurationException {

		HIVTestDetailsType hivTestDetailsType = new HIVTestDetailsType();

		Obs obs;
		obs = Utils.extractObs(client_Code_concept_Id, this.obs);
		if(obs !=null){
			hivTestDetailsType.setClientCode(obs.getValueText());
		}

		obs = Utils.extractObs(Date_Of_Visit_concept_Id, this.obs);
		if(obs !=null){
			hivTestDetailsType.setDateOfVisit(Utils.getXmlDate(obs.getValueDatetime()));
		}

		 obs = Utils.extractObs(Setting_concept_Id, this.obs);
		if(obs !=null){
			hivTestDetailsType.setSetting(obs.getValueText());
		}

		 obs = Utils.extractObs(Referred_From_concept_Id, this.obs);
		if(obs !=null && obs.getValueCoded() !=null){
			String referred_from = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "");
			hivTestDetailsType.setReferredFrom(referred_from);
		}

		 obs = Utils.extractObs(First_Time_Visit_concept_Id, this.obs);
		if(obs !=null  && obs.getValueCoded() !=null){
			String first_time = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "");
			hivTestDetailsType.setFirstTimeVisit(first_time);
		}
		 obs = Utils.extractObs(Type_Of_Session_concept_Id, this.obs);
		if(obs !=null  && obs.getValueCoded() !=null){
			String session = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "");
			hivTestDetailsType.setTypeOfSession(session);
		}

		obs = Utils.extractObs(Marital_Status_concept_Id, this.obs);
		if(obs !=null  && obs.getValueCoded() !=null){
			String marital = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "");
			hivTestDetailsType.setMaritalStatus(marital);
		}

		obs = Utils.extractObs(Number_Of_Children_Less_Than_Five_concept_Id, this.obs);
		if(obs !=null){
			hivTestDetailsType.setNumberOfChildrenLessThanFive(obs.getValueNumeric().intValue());
		}

		obs = Utils.extractObs(Number_Of_Wives_Or_Co_wives_concept_Id, this.obs);
		if(obs !=null){
			hivTestDetailsType.setNumberOfWivesOrCowives(obs.getValueNumeric().intValue());
		}

		obs = Utils.extractObs(Client_Identified_From_Index_concept_Id, this.obs);
		if(obs !=null  && obs.getValueCoded() !=null){
			String client_source = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "");
			hivTestDetailsType.setClientIdentiedFromIndex(client_source);
		}

		obs = Utils.extractObs(Index_Type_concept_Id, this.obs);
		if(obs !=null  && obs.getValueCoded() !=null){
			String index_type = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "");
			hivTestDetailsType.setIndexType(index_type);
		}

		obs = Utils.extractObs(Index_Client_ID_concept_Id, this.obs);
		if(obs !=null){
			hivTestDetailsType.setIndexClientID(obs.getValueText());
		}
		return hivTestDetailsType;
	}

	private HIVPreTestCounsellingType createPreTestCounselling() {

		HIVPreTestCounsellingType hivPreTestCounsellingType = new HIVPreTestCounsellingType();
		ClinicalTBScreeningType clinicalTBScreeningType = createClinicalTBScreeningType();
		hivPreTestCounsellingType.setClinicalTBScreening(clinicalTBScreeningType);

		KnowledgeAssessmentType knowledgeAssessmentType = createKnowledgeAssessmentType();
		hivPreTestCounsellingType.setKnowledgeAssessment(knowledgeAssessmentType);

		HIVRiskAssessmentType hivRiskAssessmentType = createHIVRiskAssessmentType();
		hivPreTestCounsellingType.setHIVRiskAssessment(hivRiskAssessmentType);

		SyndromicSTIScreeningType syndromicSTIScreeningType = createSyndromicSTIScreeningType();
		hivPreTestCounsellingType.setSyndromicSTIScreening(syndromicSTIScreeningType);

		return hivPreTestCounsellingType;
	}

	private SyndromicSTIScreeningType createSyndromicSTIScreeningType() {

		SyndromicSTIScreeningType syndromicSTIScreeningType = new SyndromicSTIScreeningType();

		Obs obs;
		obs = Utils.extractObs(Genital_Sore_Or_Swollen_Inguinal_LymphNodes_concept_Id, this.obs);
		if(obs !=null){
			syndromicSTIScreeningType.setGenitalSoreOrSwollenInguinalLymphNodes(obs.getValueAsBoolean());
		}

		obs = Utils.extractObs(Lower_Abdominal_Pains_With_Or_Without_Vaginal_Discharge_concept_Id, this.obs);
		if(obs !=null && obs.getValueCoded() !=null){
			String yes_no = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "no");
			syndromicSTIScreeningType.setLowerAbdominalPainsWithOrWithoutVaginalDischarge(yes_no == "yes" ? true : false);
		}

		obs = Utils.extractObs(Urethral_Discharge_Or_Burning_When_Urinating_concept_Id, this.obs);
		if(obs !=null){
			syndromicSTIScreeningType.setUrethralDischargeOrBurningWhenUrinating(obs.getValueAsBoolean());
		}

		obs = Utils.extractObs(Scrotal_Swelling_And_Pain_concept_Id, this.obs);
		if(obs !=null){
			syndromicSTIScreeningType.setScrotalSwellingAndPain(obs.getValueAsBoolean());
		}

		obs = Utils.extractObs(Vaginal_Discharge_Or_Burning_When_Urinating_concept_Id, this.obs);
		if(obs !=null){
			String yes_no = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "no");

			syndromicSTIScreeningType.setVaginalDischargeOrBurningWhenUrinating(yes_no == "yes" ? true : false);
		}

		return syndromicSTIScreeningType;
	}

	private HIVRiskAssessmentType createHIVRiskAssessmentType() {
		HIVRiskAssessmentType hivRiskAssessmentType = new HIVRiskAssessmentType();

		Obs obs;
		obs = Utils.extractObs(BloodTransfussionInLast3Months_concept_Id, this.obs);
		if(obs !=null && obs.getValueCoded() !=null){
			String yes_no = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "No");
			hivRiskAssessmentType.setBloodTransfussionInLast3Months(yes_no == "Yes" ? true : false);
		}

		obs = Utils.extractObs(EverHadSexualIntercourse_concept_Id, this.obs);
		if(obs !=null && obs.getValueCoded() !=null){
			String yes_no = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "no");
			hivRiskAssessmentType.setEverHadSexualIntercourse(yes_no == "Yes" ? true : false);
		}

		obs = Utils.extractObs(MoreThan1SexPartnerDuringLast3Months_concept_Id, this.obs);
		if(obs !=null && obs.getValueCoded() !=null){
			String yes_no = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "no");
			hivRiskAssessmentType.setMoreThan1SexPartnerDuringLast3Months(yes_no == "Yes" ? true : false);
		}

		obs = Utils.extractObs(STIInLast3Months_concept_Id, this.obs);
		if(obs !=null && obs.getValueCoded() !=null){
			String yes_no = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "no");
			hivRiskAssessmentType.setSTIInLast3Months(yes_no == "Yes" ? true : false);
		}

		obs = Utils.extractObs(UnprotectedSexWithCasualPartnerinLast3Months_concept_Id, this.obs);
		if(obs !=null && obs.getValueCoded() !=null){
			String yes_no = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "no");
			hivRiskAssessmentType.setUnprotectedSexWithCasualPartnerinLast3Months(yes_no == "Yes" ? true : false);
		}

		obs = Utils.extractObs(UnprotectedSexWithRegularPartnerInLast3Months_concept_Id, this.obs);
		if(obs !=null && obs.getValueCoded() !=null){
			String yes_no = valueCodedDictionary.getOrDefault(obs.getValueCoded().getConceptId(), "no");
			hivRiskAssessmentType.setUnprotectedSexWithRegularPartnerInLast3Months( yes_no == "Yes" ? true : false);
		}
		return hivRiskAssessmentType;
	}

	private KnowledgeAssessmentType createKnowledgeAssessmentType() {
		return null;
	}

	private ClinicalTBScreeningType createClinicalTBScreeningType() {
		return null;
	}

	private PostTestCounsellingType createPostTestCounselling() {
		return null;
	}

	private OtherTestingServicesType createOtherTestingService() {
		return null;
	}

	private HIVTestResultsType createHIVTestResult() {
		return null;
	}

	private HIVRecencyTestResultType createHIVRecencyTestResult() {
		return null;
	}
}
