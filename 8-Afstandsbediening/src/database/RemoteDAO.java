package database;
import org.hibernate.HibernateException;

import modelPersistent.Remote;


public class RemoteDAO extends AbstractDAO<Remote>{

	public RemoteDAO(){
		super(Remote.class);
	}

	public void create(String serialNumber, long frequency){
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
	
	public void update(Integer remoteID, long frequency){
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

	
}
