package application;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class pruebaList {
	public static void main(String[] args) throws IOException {
		
		ObservableList<String> records_objects;
		
		
		
		Socket socket = new Socket("localhost", 9000);
		System.out.println("tengo un socket");
        OutputStream outputStream = socket.getOutputStream();
        // create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        DataInputStream dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    	
		dataOutputStream.writeUTF("list_all_medical_records");
		String feedback = dataInputStream.readUTF();
		String[] parameters = feedback.split(";");
		
		//List<MedicalRecordObject> list = new ArrayList<MedicalRecordObject>();
		List<String> list = new ArrayList<String>();

		//MedicalRecordObject object;
		String object;
		
		for(int i=0;i<parameters.length;i++){
			//object = new MedicalRecordObject(parameters[i], parameters[i+1], parameters[i+2], parameters[i+3]);
			object = parameters[i];
			list.add(object);
		}
		
		//System.out.println(list.toString());
		records_objects = FXCollections.observableArrayList(list);
		System.out.println(records_objects.toString());
		
		
		
	}
}
