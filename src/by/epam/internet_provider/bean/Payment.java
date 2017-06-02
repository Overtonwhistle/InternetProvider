package by.epam.internet_provider.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class Payment {

	private int id;
	private Date paymentDate;
	private BigDecimal amount;
	private int userId;

	Payment() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + id;
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + userId;
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
		Payment other = (Payment) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (id != other.id)
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", paymentDate=" + paymentDate + ", amount=" + amount
				+ ", userId=" + userId + "]";
	}
	
}
