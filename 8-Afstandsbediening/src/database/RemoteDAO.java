package database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.dataobjects.Remote;
import values.DefaultSettings;

public class RemoteDAO{
	private SessionFactory factory;
	private Session session;
	private Transaction transaction;
	
	public RemoteDAO(){
		factory = new Configuration().configure(DefaultSettings.hibernateConfigPath.getValue()).buildSessionFactory();
	}

	public void AddRemote(String serialNumber, long frequency){
		session = factory.openSession();
		
		try {
			transaction = session.beginTransaction();
			
			Remote remote = new Remote(serialNumber, frequency);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			
			e.printStackTrace();
		} finally{
			session.close();
		}
	}
	
	public void DeleteRemote(){
		
	}
	
	public void UpdateRemote(){
		
	}
	
	public Remote ReadRemoteByID(int id){
		return null;
	}
	
}
