package by.epam.internet_provider.bean;

import java.sql.Date;

public class Request {
	private int id;
	private Date requestDate;
	private Date processedDate;
	private int processedBy;
	private int userId;
	private int tariffId;

	public Request() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public int getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(int processedBy) {
		this.processedBy = processedBy;
	}

	public int getUser() {
		return userId;
	}

	public void setUser(int user) {
		this.userId = user;
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
		result = prime * result + id;
		result = prime * result + processedBy;
		result = prime * result + ((processedDate == null) ? 0 : processedDate.hashCode());
		result = prime * result + ((requestDate == null) ? 0 : requestDate.hashCode());
		result = prime * result + tariffId;
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
		Request other = (Request) obj;
		if (id != other.id)
			return false;
		if (processedBy != other.processedBy)
			return false;
		if (processedDate == null) {
			if (other.processedDate != null)
				return false;
		} else if (!processedDate.equals(other.processedDate))
			return false;
		if (requestDate == null) {
			if (other.requestDate != null)
				return false;
		} else if (!requestDate.equals(other.requestDate))
			return false;
		if (tariffId != other.tariffId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", requestDate=" + requestDate + ", processedDate="
				+ processedDate + ", processedBy=" + processedBy + ", user=" + userId + ", tariff="
				+ tariffId + "]";
	}
}
