package fr.pinguet62.dictionary.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import fr.pinguet62.dictionary.model.Language;

/** The DAO for {@link Language}. */
@Service
public final class LanguageDao {

	/** The {@link EntityManager}. */
	private final EntityManager em = Persistence.createEntityManagerFactory(
			"test").createEntityManager();

	/**
	 * Get number of {@link Language}.
	 *
	 * @return The number of {@link Language}.
	 */
	public long count() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(Language.class)));
		return em.createQuery(cq).getSingleResult();
	}

	/**
	 * Create new {@link Language}.
	 *
	 * @param language
	 *            The {@link Language}.
	 * @return The code.
	 */
	public String create(Language language) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		em.persist(language);

		tx.commit();

		return language.getCode();
	}

	/**
	 * Delete the {@link Language}.
	 *
	 * @param language
	 *            The {@link Language} to delete.
	 */
	public void delete(Language language) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		language = em.merge(language);
		em.remove(language);

		tx.commit();
	}

	/** Delete all {@link Language} of table. */
	public void deleteAll() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Language> cd = cb.createCriteriaDelete(Language.class);
		cd.from(Language.class);
		em.createQuery(cd).executeUpdate();

		tx.commit();
	}

	/**
	 * Get the {@link Criteria} of {@link Language}.
	 *
	 * @return The {@link Criteria}.
	 */
	// public Criteria getCriteria() {
	// Session session = HibernateUtils.getSession();
	// return session.createCriteria(Language.class);
	// }

	/**
	 * Get the {@link Language} by id. <br/>
	 * Detach object of database.
	 *
	 * @param code
	 *            The {@link Language} id.
	 * @return The {@link Language}, {@code null} if not found.
	 */
	public Language get(String code) {
		Language language = em.find(Language.class, code);
		if (language == null)
			return null;

		em.detach(language);
		return language;
	}

	/**
	 * Get all {@link Language}.
	 *
	 * @return The list of {@link Language}s.
	 */
	public List<Language> list() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Language> cq = cb.createQuery(Language.class);
		Root<Language> rootEntry = cq.from(Language.class);
		CriteriaQuery<Language> all = cq.select(rootEntry);
		TypedQuery<Language> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	/**
	 * Update a {@link Language}.
	 *
	 * @param language
	 *            The {@link Language} to update.
	 * @return The updated {@link Language}.
	 */
	public Language update(Language language) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		language = em.merge(language);
		em.persist(language);
		// TODO em.detach(language);

		tx.commit();

		return language;
	}

}
