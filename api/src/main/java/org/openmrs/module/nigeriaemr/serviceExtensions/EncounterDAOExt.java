package org.openmrs.module.nigeriaemr.serviceExtensions;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.api.db.hibernate.HibernateEncounterDAO;

import java.util.List;

public class EncounterDAOExt extends HibernateEncounterDAO {

	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;

	/**
	 * Set session factory
	 *
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	private List<Encounter> getEncounterByPatientAndEncounterType(Patient patient, EncounterType encounterType) {


		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);

		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("encounterType", encounterType));
		criteria.add(Restrictions.eq("voided", false));

		return criteria.list();
	}

}
