package by.epam.internet_provider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.epam.internet_provider.bean.User;
import by.epam.internet_provider.dao.InternetProviderDAO;
import by.epam.internet_provider.dao.exception.DAOException;
import by.epam.internet_provider.dao.factory.DAOFactory;

public class DAOInspector {
	private static final Logger logger = Logger.getLogger(DAOInspector.class);

	public static void main(String[] args) throws DAOException, ParseException {

		DAOFactory factory = DAOFactory.getIstance();
		InternetProviderDAO internetProviderDAO = factory.getInternetProviderDAO();

		internetProviderDAO.initDAO();

		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		// java.util.Date dat_utl;
		// java.sql.Date dat_sql = new java.sql.Date(new
		// java.util.Date().getTime());
		// dat_utl=df.parse("2005-11-10");
		// dat_sql=(Date) dat_utl;

//		 User user = new User();
//		 user.setRole(UserRole.CLIENT);
//		 user.setLogin("mylogin");
//		 user.setPassword("mypass");
//		 user.setEmail("test");
//		 user.setFirstName("1111");
//		 user.setLastName("22222");
//		 user.setPassportNumber("124124124121");
//		 user.setRegDate(new Date(117,04,01));
//		 user.setMonthlyDataUsage(1233);
//		 user.setTotalDataUsage(34443);
//		 user.setAccountBallance(new BigDecimal("34.4"));
//		 user.setTariff(3);
		List<User> list = new ArrayList<>();
		try {
//			 internetProviderDAO.addUser(user);
			// user = internetProviderDAO.getUserById(1);
//			list = internetProviderDAO.getUsersToBan();
//			internetProviderDAO.banUser(9, 2, new java.sql.Timestamp(System.currentTimeMillis()), "test banning ");
//			internetProviderDAO.banUser(9, 2, new java.sql.Timestamp(2017-1900,01,01, 13, 20, 10, 10), "test banning ");
			internetProviderDAO.unbanUser(9, new java.sql.Timestamp(System.currentTimeMillis()), "test AN!!");
		} catch (DAOException e) {
			e.printStackTrace();
		}


//		for (User user : list) {
//			System.out.println(user);
//		}

		System.out.println("=== End of inspector");
		internetProviderDAO.dismissDAO();
	}

}
