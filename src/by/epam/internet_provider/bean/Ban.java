package by.epam.internet_provider.bean;

import java.sql.Date;

public class Ban {
	private int id;
	private Date setDate;
	private Date resetDate;
	private String comment;
	private int banReason;
	private int user;

	public Ban() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSetDate() {
		return setDate;
	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	public Date getResetDate() {
		return resetDate;
	}

	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getBanReason() {
		return banReason;
	}

	public void setBanReason(int banReason) {
		this.banReason = banReason;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + banReason;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + id;
		result = prime * result + ((resetDate == null) ? 0 : resetDate.hashCode());
		result = prime * result + ((setDate == null) ? 0 : setDate.hashCode());
		result = prime * result + user;
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
		Ban other = (Ban) obj;
		if (banReason != other.banReason)
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (id != other.id)
			return false;
		if (resetDate == null) {
			if (other.resetDate != null)
				return false;
		} else if (!resetDate.equals(other.resetDate))
			return false;
		if (setDate == null) {
			if (other.setDate != null)
				return false;
		} else if (!setDate.equals(other.setDate))
			return false;
		if (user != other.user)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ban [id=" + id + ", setDate=" + setDate + ", resetDate=" + resetDate + ", comment="
				+ comment + ", banReason=" + banReason + ", user=" + user + "]";
	}
}
