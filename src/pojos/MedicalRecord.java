package pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class MedicalRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer medicalRecord_id;
	private Date recordDate;
	private Integer referenceNumber;
	private Boolean bitalinoTestIncluded;
	
	public MedicalRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalRecord(Date recordDate, Boolean bitalinoTestIncluded) {
		super();
		this.recordDate = recordDate;
		this.bitalinoTestIncluded = bitalinoTestIncluded;
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

	
	
	
	
	
	
	
}
