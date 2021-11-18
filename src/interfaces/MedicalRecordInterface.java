package interfaces;

import java.util.List;

import pojos.MedicalRecord;
import pojos.Symptom;
import pojos.User;

public interface MedicalRecordInterface {
	public boolean Connect();
	public boolean CreateTables();
	
	
	
	public MedicalRecord Search_stored_record(User user);
	
	
	public MedicalRecord Search_record_by_id(Integer record_id);
	
	public List<User> List_all_users();
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id);
	
	public boolean Close_connection();
	
}
