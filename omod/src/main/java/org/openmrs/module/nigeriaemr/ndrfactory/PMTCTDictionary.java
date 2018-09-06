package org.openmrs.module.nigeriaemr.ndrfactory;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.nigeriaemr.model.ndr.AntenatalRegistrationType;
import org.openmrs.module.nigeriaemr.model.ndr.ChildType;
import org.openmrs.module.nigeriaemr.model.ndr.DeliveryEncounterType;
import org.openmrs.module.nigeriaemr.model.ndr.PartnerDetailType;
import org.openmrs.module.nigeriaemr.model.ndr.PregnancyEncounterType;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PMTCTDictionary {
	
	public PregnancyEncounterType createPregnancyRecord(Patient pts,Encounter enc) {
		
		PregnancyEncounterType pregnancyEncounter = new PregnancyEncounterType();

		 List<Encounter> registrationEncounter = Utils.getEncounterByPatientAndEncounterTypeId(pts,0);

		 if(registrationEncounter !=null){

		 }

		 List<Obs> ancRegistrationObs = new ArrayList<>(enc.getAllObs());

		AntenatalRegistrationType antenatalRegistration = createAntenatalRecord(pts, enc, ancRegistrationObs);
		PartnerDetailType partnerDetail = createPartnerDetail();
		DeliveryEncounterType deliveryEncounter = createDeliveryRecord();
		List<ChildType> childRecords = createChildRecords();
		
		if (antenatalRegistration == null && partnerDetail == null && deliveryEncounter == null
		        && (childRecords == null || childRecords.size() == 0)) {
			return null;
		}
		
		if (antenatalRegistration != null) {
			pregnancyEncounter.setAntenatalRegistration(antenatalRegistration);
		}
		if (partnerDetail != null) {
			pregnancyEncounter.setPartnerDetail(partnerDetail);
		}
		if (deliveryEncounter != null) {
			pregnancyEncounter.setDeliveryEncounter(deliveryEncounter);
		}
		if (childRecords != null && childRecords.size() > 0) {
			pregnancyEncounter.getChild().addAll(childRecords);
		}
		return pregnancyEncounter;
	}
	
	private AntenatalRegistrationType createAntenatalRecord(Patient pts, Encounter enc, List<Obs> ancRegistrationObs) {
		
		AntenatalRegistrationType antenatalRegistration = new AntenatalRegistrationType();
		
		return antenatalRegistration;
	}
	
	private DeliveryEncounterType createDeliveryRecord() {
		DeliveryEncounterType deliveryEncounter = new DeliveryEncounterType();
		
		return deliveryEncounter;
	}
	
	private PartnerDetailType createPartnerDetail() {
		PartnerDetailType detailType = new PartnerDetailType();
		
		return detailType;
	}
	
	private List<ChildType> createChildRecords(){
		List<ChildType> children = new ArrayList<>();

		return children;

	}
}
