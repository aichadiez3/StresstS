package serverApp;

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
            try {
            	serverSocket = new ServerSocket(9001);
                socket = serverSocket.accept();
                
                //while true, lee el mensaje y hacemos los métodos que nos pida el mensaje
                InputStream inputStream = socket.getInputStream();
                
                
                byte[] bytes = String.valueOf(inputStream.read()).getBytes();
                
                String message = new String(bytes, StandardCharsets.UTF_8);
                
                if(message.equals("insert user")) {
                	//SQLiteMethods.Insert_new_user(); // Parámetros se tienen que introducir a través de los sockets desde cliente 
                										//-> son los datos q envía el cliente al server, y el server al manejador de db
                	
                }
                  
                
            } catch (IOException e) {
            	releaseResourcesClient(socket);
                System.out.println("\n\n\nClient finished");
            } finally {
            	releaseResourcesServer(socket, serverSocket);
            	
            }
        
     
		
	}
	
	private static void releaseResourcesClient(Socket socket) {
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
