package by.epam.internet_provider.dao.impl.sql_database.connection_pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolException;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolInitException;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.impl.ConnectionPoolOne;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.pool_factory.ConnectionPoolFactory;

public class Inspector {

	public static void main(String[] args) throws ConnectionPoolException, SQLException, ConnectionPoolInitException {

		// ConnectionPoolOne pool = ConnectionPoolOne.getInstance();
		// pool.initPoolData();

		ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getIstance();
		ConnectionPool pool = poolFactory.getConnectionPool();

		Connection con = pool.getConnection();
		Connection con1 = pool.getConnection();
		Connection con2 = pool.getConnection();

		Statement st = null;
		ResultSet rs = null;

		st = con.createStatement();
		System.out.println(pool.getFreeConnections());
		rs = st.executeQuery("select * from technology");

		while (rs.next()) {
			System.out.println("id: " + rs.getInt(1) + " title: " + rs.getString(2) + " need eq:"
					+ rs.getBoolean(3));
		}
		pool.releaseConnection(con);
		// System.out.println(rs.getMetaData());
		// pool.closeConnection(con, st, rs);
		System.out.println(pool.getFreeConnections());
		con.close();
		System.out.println(pool.getFreeConnections());
		con1.close();
		System.out.println(pool.getFreeConnections());
		con2.close();
		// con1.close();

		System.out.println(pool.getFreeConnections());
		pool.closeAllConnections();
	}

}
