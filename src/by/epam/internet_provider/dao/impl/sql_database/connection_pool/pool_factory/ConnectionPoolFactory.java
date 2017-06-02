package by.epam.internet_provider.dao.impl.sql_database.connection_pool.pool_factory;

import by.epam.internet_provider.dao.impl.sql_database.connection_pool.ConnectionPool;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.impl.ConnectionPoolOne;

public final class ConnectionPoolFactory {

	private final static ConnectionPoolFactory instance = new ConnectionPoolFactory();
	private final static ConnectionPool connectionPoolOne = ConnectionPoolOne.getInstance();

	private ConnectionPoolFactory() {
	}

	public static ConnectionPoolFactory getIstance() {
		// connectionPoolOne.initPool();
		return instance;
	}

	public ConnectionPool getConnectionPool() {
		return connectionPoolOne;
	}
}
