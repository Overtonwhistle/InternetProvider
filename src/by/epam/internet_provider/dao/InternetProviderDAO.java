package by.epam.internet_provider.dao;

import java.sql.Timestamp;
import java.util.List;

import by.epam.internet_provider.bean.User;
import by.epam.internet_provider.dao.exception.DAOException;

public interface InternetProviderDAO {
	public void initDAO() throws DAOException;

	public void dismissDAO() throws DAOException;

	public User getUserById(int userId) throws DAOException;

	public boolean addUser(User user) throws DAOException;

	public boolean banUser(int userId, int banReason, Timestamp setDateTime, String comment)
			throws DAOException;

	public boolean unbanUser(int userId, Timestamp resetDateTime, String comment)
			throws DAOException;
	
	public List<User> getUsersWithNegativeBallance() throws DAOException;

	public List<User> getUsersToBan() throws DAOException;
}
