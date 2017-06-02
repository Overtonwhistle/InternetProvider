package by.epam.internet_provider.dao.impl.sql_database.connection_pool.database_resource_manager;

import java.util.ResourceBundle;

public class DBResourceManager {

	private final static DBResourceManager instance = new DBResourceManager();
	private ResourceBundle bundle = ResourceBundle.getBundle("database");

	public static DBResourceManager getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return bundle.getString(key);
	}

}
