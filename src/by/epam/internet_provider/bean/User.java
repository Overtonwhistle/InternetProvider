package by.epam.internet_provider.bean;

import java.math.BigDecimal;
import java.sql.Date;

import by.epam.internet_provider.bean.Param.UserRole;

public class User {
	private int id;
	private UserRole role;
	private String login;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String passportNumber;
	private Date regDate;
	private long monthlyDataUsage;
	private long totalDataUsage;
	private BigDecimal accountBallance;
	private int tariffId;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public long getMonthlyDataUsage() {
		return monthlyDataUsage;
	}

	public void setMonthlyDataUsage(long monthlyDataUsage) {
		this.monthlyDataUsage = monthlyDataUsage;
	}

	public long getTotalDataUsage() {
		return totalDataUsage;
	}

	public void setTotalDataUsage(long totalDataUsage) {
		this.totalDataUsage = totalDataUsage;
	}

	public BigDecimal getAccountBallance() {
		return accountBallance;
	}

	public void setAccountBallance(BigDecimal accountBallance) {
		this.accountBallance = accountBallance;
	}

	public int getTariff() {
		return tariffId;
	}

	public void setTariff(int tariff) {
		this.tariffId = tariff;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountBallance == null) ? 0 : accountBallance.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + (int) (monthlyDataUsage ^ (monthlyDataUsage >>> 32));
		result = prime * result + ((passportNumber == null) ? 0 : passportNumber.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((regDate == null) ? 0 : regDate.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + tariffId;
		result = prime * result + (int) (totalDataUsage ^ (totalDataUsage >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountBallance == null) {
			if (other.accountBallance != null)
				return false;
		} else if (!accountBallance.equals(other.accountBallance))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (monthlyDataUsage != other.monthlyDataUsage)
			return false;
		if (passportNumber == null) {
			if (other.passportNumber != null)
				return false;
		} else if (!passportNumber.equals(other.passportNumber))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (regDate == null) {
			if (other.regDate != null)
				return false;
		} else if (!regDate.equals(other.regDate))
			return false;
		if (role != other.role)
			return false;
		if (tariffId != other.tariffId)
			return false;
		if (totalDataUsage != other.totalDataUsage)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + ", login=" + login + ", password=" + password
				+ ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", passportNumber=" + passportNumber + ", regDate=" + regDate
				+ ", monthlyDataUsage=" + monthlyDataUsage + ", totalDataUsage=" + totalDataUsage
				+ ", accountBallance=" + accountBallance + ", tariff=" + tariffId + "]";
	};

}
