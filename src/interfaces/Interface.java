package interfaces;

import java.time.LocalDate;
import java.util.List;

import pojos.Doctor;
import pojos.MedicalRecord;
import pojos.Patient;
import pojos.Symptom;
import pojos.User;

public interface Interface {
	
	public User Insert_new_user(String user_name, String password, String email);
	public Patient Insert_new_patient(User user, String name, String surname, LocalDate birth_date, Integer age, Integer height, Integer weight, String gender, Integer telephone, String insurance_company);
	public Doctor Insert_new_doctor(User user);
	
	
	public MedicalRecord Search_stored_record_by_id(Integer record_id);
	public List<MedicalRecord> Search_stored_record_by_test(Integer test);
	public Symptom Search_symptom_by_id(Integer symptom_id);
	
	public List<MedicalRecord> Search_stored_record_by_date_ascendent();
	public List<MedicalRecord> Search_stored_record_by_date_descendent();
	
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id);
	public List<User> List_all_users();
	
	
}
