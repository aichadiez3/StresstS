package pojos;

import java.io.Serializable;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer patient_id;
	private User user;
	private String name;
	private String surname;
	private LocalDate birth_date;
	private Integer age;
	private Integer height;
	private Integer weight;
	private String gender;
	private Integer telephone;
	private String insurance_company;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Patient(String name, String surname, String insurance_company) {
		super();
		this.name = name;
		this.surname = surname;
		this.insurance_company = insurance_company;
	}

	public Integer getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Integer patient_id) {
		this.patient_id = patient_id;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirth_date() {
		return birth_date;
	}
	
	public void setBirth_date(LocalDate birth_date) {
			this.birth_date = birth_date;
	}
	
	public void setBirth_date(Date birth_date) {
		Instant instant = birth_date.toInstant();
		ZonedDateTime zone =instant.atZone(ZoneId.systemDefault());
		LocalDate givenDate = zone.toLocalDate();
		this.birth_date = givenDate;
	}
	
	public Date convert_LocalDate_to_Date(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		Period period = Period.between(this.birth_date, LocalDate.now());
		this.age = period.getYears();
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getTelephone() {
		return telephone;
	}

	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}

	public String getInsurance_company() {
		return insurance_company;
	}

	public void setInsurance_company(String insurance_company) {
		this.insurance_company = insurance_company;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patient_id == null) ? 0 : patient_id.hashCode());
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
		Patient other = (Patient) obj;
		if (patient_id == null) {
			if (other.patient_id != null)
				return false;
		} else if (!patient_id.equals(other.patient_id))
			return false;
		return true;
	}

	
	
	
	@Override
	public String toString() {
		return "Patient [patient_id=" + patient_id + ", user=" + user + ", name=" + name + ", surname=" + surname
				+ ", birth_date=" + birth_date + ", age=" + age + ", height=" + height + ", weight=" + weight
				+ ", gender=" + gender + ", telephone=" + telephone + ", insurance_company=" + insurance_company + "]";
	}




}
