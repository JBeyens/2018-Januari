package database;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class GenericDAO<T> {
	protected EntityManagerFactory factory;
	protected Session session;
	protected Transaction transaction;
	
	private Class<T> genericClass;

	public GenericDAO(Class<T> genericClass) {
		this.genericClass = genericClass;
		
		factory = EntityMgrFactory.getFactory(genericClass.getSimpleName());
	}
	
	@SuppressWarnings("unchecked")
	public T findOne(final int id) {
		session = factory.openSession();
	    Object obj = null;
	      try {
	         obj = (T)session.get(genericClass, id);
	      } catch (HibernateException e) {	         
	         e.printStackTrace(); 
	      } finally {
	         session.clear(); 
	      }
		return (T) obj;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		session = factory.openSession();
		transaction = null;
	    List<T> list = null;
	      try {
	         transaction = session.beginTransaction();
	         
	         list = (List<T>) session.createQuery("FROM " + genericClass.getName()).list();
	         
	         transaction.commit();
	      } catch (HibernateException e) {	         
	         e.printStackTrace(); 
	      } finally {
	         session.clear(); 
	      }
		return list;
	}

	public void create(final T entity) {
		
		Session session = factory.openSession();
		
		try {
			transaction = session.beginTransaction();
			
			session.save(entity);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			
			e.printStackTrace();
		} finally {
			session.clear();
		}
	}

	public void update(final T entity) {
		session = factory.openSession();
	      
	      try {
	         transaction = session.beginTransaction();
			 session.update(entity); 
	         transaction.commit();
	      } catch (HibernateException e) {
	         if (transaction!=null) 
	        	 transaction.rollback();
	         
	         e.printStackTrace(); 
	      } finally {
	         session.clear(); 
	      }
	}

	public void delete(final T entity) {
		session = factory.openSession();
		
		try {
			transaction = session.beginTransaction();
			
			session.delete(entity);
			session.flush();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			
			e.printStackTrace();
		} finally{
			session.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteById(final int entityId) {
		session = factory.openSession();
		Object obj = null;
		try {
			transaction = session.beginTransaction();
			
			obj = (T)session.load(genericClass, entityId);
			session.delete(obj);
			session.flush();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			
			e.printStackTrace();
		} finally{
			session.clear();
		}
		
	}
}
