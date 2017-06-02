package by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception;

//public class ConnectionPoolInitException extends ConnectionPoolException {
	public class ConnectionPoolInitException extends Exception {
	private static final long serialVersionUID = 1L;

	public ConnectionPoolInitException (String message, Exception e) {
		super(message, e);
	}
}
