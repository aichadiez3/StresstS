package SQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import application.BitalinoController;
import interfaces.Interface;
import pojos.Doctor;
import pojos.MedicalRecord;
import pojos.Patient;
import pojos.Symptom;
import pojos.User;

public class SQLiteMethods implements Interface {

	private Connection sqlite_connection;
	
	public SQLiteMethods() {
		super();
		// TODO Auto-generated constructor stub
	}

	// -----> INSERT METHODS <-----
	
	public User Insert_new_user(String user_name, String password, String email) {
		try {
			String table = "INSERT INTO user (user_name, password,email) " + " VALUES(?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, user_name);
			template.setString(2, password);
			template.setString(3, email);
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM user WHERE user_name = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, user_name);
			ResultSet result_set = template.executeQuery();
			User user = new User();
		    user.setUserName(result_set.getString("user_name"));
		    user.setPassword(result_set.getString("password"));
		    user.setEmail(result_set.getString("email"));
		    user.setUserId(result_set.getInt("user_id"));
		    return user;
		} catch (SQLException insert_user_error) {
			return null;
		}
	}
	
	
    public Patient Insert_new_patient(User user, String name, String surname, LocalDate birth_date, Integer age, Integer height, Integer weight, String gender, Integer telephone, String insurance_company ) {
		try {
			String table = "INSERT INTO patient (user_id, name, surname, birth_date, age, height, weight, gender,telephone,insurance_company) " + "VALUES (?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setInt(1, user.getUserId());
			template.setString(2, user.getUserName());
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM patient WHERE user_id = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, user.getUserId());
			ResultSet result_set = template.executeQuery();
			Patient patient = new Patient();
			patient.setPatient_id(result_set.getInt("patient_id"));
			patient.setName(result_set.getString("name"));
			patient.setUser(user);
			patient.setSurname(result_set.getString("surname"));
			patient.setBirth_date(result_set.getDate("birth_date"));
			patient.setAge(result_set.getInt("age"));
			patient.setHeight(result_set.getInt("height"));
			patient.setWeight(result_set.getInt("weight"));
			patient.setGender(result_set.getString("gender"));
			patient.setTelephone(result_set.getInt("telephone"));
			patient.setInsurance_company(result_set.getString("insurance_company"));
			
			return patient;
		} catch (SQLException new_client_account_error) {
			new_client_account_error.printStackTrace();
			return null;
		}
    }
    
    public Doctor Insert_new_doctor(User user) {
		try {
			String table = "INSERT INTO doctor (user_id, name) " + "VALUES (?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setInt(1, user.getUserId());
			template.setString(2, user.getUserName());
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM director WHERE user_id = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, user.getUserId());
			ResultSet result_set = template.executeQuery();
			Doctor doctor = new Doctor();
			doctor.setDoctor_id(result_set.getInt("director_id"));
			doctor.setName(result_set.getString("name"));
			doctor.setTelephone(0);
			return doctor;
		} catch(SQLException new_director_error) {
			return null;
		}
	}
	
	
	// -----> UPDATE METHODS <-----
	

	// -----> SEARCH STORED ELEMENTS BY ID METHODS <-----
	public MedicalRecord Search_stored_record_by_id(Integer record_id) {
		try {
			String SQL_code = "SELECT * FROM medical_record WHERE medicalRecord_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, record_id);
			MedicalRecord record=new MedicalRecord();
			ResultSet result_set = template.executeQuery();
			record.setReferenceNumber(result_set.getInt("reference_number"));
			record.setRecordDate(result_set.getDate("record_date"));
			record.setBitalinoTestIncluded((BitalinoController) result_set.getObject("bitalinoTestIncluded"));
			List<Symptom> symptoms_list = Search_all_symptoms_from_record(record_id);
			record.setSymptoms_list(symptoms_list);
			template.close();
			return record;
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
		}
		return null;
	}
	
	public List<MedicalRecord> Search_stored_record_by_test(Integer test) {
		List<MedicalRecord> records = new LinkedList<MedicalRecord>();
		try {
			String SQL_code = "SELECT * FROM medical_record WHERE reference_number LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet rs = template.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("medicalRecord_id");
				Date date = rs.getDate("record_date");
				int referenceNumber = rs.getInt("reference_number");
				BitalinoController bitalinoController = (BitalinoController) rs.getObject("bitalinoTestIncluded");
				List<Symptom> symptoms_list = (List<Symptom>) rs.getArray("symptoms_list");
				records.add(new MedicalRecord(id, date, referenceNumber, bitalinoController, symptoms_list));
			}
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		return records;
	}
	
	
	public Symptom Search_symptom_by_id(Integer symptom_id) {
		try {
			String SQL_code = "SELECT * FROM medicalRecord_symptom WHERE symptom_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, symptom_id);
			Symptom symptom = new Symptom();
			ResultSet result_set = template.executeQuery();
			symptom.setSymptom_id(symptom_id);
			symptom.setName(result_set.getString("name"));
			symptom.setWeight(result_set.getInt("weight"));
			template.close(); 
			return symptom;
		} catch (SQLException search_symptom_error) {
			search_symptom_error.printStackTrace();
			return null;
		}
	}
	
	
	// -----> SEARCH BY DATE METHODS <-----
	public List<MedicalRecord> Search_stored_record_by_date_ascendent() {
		List<MedicalRecord> records = new LinkedList<MedicalRecord>();
		try {
			String SQL_code = "SELECT * FROM medical_record ORDER BY record_date ASC";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet rs = template.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("medicalRecord_id");
				Date date = rs.getDate("record_date");
				int referenceNumber = rs.getInt("reference_number");
				BitalinoController bitalinoController = (BitalinoController) rs.getObject("bitalinoTestIncluded");
				List<Symptom> symptoms_list = (List<Symptom>) rs.getArray("symptoms_list"); // PUEDE QUE ESTO VAYA A DAR UN ERROR CON EL TIPO DE DATO DE LA TABLA medical_record
				records.add(new MedicalRecord(id, date, referenceNumber, bitalinoController, symptoms_list));
			}
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		return records;
	}
	
	
	public List<MedicalRecord> Search_stored_record_by_date_descendent() {
		List<MedicalRecord> records = new LinkedList<MedicalRecord>();
		try {
			String SQL_code = "SELECT * FROM medical_record ORDER BY record_date DESC";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet rs = template.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("medicalRecord_id");
				Date date = rs.getDate("record_date");
				int referenceNumber = rs.getInt("reference_number");
				BitalinoController bitalinoController = (BitalinoController) rs.getObject("bitalinoTestIncluded");
				List<Symptom> symptoms_list = (List<Symptom>) rs.getArray("symptoms_list"); // PUEDE QUE ESTO VAYA A DAR UN ERROR CON EL TIPO DE DATO DE LA TABLA medical_record
				records.add(new MedicalRecord(id, date, referenceNumber, bitalinoController, symptoms_list));
			}
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		return records;
	}
	
	
	
	// -----> LIST METHODS <-----
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id) {
		try {
			
			String SQL_code = "SELECT * FROM medicalRecord_symptom WHERE medicalRecord_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, record_id);
			ResultSet result_set = template.executeQuery();
			List<Symptom> symptom_list = new LinkedList<Symptom>();
			while (result_set.next()) {
				Symptom symptom = Search_symptom_by_id(result_set.getInt("symptom_id"));
				symptom_list.add(symptom);
			}
			template.close();
			return symptom_list;
		} catch (SQLException list_symptom_record_error) {
			list_symptom_record_error.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<User> List_all_users() {
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT * FROM user";
			List<User> users_list = new LinkedList<User>();
			ResultSet result_set = statement.executeQuery(SQL_code);
			while (result_set.next()) {
				User user = new User();
				user.setUserId(result_set.getInt("user_id"));
				user.setUserName(result_set.getString("user_name"));
				user.setPassword(result_set.getString("password"));
				user.setEmail(result_set.getString("email"));
				users_list.add(user);
			}
			statement.close();
			return users_list;
		} catch (SQLException list_users_error) {
			list_users_error.printStackTrace();
			return null;
		}
	}
	
}
