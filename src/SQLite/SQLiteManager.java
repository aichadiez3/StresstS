package SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import interfaces.DBManager;

public class SQLiteManager implements DBManager{
	private Connection c;
	
	public SQLiteManager() {
		super();
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.sqlite.JDBC");
			this.c = DriverManager.getConnection("jdbc:sqlite:./db/Clinicaltrials.db");//hay que poner nuestra database
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			// create Managers

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Connection getConnection() {
		return c;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createTables() {
		// TODO Auto-generated method stub
		Statement stmt1;
		try {
			stmt1 = c.createStatement();
			String sql1 = "CREATE TABLE Symptom " + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " manifestation     TEXT     NOT NULL)";
			stmt1.executeUpdate(sql1);
			
		}catch (SQLException e) {
			if (e.getMessage().contains("already exists")) {
			} else {
				e.printStackTrace();
			}
		}
	}

}
