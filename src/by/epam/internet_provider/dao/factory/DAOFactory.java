package by.epam.internet_provider.dao.factory;

import by.epam.internet_provider.dao.InternetProviderDAO;
import by.epam.internet_provider.dao.impl.sql_database.SqlInternetProviderDAO;

public final class DAOFactory {

	private static final DAOFactory instance = new DAOFactory();

	private final InternetProviderDAO sqlProviderDAO = new SqlInternetProviderDAO();

	private DAOFactory() {
	}

	public static DAOFactory getIstance() {
		return instance;
	}

	public InternetProviderDAO getInternetProviderDAO() {
		return sqlProviderDAO;
	}

}
