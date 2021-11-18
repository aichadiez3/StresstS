package interfaces;

import java.util.List;

import pojos.Symptom;
import pojos.User;

public interface BitalinoTest {
	public boolean Connect();
	public boolean CreateTables();
	
	
	
	public BitalinoTest Search_stored_record(User user);
	public BitalinoTest Search_Bitalino_test_by_id(Integer test_id);
	
	public boolean Close_connection();
}
