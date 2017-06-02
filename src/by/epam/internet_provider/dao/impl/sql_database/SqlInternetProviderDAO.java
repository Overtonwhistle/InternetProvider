package by.epam.internet_provider.dao.impl.sql_database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epam.internet_provider.bean.Param.UserRole;
import by.epam.internet_provider.bean.User;
import by.epam.internet_provider.dao.InternetProviderDAO;
import by.epam.internet_provider.dao.exception.DAOException;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.ConnectionPool;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolException;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.exception.ConnectionPoolInitException;
import by.epam.internet_provider.dao.impl.sql_database.connection_pool.pool_factory.ConnectionPoolFactory;

public class SqlInternetProviderDAO implements InternetProviderDAO {
	private static final Logger logger = Logger.getLogger(SqlInternetProviderDAO.class);
	public final static ConnectionPoolFactory poolFactory = ConnectionPoolFactory.getIstance();
	static ConnectionPool pool = poolFactory.getConnectionPool();

	private List<User> getUsersList(String query) throws DAOException {

		List<User> usersList = new ArrayList<User>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = pool.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				User user = new User();

				user.setId(rs.getInt(1));
				user.setRole(UserRole.valueOf(rs.getString(2).toUpperCase()));
				user.setLogin(rs.getString(3));
				user.setPassword(rs.getString(4));
				user.setEmail(rs.getString(5));
				user.setFirstName(rs.getString(6));
				user.setLastName(rs.getString(7));
				user.setPassportNumber(rs.getString(8));
				user.setRegDate(rs.getDate(9));
				user.setMonthlyDataUsage(rs.getLong(10));
				user.setTotalDataUsage(rs.getLong(11));
				user.setAccountBallance(rs.getBigDecimal(12));
				user.setTariff(rs.getInt(13));

				usersList.add(user);
			}

		} catch (SQLException e) {
			logger.error("SQLException from getUsersList().", e);
			throw new DAOException("SQLException from getUsersList()", e);
		}

		finally {
			try {
				if (con != null) {
					pool.releaseConnection(con);
				}
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				logger.error("SQLException from getUsersWithNegativeBallance().", e);
				throw new DAOException("SQLException from getUsersWithNegativeBallance()", e);
			}
		}
		return usersList;

	}

	@Override
	public void initDAO() throws DAOException {
		logger.info("initDAO()");
		try {
			pool.initPool();
		} catch (ConnectionPoolInitException e) {
			logger.error("Error connecting to the data source.", e);
			throw new DAOException("Error connecting to the data source.", e);
		}
	}

	@Override
	public void dismissDAO() throws DAOException {
		logger.info("dismissDAO()");
		try {
			pool.closeAllConnections();
		} catch (ConnectionPoolException e) {
			logger.error("Error closing data source.", e);
			throw new DAOException("Error closing data source.", e);
		}

	}

	@Override
	public User getUserById(int userId) throws DAOException {
		logger.info("getUserById()");
		User user = new User();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			con = pool.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(
					"select u_id, u_role, u_login, u_password, u_email, u_first_name, u_last_name,"
							+ " u_passport_number, u_reg_date, u_monthly_data_usage, u_total_data_usage,"
							+ " u_account_ballance, tariff_t_id from user where u_id=" + userId);

			while (rs.next()) {
				user.setId(rs.getInt(1));
				user.setRole(UserRole.valueOf(rs.getString(2).toUpperCase()));
				user.setLogin(rs.getString(3));
				user.setPassword(rs.getString(4));
				user.setEmail(rs.getString(5));
				user.setFirstName(rs.getString(6));
				user.setLastName(rs.getString(7));
				user.setPassportNumber(rs.getString(8));
				user.setRegDate(rs.getDate(9));
				user.setMonthlyDataUsage(rs.getLong(10));
				user.setTotalDataUsage(rs.getLong(11));
				user.setAccountBallance(rs.getBigDecimal(12));
				user.setTariff(rs.getInt(13));
			}

		} catch (SQLException e) {
			logger.error("SQLException from getUserById().", e);
			throw new DAOException("SQLException from getUserById()", e);
		}

		finally {
			try {
				if (con != null) {
					pool.releaseConnection(con);
				}
				if (st != null) {
					st.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				logger.error("SQLException from getUserById().", e);
				throw new DAOException("SQLException from getUserById()", e);
			}
		}
		return user;
	}

	@Override
	public boolean addUser(User user) throws DAOException {
		logger.info("addUser()");
		Connection con = null;
		Statement st = null;
		PreparedStatement ps = null;

		try {
			con = pool.getConnection();
			st = con.createStatement();
			String sql = "INSERT INTO `internet_provider`.`user` (`u_role`, `u_login`, `u_password`,"
					+ " `u_email`, `u_first_name`, `u_last_name`, `u_passport_number`, `u_reg_date`,"
					+ " `u_monthly_data_usage`, `u_total_data_usage`, `u_account_ballance`, `tariff_t_id`) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			ps = con.prepareStatement(sql);
			ps.setString(1, user.getRole().name());
			ps.setString(2, user.getLogin());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getFirstName());
			ps.setString(6, user.getLastName());
			ps.setString(7, user.getPassportNumber());
			ps.setDate(8, user.getRegDate());
			ps.setLong(9, user.getMonthlyDataUsage());
			ps.setLong(10, user.getTotalDataUsage());
			ps.setBigDecimal(11, user.getAccountBallance());
			ps.setInt(12, user.getTariff());
			ps.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException from addUser().", e);
			throw new DAOException("SQLException from addUser()", e);
		}

		finally {
			try {
				if (con != null) {
					pool.releaseConnection(con);
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException e) {
				logger.error("SQLException from getUserById().", e);
				throw new DAOException("SQLException from getUserById()", e);
			}
		}
		return true;
	}

	@Override
	public List<User> getUsersWithNegativeBallance() throws DAOException {
		logger.info("getUsersWithNegativeBallance()");

		String query = "select * FROM user WHERE u_account_ballance<0";

		return getUsersList(query);

	}

	@Override
	public List<User> getUsersToBan() throws DAOException {
		logger.info("getUsersWithNegativeBallance()");

		String query = "SELECT * FROM user WHERE u_account_ballance < 0 AND user.u_id NOT IN (SELECT user_u_id FROM ban)";

		return getUsersList(query);
	}

	@Override
	public boolean banUser(int userId, int banReason, Timestamp setDateTime, String comment)
			throws DAOException {
		logger.info("banUser()");
		Connection con = null;
		Statement st = null;
		PreparedStatement ps = null;

		try {
			con = pool.getConnection();
			st = con.createStatement();
			String sql = "INSERT INTO `internet_provider`.`ban` (`user_u_id`, `ban_reason_br_id`, `b_set_date`, `b_comment`) "
					+ "VALUES (?, ?, ?, ?)";

			ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, banReason);
			ps.setTimestamp(3, setDateTime);
			ps.setString(4, comment);
			ps.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException from banUser().", e);
			throw new DAOException("SQLException from banUser()", e);
		}

		finally {
			try {
				if (con != null) {
					pool.releaseConnection(con);
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException e) {
				logger.error("SQLException after banUser().", e);
				throw new DAOException("SQLException after banUser()", e);
			}
		}
		return true;
	}

	@Override
	public boolean unbanUser(int userId, Timestamp resetDateTime, String comment)
			throws DAOException {
		logger.info("unbanUser()");
		Connection con = null;
		Statement st = null;
		PreparedStatement ps = null;

		try {
			con = pool.getConnection();
			st = con.createStatement();

			// banId version
			// String sql = "UPDATE `internet_provider`.`ban` SET
			// `b_reset_date`=?, `b_comment`=? WHERE `b_id`= ?";

			String sql = "UPDATE `internet_provider`.`ban` SET `b_reset_date`=?, `b_comment`=? "
					+ "WHERE `user_u_id`= ? AND ISNULL(b_reset_date)";
			ps = con.prepareStatement(sql);
			ps.setTimestamp(1, resetDateTime);
			ps.setString(2, comment);
			ps.setInt(3, userId);

			ps.executeUpdate();

		} catch (SQLException e) {
			logger.error("SQLException from unbanUser().", e);
			throw new DAOException("SQLException from unbanUser()", e);
		}

		finally {
			try {
				if (con != null) {
					pool.releaseConnection(con);
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (SQLException e) {
				logger.error("SQLException after unbanUser().", e);
				throw new DAOException("SQLException after unbanUser()", e);
			}
		}
		return true;
	}

}
