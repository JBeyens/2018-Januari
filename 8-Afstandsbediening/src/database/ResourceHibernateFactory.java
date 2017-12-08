package database;

import values.DefaultSettings;

public class ResourceHibernateFactory {

	public static String getResourceFile(Class<?> clazz) {
		String resourceFile = null;

		switch (clazz.getSimpleName()) {
		case "Remote":
			resourceFile = DefaultSettings.resourceRemote.getValue();
			break;
		case "Address":
			resourceFile = DefaultSettings.resourceAddress.getValue();
			break;

		}
		return resourceFile;
	}

}
