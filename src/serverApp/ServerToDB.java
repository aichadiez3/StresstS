package serverApp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteMethods;

public class ServerToDB {

	private static ServerSocket serverSocket;
	
	public static void main(String args[]) throws IOException {
		
		Socket socket = null;
            
            //This executes when we have a client
        	InputStream inputStream = null;
            byte[] byteRead;
            String instruction;
            SQLiteMethods methods;

            try {
            	serverSocket = new ServerSocket(9001);
                socket = serverSocket.accept();
                methods = new SQLiteMethods();
                
                //while true, lee el mensaje y hacemos los métodos que nos pida el mensaje
                	
                try {
                	DataInputStream dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
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
                        
                        //aqui hay que ver cómo recibir distintos mensajes con los parametros ooooo separar el mensaje basandonos en espacios o comas por ejemplo
                			//y en la instruccion mandada pues que sea "new_user,roberto,123,aa@aa" o algo asi
                        String[] parameters = instruction.split(",");
                        if (parameters[0].equals("new_user")) {
                        	String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                            methods.Insert_new_user(user_name, password, email);
                        }
                        /*if (parameters[0].equals("new_patient")) {
                        	String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                            methods.Insert_new_user(user_name, password, email);
                        }
                        if (parameters[0].equals("new_user")) {
                        	String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                            methods.Insert_new_user(user_name, password, email);
                        }
                        if (parameters[0].equals("new_user")) {
                        	String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                            methods.Insert_new_user(user_name, password, email);
                        }*/
                        
                    }
                
                }  catch (IOException ex) {
                    Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    releaseResourcesClient(inputStream, socket);
                }
                
                byte[] bytes = String.valueOf(inputStream.read()).getBytes();
                
                String message = new String(bytes, StandardCharsets.UTF_8);
                
                if(message.equals("insert user")) {
                	//SQLiteMethods.Insert_new_user(); // Parámetros se tienen que introducir a través de los sockets desde cliente 
                										//-> son los datos q envía el cliente al server, y el server al manejador de db
                	
                }
                  
                
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
