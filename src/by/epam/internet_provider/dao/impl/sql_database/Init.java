package by.epam.internet_provider.dao.impl.sql_database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import by.epam.internet_provider.dao.impl.sql_database.connection_pool.ConnectionPool;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolInitException;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.pool_factory.ConnectionPoolFactory;

@WebServlet(description = "Pool initialization", urlPatterns = { "/Init" })
public class Init extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Init() throws ConnectionPoolInitException {
        super();
        final ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getIstance();
    	ConnectionPool pool = poolFactory.getConnectionPool();
    	pool.initPool();
        
    }


}
