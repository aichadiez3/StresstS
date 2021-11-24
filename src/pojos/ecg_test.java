package pojos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class ecg_test implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ecg_id;
	private LinkedList<Integer> ecg_values = new LinkedList<Integer>();
	
	public ecg_test() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ecg_test(LinkedList<Integer> ecg_values) {
		super();
		this.ecg_values = ecg_values;
	}

	public Integer getEcg_id() {
		return ecg_id;
	}

	public void setEcg_id(Integer ecg_id) {
		this.ecg_id = ecg_id;
	}

	public LinkedList<Integer> getEcg_values() {
		return ecg_values;
	}

	public void setEcg_values(LinkedList<Integer> ecg_values) {
		this.ecg_values = ecg_values;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ecg_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ecg_test other = (ecg_test) obj;
		return Objects.equals(ecg_id, other.ecg_id);
	}
	
	
	
	
}
