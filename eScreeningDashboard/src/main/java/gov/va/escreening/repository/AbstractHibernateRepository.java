package gov.va.escreening.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class AbstractHibernateRepository<T extends Serializable> implements RepositoryInterface<T> {

	final protected Logger logger = LoggerFactory.getLogger(getClass());

	private Class<T> clazz;

	@PersistenceContext
	protected EntityManager entityManager;

	protected final void setClazz(final Class<T> clazzToSet) {
		clazz = clazzToSet;
	}

	public final T findOne(final int id) {
		return entityManager.find(clazz, id);
	}

	public final List<T> findAll() {
		return _findAll();
	}

	protected List<T> _findAll() {
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	public final void create(final T entity) {
		// Persist is being used instead of 'save'. PK field will not be set immediately.
		entityManager.persist(entity);
	}

	public final T update(final T entity) {
		return entityManager.merge(entity);
	}

	public final void delete(final T entity) {
		entityManager.remove(entity);
	}

	public final void deleteById(final int entityId) {
		final T entity = findOne(entityId);
		delete(entity);
	}

	public final void flush() {
		entityManager.flush();
	}

	public final void commit() {
		entityManager.flush();
	}
}
