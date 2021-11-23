package SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLiteManager {
	private Connection sqlite_connection;
	
	public SQLiteManager() {
		super();
	}

	
	public boolean Connect() {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.sqlite.JDBC");
			this.sqlite_connection = DriverManager.getConnection("jdbc:sqlite:./db/Clinicaltrials.db");//hay que poner nuestra database
			sqlite_connection.createStatement().execute("PRAGMA foreign_keys=ON");
			return true;
			// create Managers

		} catch (ClassNotFoundException | SQLException connection_error) {
			connection_error.printStackTrace();
			return false;
		}
	}

	protected Connection getConnection() {
		return sqlite_connection;
	}

	

	public boolean CreateTables() {
		try {
			Statement stmt0 = sqlite_connection.createStatement();
			String sql0 = "CREATE TABLE user " + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " user_name TEXT NOT NULL UNIQUE, " + " password TEXT NOT NULL, "+" email TEXT NOT NULL UNIQUE)";
			stmt0.execute(sql0);
			stmt0.close();
			
			Statement stmt1 = sqlite_connection.createStatement();
			String sql1 = "CREATE TABLE patient " + "(patient_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL, "
					+ " surname TEXT NOT NULL, " + " birthdate DATETIME NOT NULL, " + " age INTEGER, " + " telephone INTEGER default NULL, "
					+ " height INTEGER default NULL, " + " weight INTEGER default NULL, " + " insurance_id FOREIGN KEY REFERENCES insurance(insurance_id), " 
					+ " user_id FOREING KEY REFERENCES user(user_id) ON DELETE CASCADE)";
			stmt1.execute(sql1);
			stmt1.close();
			
			
			Statement stmt2 = sqlite_connection.createStatement();
			String sql2 = "CREATE TABLE symptom " + "(symptom_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " name TEXT NOT NULL, " + " weight INTEGER default 0, "
					+ " record_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt2.execute(sql2);
			stmt2.close();
			
			Statement stmt3 = sqlite_connection.createStatement();
			String sql3 = "CREATE TABLE medical_record " + "(medicalRecord_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " reference_number INTEGER UNIQUE, " + " record_date DATETIME NOT NULL, " + " , bitalinoTestIncluded TEXT default NULL, "
					+ " symptoms_list TEXT default NULL, "
					+ " FOREING KEY (patient_id) REFERENCES patient (patient_id) ON UPDATE RESTRICT ON DELETE CASCADE)";
			stmt3.execute(sql3);
			stmt3.close();
			
			Statement stmt4 = sqlite_connection.createStatement();
			String sql4 = "CREATE TABLE bitalino_test " + "(test_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " type TEXT NOT NULL, " + " test_date DATETIME NOT NULL, "
					+ " record_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt4.execute(sql4);
			stmt4.close();
			
			Statement stmt5 = sqlite_connection.createStatement();
			String sql5 = "CREATE TABLE insurance " + "(insurance_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL)";
			stmt5.execute(sql5);
			stmt5.close();
			
			Statement stmt6 = sqlite_connection.createStatement();
			String sql6 = "CREATE TABLE doctor " + "(doctor_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " name TEXT NOT NULL, " + " telephone INTEGER default NULL, " + " insurance_id FOREING KEY REFERENCES insurance(insurance_id), "
					+ " user_id FOREING KEY REFERENCES user(user_id) ON DELETE CASCADE)";
			stmt6.execute(sql6);
			stmt6.close();
			
			
			// ManyToMany relation tables go here
			Statement stmt8 = sqlite_connection.createStatement();
			String sql8 = "CREATE TABLE patient_doctor " + "(patient_id INTEGER REFERENCES patient(patient_id), "
					+ " doctor_id REFERENCES doctor(doctor_id),"
					+ " PRIMARY KEY (patient_id, doctor_id))";
			stmt8.execute(sql8);
			stmt8.close();
		
			Statement stmt9 = sqlite_connection.createStatement();
			String sql9 = "CREATE TABLE medicalRecord_symptom " + "(medicalRecord_id INTEGER REFERENCES medical_record(medicalRecord_id), "
					+ " symptom_id REFERENCES symptom(symptom_id),"
					+ " PRIMARY KEY (medicalRecord_id, symptom_id))";
			stmt9.execute(sql9);
			stmt9.close();
			
			return true;
		}catch (SQLException tables_error) {
			if (tables_error.getMessage().contains("already exists")) {
			} else {
				tables_error.printStackTrace();
				return false;
			}
			
			return false;
		}
	}
	
	
	// -------> CLOSE DATABASE CONNECTION <---------
	
	public boolean Close_connection() {
		// TODO Auto-generated method stub
		try {
			this.sqlite_connection.close();
			return true;
		} catch (SQLException close_connection_error) {
			close_connection_error.printStackTrace();
			return false;
		}
	}

}
