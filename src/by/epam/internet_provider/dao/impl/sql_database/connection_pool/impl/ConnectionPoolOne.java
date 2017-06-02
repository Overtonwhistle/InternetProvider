package by.epam.internet_provider.dao.impl.sql_database.connection_pool.impl;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;

import by.epam.internet_provider.dao.impl.sql_database.connection_pool.ConnectionPool;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.database_resource_manager.DBParameter;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.database_resource_manager.DBResourceManager;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolException;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolInitException;

public final class ConnectionPoolOne implements ConnectionPool {
	private Logger logger = Logger.getLogger(getClass());
	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConQueue;
	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;
	private static final ConnectionPoolOne INSTANCE = new ConnectionPoolOne();

	public static ConnectionPoolOne getInstance() {
		return INSTANCE;
	}

	private ConnectionPoolOne() {
		DBResourceManager dbResourseManager = DBResourceManager.getInstance();
		this.driverName = dbResourseManager.getValue(DBParameter.DB_DRIVER);
		this.url = dbResourseManager.getValue(DBParameter.DB_URL);
		this.user = dbResourseManager.getValue(DBParameter.DB_USER);
		this.password = dbResourseManager.getValue(DBParameter.DB_PASSWORD);

		try {
			this.poolSize = Integer.parseInt(dbResourseManager.getValue(DBParameter.DB_POLL_SIZE));
		} catch (NumberFormatException e) {
			logger.warn("Wrong pool size value, set to default (5).");
			poolSize = 5;
		}

		/*
		 * try { this.initPool(); } catch (ConnectionPoolInitException e) {
		 * logger.error("Wrong pool size value, set to default (5).");
		 * e.printStackTrace(); }
		 */
	}

	@Override
	public void initPool() throws ConnectionPoolInitException {
		try {
			Class.forName(driverName);
			givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
			connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				PooledConnection pooledConnection = new PooledConnection(connection);
				connectionQueue.add(pooledConnection);
			}
		} catch (SQLException e) {
			logger.error("SQLException in ConnectionPool init().", e);
			throw new ConnectionPoolInitException("SQLException in ConnectionPool init().", e);
		} catch (ClassNotFoundException e) {
			logger.error("Can't find database driver class in ConnectionPool init().", e);
			throw new ConnectionPoolInitException("Can't find database driver class.", e);
		}
		logger.debug("Pool ititiated.");
	}

	@Override
	public int getFreeConnections() {
		return connectionQueue.size();
	}

	@Override
	public int getBusyConnections() {
		return givenAwayConQueue.size();
	}

	@Override
	public Connection getConnection() throws ConnectionPoolException {

		Connection connection = null;
		try {
			connection = connectionQueue.take();
			givenAwayConQueue.add(connection);
		} catch (InterruptedException e) {
			logger.error(
					"Error connecting to the data source (InterruptedException from inner queues)",
					e);
			throw new ConnectionPoolException("Error connecting to the data source.", e);
		}
		return connection;
	}

	@Override
	public void releaseConnection(Connection con) throws ConnectionPoolException {

		try {
			con.close();
		} catch (SQLException e) {
			logger.error("Connection isn't return to the pool.", e);
			throw new ConnectionPoolException("Connection isn't return to the pool.", e);
		}

	}

	@Override
	public void closeAllConnections() throws ConnectionPoolException {
		logger.info("Disposing pool...");
		clearConnectionQueue();
	}

	private void clearConnectionQueue() throws ConnectionPoolException {
		try {
			closeConnectionsQueue(givenAwayConQueue);
			closeConnectionsQueue(connectionQueue);
		} catch (SQLException e) {
			logger.error("Error closing the connection.");
			throw new ConnectionPoolException("Error closing the connection.", e);
		}
	}

	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;
		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).reallyClose();
		}
	}

	///// ==========================////// CONNECTION =================

	private class PooledConnection implements Connection {
		private Connection connection;

		public PooledConnection(Connection c) throws SQLException {
			this.connection = c;
			this.connection.setAutoCommit(true);
		}

		public void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public void close() throws SQLException {
			logger.info("Overrided close() - connection just returned in pool.");
			if (connection.isClosed()) {
				throw new ConnectionPoolException("Attempting to close closed connection.", null);
			}

			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}

			if (!givenAwayConQueue.remove(this)) {
				logger.error("Error deleting connection from the given away connections pool.");
				throw new ConnectionPoolException(
						"Error deleting connection from the given away connections pool.", null);
			}

			if (!connectionQueue.offer(this)) {
				logger.error("Error allocating connection in the pool.");
				throw new ConnectionPoolException("Error allocating connection in the pool.", null);
			}
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();
		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency,
				int resultSetHoldability) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency,
					resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency,
					resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
				throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
				throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames)
				throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,
				int resultSetConcurrency) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType,
				int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency,
					resultSetHoldability);
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public void abort(Executor arg0) throws SQLException {
			connection.abort(arg0);
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public void releaseSavepoint(Savepoint arg0) throws SQLException {
			connection.releaseSavepoint(arg0);
		}

		@Override
		public void rollback(Savepoint arg0) throws SQLException {
			connection.rollback(arg0);
		}

		@Override
		public void setClientInfo(Properties arg0) throws SQLClientInfoException {
			connection.setClientInfo(arg0);
		}

		@Override
		public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
			connection.setNetworkTimeout(arg0, arg1);
		}

		@Override
		public void setSchema(String arg0) throws SQLException {
			connection.setSchema(arg0);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
			connection.setTypeMap(arg0);
		}

	}
}
