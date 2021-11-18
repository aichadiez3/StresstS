package pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MedicalRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer medicalRecord_id;
	private Date recordDate;
	private Integer referenceNumber;
	private Boolean bitalinoTestIncluded;
	private List<String> symptoms_list = new LinkedList<String>();
	
	public MedicalRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalRecord(Date recordDate, Boolean bitalinoTestIncluded) {
		super();
		this.recordDate = recordDate;
		this.bitalinoTestIncluded = bitalinoTestIncluded;
	}
	
	public MedicalRecord(Date recordDate, Boolean bitalinoTestIncluded, List<String> symptoms_list) {
		super();
		this.recordDate = recordDate;
		this.bitalinoTestIncluded = bitalinoTestIncluded;
		this.symptoms_list = symptoms_list;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(Integer referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Boolean getBitalinoTestIncluded() {
		return bitalinoTestIncluded;
	}

	public void setBitalinoTestIncluded(Boolean bitalinoTestIncluded) {
		this.bitalinoTestIncluded = bitalinoTestIncluded;
	}

	@Override
	public int hashCode() {
		return Objects.hash(medicalRecord_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalRecord other = (MedicalRecord) obj;
		return Objects.equals(medicalRecord_id, other.medicalRecord_id);
	}

	public List<String> getSymptoms_list() {
		return symptoms_list;
	}

	public void setSymptoms_list(List<String> symptoms_list) {
		this.symptoms_list = symptoms_list;
	}
	public void addSymptom(String symptom) {
		symptoms_list.add(symptom);
	}
	
	public void removeSymptom(String symptom) {
		if(symptoms_list.contains(symptom)) {
			symptoms_list.remove(symptom);
		}
	}
	
	
	
	
	
	
	
}
