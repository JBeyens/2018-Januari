package database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.HibernateException;


/**
 * 	@Author Ben Vandevorst & Jef Beyens
	@Datum 09/12/2017
	@Project Afstandsbediening
	@Doel Generic DAO class to handle database operations. Can be extended if additional 
		methods are required
 */

public class GenericDAO<T> {
	protected EntityManagerFactory factory;
	protected Class<T> genericClass;

	public GenericDAO(Class<T> genericClass) {
		this.genericClass = genericClass;
		
		this.factory = EManagerFactory.getFactory();
	}
	
	
	/*
	 * EntityManager not thread safe
	 * Creating/Closing needed with every call
	 */
	
	@SuppressWarnings("unchecked")
	public T findOne(final int id) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
	    Object obj = null;
	    
	      try {
	    	  transaction.begin();
	          obj = (T) manager.find(genericClass, id);
	          transaction.commit();
	      } catch (HibernateException e) {
	    	  if(transaction != null)
	    		  transaction.rollback();
	         e.printStackTrace(); 
	      } finally {
	         manager.close(); 
	      }
		return (T) obj;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
	    List<T> list = null;
	      try {
	         transaction.begin();
	         
	         list = (List<T>) manager.createQuery("Select x FROM " + genericClass.getSimpleName() + " x").getResultList();
	         
	         transaction.commit();
	      } catch (HibernateException e) {
	    	  if(transaction != null)
	    		  transaction.rollback();
	         e.printStackTrace(); 
	      } finally {
	         manager.clear(); 
	      }
		return list;
	}
	
	public List<T> executeNamedQuery(String query){
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		
	    List<T> list = null;
		try {
			transaction.begin();
			
			list =(List<T>) manager.createNamedQuery(query, genericClass).getResultList();
			
			transaction.commit();
		} catch (HibernateException e) {
			if(transaction != null)
	    		  transaction.rollback();
	         e.printStackTrace(); 
		} finally{
			manager.clear();
		}
		return list;
	}

	public void create(final T entity) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		
		try {
			transaction.begin();
			manager.persist(entity);
			manager.flush();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();		
			e.printStackTrace();
		} finally {
			manager.clear();
		}
	}

	public T update(T entity) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
	      
	      try {
	         transaction.begin();
			 entity = manager.merge(entity);
			 manager.flush();
	         transaction.commit();
	      } catch (HibernateException e) {
	         if (transaction!=null) 
	        	 transaction.rollback();      
	         e.printStackTrace(); 
	      } finally {
	         manager.clear(); 
	      }
	      return entity;
	}

	public void delete(final T entity) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		Object obj = null;
		
		try {
			transaction.begin();
			obj = (T) manager.merge(entity);
			manager.remove(obj);
			manager.flush();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();	
			e.printStackTrace();
		} finally{
			manager.clear();
		}
	}

	public void deleteById(final int entityId) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		Object obj = null;
		try {
			transaction.begin();
			
			obj = (T)manager.find(genericClass, entityId);
			manager.remove(obj);
			manager.flush();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			
			e.printStackTrace();
		} finally{
			manager.clear();
		}		
	}

	public void deleteAll() {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		Query query = manager.createNativeQuery("DELETE FROM " + genericClass.getSimpleName());
		
		try {
			transaction.begin();
			
			query.executeUpdate();
			
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			
			e.printStackTrace();
		} finally{
			manager.clear();
		}		
	}
	
}
