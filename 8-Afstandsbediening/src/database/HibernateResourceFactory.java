package database;

import java.rmi.Remote;

import modelPersistent.Address;
import values.DefaultSettings;

public class HibernateResourceFactory {
	public static String getHibernateResourceFile(Class className){
		String string = null;
		
		if(className.isInstance(Remote.class))
			return DefaultSettings.resourceRemote.getValue();
		
		if(className.isInstance(Address.class))
			return DefaultSettings.resourceAddress.getValue();
		
		return string;
	}
}
