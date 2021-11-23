package interfaces;

import java.util.List;

import pojos.MedicalRecord;
import pojos.Symptom;
import pojos.User;

public interface Interface {
	public boolean Connect();
	public boolean CreateTables();
	
	
	
	public MedicalRecord Search_stored_record_by_id(Integer record_id);
	public List<MedicalRecord> Search_stored_record_by_test(Integer test);
	public Symptom Search_symptom_by_id(Integer symptom_id);
	
	public List<MedicalRecord> Search_stored_record_by_date_ascendent();
	public List<MedicalRecord> Search_stored_record_by_date_descendent();
	
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id);
	public List<User> List_all_users();
	
	public boolean Close_connection();
	
}
