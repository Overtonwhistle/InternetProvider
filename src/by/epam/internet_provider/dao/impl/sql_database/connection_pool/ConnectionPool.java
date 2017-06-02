package by.epam.internet_provider.dao.impl.sql_database.connection_pool;

import java.sql.Connection;
import java.sql.SQLException;

import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolException;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolInitException;

public interface ConnectionPool {

	public void initPool() throws ConnectionPoolInitException;

	public Connection getConnection() throws ConnectionPoolException;

	public void releaseConnection(Connection connection) throws SQLException;

	public int getFreeConnections();

	public int getBusyConnections();

	public void closeAllConnections() throws ConnectionPoolException;

}
