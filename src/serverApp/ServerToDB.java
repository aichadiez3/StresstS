package serverApp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteMethods;
import pojos.EcgTest;
import pojos.EdaTest;
import pojos.MedicalRecord;
import pojos.User;

public class ServerToDB {

	private static ServerSocket serverSocket;
	
	public static void main(String args[]) throws IOException {
		
		Socket socket = null;
            
            //This executes when we have a client
        	InputStream inputStream = null;
            byte[] byteRead;
            String instruction;
            SQLiteMethods methods;
            User user = null;
            MedicalRecord record = null;
            EcgTest ecg = null;
            EdaTest eda = null;


            try {
            	serverSocket = new ServerSocket(9001);
                socket = serverSocket.accept();
                methods = new SQLiteMethods();
                
                //while true, lee el mensaje y hacemos los métodos que nos pida el mensaje
                	
                try {
                	DataInputStream dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                	ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                	// inputStream = socket.getInputStream(); 
            		boolean stopClient = false;
                    while (!stopClient) {
                    	//instruction = dataInputStream.readLine();
                    	instruction = dataInputStream.readUTF();
                        //byteRead = dataInputStream.readLine();
                        //We read until is finished the connection or character 'x'
                        if (instruction.equals("end_client")) {
                            //System.out.println("Client character reception finished");
                            stopClient = true;
                        }
                        
                        //todo lo que he hecho, aïcha habia puesto esto mas adelante, donde comentare **!**, no se donde habrá que colocarlo para que funcione
                        String[] parameters = instruction.split(",");
                        if (parameters[0].equals("new_user")) {
                        	String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                            methods.Insert_new_user(user_name, password, email);
                        }
                        if (parameters[0].equals("new_patient")) {
                        	try {
								user = (User) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_patient(user);
                        }
                        if (parameters[0].equals("new_doctor")) {
                        	try {
								user = (User) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_doctor(user);
                        }
                        if (parameters[0].equals("new_medical_record")) {
                        	try {
								record = (MedicalRecord) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_medical_record(record);
                        }
                        if (parameters[0].equals("new_ecg")) {
                        	try {
                        		ecg = (EcgTest) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_ecg(ecg);
                        }
                        if (parameters[0].equals("new_eda")) {
                        	try {
                        		eda = (EdaTest) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_eda(eda);
                        }
                        
                        
                    }
                
                }  catch (IOException ex) {
                    Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    releaseResourcesClient(inputStream, socket);
                }
                
                //aqui aïcha es donde decia de poner lo que yo he puesto donde esta el **!**, yo no se donde ponerlo, me acabo de dar cuenta
                
                /*byte[] bytes = String.valueOf(inputStream.read()).getBytes();
                
                String message = new String(bytes, StandardCharsets.UTF_8);
                
                if(message.equals("insert user")) {
                	//SQLiteMethods.Insert_new_user(); // Parámetros se tienen que introducir a través de los sockets desde cliente 
                										//-> son los datos q envía el cliente al server, y el server al manejador de db
                	
                }*/
                  
                
            } catch (IOException e) {
            	releaseResourcesClient(inputStream, socket);
                System.out.println("\n\n\nClient finished");
            } finally {
            	releaseResourcesServer(socket, serverSocket);
            	
            }
        
     
		
	}
	
	private static void releaseResourcesClient(InputStream inputStream, Socket socket) {
		try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	
	private static void releaseResourcesServer(Socket socket, ServerSocket serverSocket) {
		try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
