package by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception;

public class ConnectionPoolEmptyPoolException extends ConnectionPoolException {
	private static final long serialVersionUID = 1L;

	public ConnectionPoolEmptyPoolException(String message, Exception e) {
		super(message, e);
	}
}
