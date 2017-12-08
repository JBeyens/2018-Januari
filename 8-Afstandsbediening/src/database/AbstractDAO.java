package database;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class AbstractDAO<T> {
	protected SessionFactory factory;
	protected Session session;
	protected Transaction transaction;
	
	private Class<T> genericClass;

	public AbstractDAO(Class<T> genericClass) {
		this.genericClass = genericClass;
		factory = new Configuration().configure().addResource(ResourceHibernateFactory.getResourceFile(genericClass)).buildSessionFactory();
	}
	
	
	public T findOne(final int id) {
		session = factory.openSession();
	    Object obj = null;
	      try {
	         obj = (T)session.get(genericClass, id);
	      } catch (HibernateException e) {	         
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return (T) obj;
	}

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
	         session.close(); 
	      }
		return list;
	}

	public void create(final T entity) {
		session = factory.openSession();
		
		try {
			transaction = session.beginTransaction();
			
			session.save(entity);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			
			e.printStackTrace();
		} finally{
			session.close();
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
	         session.close(); 
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
			session.close();
		}
	}

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
			session.close();
		}
		
	}
}
