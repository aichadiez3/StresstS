package pojos;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
public class Bitalino_test implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer test_id;
	private String type;
	private LocalDate date;
	private String file;
	
	public Bitalino_test() {
		super();
	}
	
	public Bitalino_test(String type, LocalDate date, String file) {
		super();
		this.type = type;
		this.date = date;
		this.file = file;
	}

	public Integer getTest_id() {
		return test_id;
	}

	public void setTest_id(Integer test_id) {
		this.test_id = test_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public int hashCode() {
		return Objects.hash(test_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bitalino_test other = (Bitalino_test) obj;
		return Objects.equals(test_id, other.test_id);
	}

	

	
	
}
