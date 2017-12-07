package database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import values.DefaultSettings;

public abstract class AbstractDAO<T> {
	protected SessionFactory factory;
	protected Session session;
	protected Transaction transaction;
	
	private Class<T> genericClass;

	public AbstractDAO(Class<T> genericClass) {
		this.genericClass = genericClass;
		setSessionFactory();
	}
	
	private void setSessionFactory(){
		factory = new Configuration().configure().addResource(DefaultSettings.resourceRemote.getValue()).buildSessionFactory();
	}
	
	protected Session getCurrenSession(){
		return factory.getCurrentSession();
	}

	public T findOne(final int id) {
		session = factory.openSession();
		return (T) session.get(genericClass, id);
	}

	protected List<T> findAll() {
		return null;
	}

	protected void create(final T entity) {
	}

	protected T update(final T entity) {
		return entity;
	}

	protected void delete(final T entity) {
	}

	protected void deleteById(final long entityId) {
	}
}
