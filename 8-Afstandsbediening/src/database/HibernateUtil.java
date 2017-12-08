package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
	private static StandardServiceRegistryBuilder ssrb;
	private static StandardServiceRegistry ssr;
	private static SessionFactory sessionFactory;


	public static SessionFactory buildSessionFactory(String resourcePath) {
		if (sessionFactory == null) {
			try {
				Configuration cfg = new Configuration().configure().addResource(resourcePath);
				ssrb = new StandardServiceRegistryBuilder();
				ssrb.applySettings(cfg.getProperties());
				
				ssr = ssrb.build();
				sessionFactory = cfg.buildSessionFactory(ssr);
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		return sessionFactory;
	}

}
