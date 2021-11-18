package pojos;

import java.io.Serializable;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Insurance_company implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer insurance_id;
	private String company_name;
	public Integer getInsurance_id() {
		return insurance_id;
	}
	public void setInsurance_id(Integer insurance_id) {
		this.insurance_id = insurance_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public Insurance_company(Integer insurance_id, String company_name) {
		super();
		this.insurance_id = insurance_id;
		this.company_name = company_name;
	}
	public Insurance_company() {
		super();
	}
	@Override
	public int hashCode() {
		return Objects.hash(company_name, insurance_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Insurance_company other = (Insurance_company) obj;
		return Objects.equals(company_name, other.company_name) && Objects.equals(insurance_id, other.insurance_id);
	}
	@Override
	public String toString() {
		return "Insurance_company [insurance_id=" + insurance_id + ", company_name=" + company_name + "]";
	}
	
	
	
}
