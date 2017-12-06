package database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.List;

import modelPersistent.Remote;

public class RemoteDAO{
	private SessionFactory factory;
	private Session session;
	private Transaction transaction;
	
	public RemoteDAO(){
		factory = new Configuration().configure().addResource("remote.hbm.xml").buildSessionFactory();
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
	
	public void UpdateRemote(){
		
	}
	
	public Remote ReadRemoteByID(int id){
		return null;
	}

	public List ReadAll(){
		return null;
	}
	
}
