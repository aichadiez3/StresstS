package pojos;
import java.io.Serializable;

public class Symptom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer symptom_id;
	private Integer weight;
	private String text;
	
	
	public Symptom() {
		super();
	}

	public Symptom(Integer weight, String text) {
		super();
		this.weight = weight;
		this.text = text;
	}
	
	public Integer getSymptom_id() {
		return symptom_id;
	}

	public void setSymptom_id(Integer symptom_id) {
		this.symptom_id = symptom_id;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
	@Override
	public String toString() {
		return "Symptom [weight=" + weight + ", text=" + text + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		Symptom other = (Symptom) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
}