package database;

import java.util.List;

public interface GenericDAO<T> {	
	List<T> findAll();
	T findById(long id);
	boolean update(T entity);
	boolean delete(T entity);
	void create(T entity);
}
