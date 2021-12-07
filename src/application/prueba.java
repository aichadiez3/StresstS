package application;

import java.io.FileWriter;
import java.io.IOException;

public class prueba {
	
	public static void main(String[] args) {
		try {
		      FileWriter writer1 = new FileWriter("./src/.bitalinoResults/ecg_results.txt");
		      writer1.write("conseguido");
		      writer1.close();
		      
		      FileWriter writer2 = new FileWriter("./src/.bitalinoResults/eda_results.txt");
		      writer2.write("conseguido");
		      writer2.close();
		      
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException file_creation_error) {
		      System.out.println("An error occurred.");
		      file_creation_error.printStackTrace();
		    }
	}
}
