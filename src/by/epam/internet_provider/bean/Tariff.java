package by.epam.internet_provider.bean;

import java.math.BigDecimal;

public class Tariff {
	private int id;
	private String title;
	private BigDecimal monthlyCost;
	private long monthlyDataLimit;
	private boolean unlimTraffic;
	private BigDecimal overloadLimitCost;
	private String description;
	private int technologyId;

	public Tariff() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getMonthlyCost() {
		return monthlyCost;
	}

	public void setMonthlyCost(BigDecimal monthlyCost) {
		this.monthlyCost = monthlyCost;
	}

	public long getMonthlyDataLimit() {
		return monthlyDataLimit;
	}

	public void setMonthlyDataLimit(long monthlyDataLimit) {
		this.monthlyDataLimit = monthlyDataLimit;
	}

	public boolean isUnlimTraffic() {
		return unlimTraffic;
	}

	public void setUnlimTraffic(boolean unlimTraffic) {
		this.unlimTraffic = unlimTraffic;
	}

	public BigDecimal getOverloadLimitCost() {
		return overloadLimitCost;
	}

	public void setOverloadLimitCost(BigDecimal overloadLimitCost) {
		this.overloadLimitCost = overloadLimitCost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTechnology() {
		return technologyId;
	}

	public void setTechnology(int technology) {
		this.technologyId = technology;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((monthlyCost == null) ? 0 : monthlyCost.hashCode());
		result = prime * result + (int) (monthlyDataLimit ^ (monthlyDataLimit >>> 32));
		result = prime * result + ((overloadLimitCost == null) ? 0 : overloadLimitCost.hashCode());
		result = prime * result + technologyId;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + (unlimTraffic ? 1231 : 1237);
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
		Tariff other = (Tariff) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (monthlyCost == null) {
			if (other.monthlyCost != null)
				return false;
		} else if (!monthlyCost.equals(other.monthlyCost))
			return false;
		if (monthlyDataLimit != other.monthlyDataLimit)
			return false;
		if (overloadLimitCost == null) {
			if (other.overloadLimitCost != null)
				return false;
		} else if (!overloadLimitCost.equals(other.overloadLimitCost))
			return false;
		if (technologyId != other.technologyId)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (unlimTraffic != other.unlimTraffic)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tariff [id=" + id + ", title=" + title + ", monthlyCost=" + monthlyCost
				+ ", monthlyDataLimit=" + monthlyDataLimit + ", unlimTraffic=" + unlimTraffic
				+ ", overloadLimitCost=" + overloadLimitCost + ", description=" + description
				+ ", technology=" + technologyId + "]";
	}

}
