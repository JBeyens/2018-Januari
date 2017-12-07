package database;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;

import modelPersistent.Remote;
import values.DefaultSettings;

public class RemoteDAO extends AbstractDAO{

	public RemoteDAO(){
		super();
		factory = new Configuration().configure().addResource(DefaultSettings.resourceRemote.getValue()).buildSessionFactory();
	}

	public void AddRemote(String serialNumber, long frequency){
		session = factory.openSession();
		
		try {
			transaction = session.beginTransaction();
			
			Remote remote = new Remote(serialNumber, frequency);
			session.save(remote);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			
			e.printStackTrace();
		} finally{
			session.close();
		}
	}
	
	public void DeleteRemote(Integer remoteID){
		session = factory.openSession();
		
		try {
			transaction = session.beginTransaction();
			
			Remote remote = (Remote) session.load(Remote.class, remoteID);
			session.delete(remote);
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
	
	public void UpdateRemoteFrequency(Integer remoteID, long frequency){
		session = factory.openSession();
	      
	      try {
	         transaction = session.beginTransaction();
	         Remote remote = (Remote)session.get(Remote.class, remoteID); 
	         remote.setFrequency(frequency);
			 session.update(remote); 
	         transaction.commit();
	      } catch (HibernateException e) {
	         if (transaction!=null) 
	        	 transaction.rollback();
	         
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }	
	}
	
	public Remote ReadRemoteByID(Integer remoteID){
		session = factory.openSession();
	    Remote remote = null;
	      try {
	         remote = (Remote)session.load(Remote.class, remoteID);
	         Hibernate.initialize(remote);
	      } catch (HibernateException e) {	         
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return remote;
	}

	@SuppressWarnings("unchecked")
	public List<Remote> ReadAll(){
		session = factory.openSession();
		transaction = null;
	    List<Remote> list = null;
	      try {
	         transaction = session.beginTransaction();
	         
	         list = (List<Remote>) session.createQuery("FROM Remote").list();
	         
	         transaction.commit();
	      } catch (HibernateException e) {	         
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return list;
	}
	
}
