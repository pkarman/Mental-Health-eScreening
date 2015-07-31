package gov.va.escreening.repository;

import java.io.Serializable;
import java.util.List;

public interface RepositoryInterface<T extends Serializable> {

	T findOne(final int id);

	List<T> findAll();

	void create(final T entity);

	T update(final T entity);

	void delete(final T entity);

	void deleteById(final int entityId);
	
	void flush();
	
	void commit();
}
