package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDAO {
	protected SessionFactory factory;
	protected Session session;
	protected Transaction transaction;
	
	public AbstractDAO(){
		
	}
}
