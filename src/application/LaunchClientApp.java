package application;
	
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import serverApp.ServerToDB;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;


public class LaunchClientApp extends Application{
	
	private static Stage stage;
	private double xOffset = 0;
	private double yOffset = 0;
	
	//esto es el la instruccion que voy a recibir de cada boton o lo que sea y que quiero que este client
		//le mande al server y ese al otro server para que haga las cosas en la DB
	public static String instruction = null;
	public static Object object = null;
	public static Object object2 = null;
	
	public static Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
			primaryStage.setTitle("Log in page");
			Scene scene = new Scene(root);
			scene.getStylesheets().addAll(getClass().getResource("dark-theme.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			stage = primaryStage;
			primaryStage.show();
			
			
			
			// ----> This is to be able to move the window
			
			root.setOnMousePressed(new EventHandler<MouseEvent> () {

				@Override
				public void handle(MouseEvent arg0) {
					xOffset = arg0.getSceneX();
					yOffset = arg0.getSceneY();
				}
				
			});
			
			root.setOnMouseDragged(new EventHandler<MouseEvent> () {

				@Override
				public void handle(MouseEvent arg0) {
					stage.setX(arg0.getSceneX() - xOffset);
					stage.setY(arg0.getSceneY() - yOffset);
				}
				
			});
			
		} catch(IOException fatal_error) {
			fatal_error.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws IOException {
		launch(args);
			
		// -------> Here goes the code for the client connection to server
		// * 
		Socket socket = new Socket("localhost", 9000);
        OutputStream outputStream = socket.getOutputStream();
        // create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        // create an object output stream from the output stream so we can send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        //no se si puedo hacer esas dos cosas a la vez con el outputStream la verdad
        
        // write the message we want to send
        //aqui tal vez pondria un while(true) o while(stopClient == false)
        //y luego un if(instruction != null)
        dataOutputStream.writeUTF(instruction);
        dataOutputStream.flush();   
        
        objectOutputStream.writeObject(object);
        //no he visto que se haga nada tipo objectOutputStream.close(), no se si habra que hacerlo
        try {
			object2 = objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        releaseResources(dataOutputStream, outputStream, socket);
        
        /* * ............
         * 
		*/
	}
	
	private static void releaseResources(DataOutputStream dataOutputStream, OutputStream outputStream, Socket socket) {
		 try {
			 try {
	                dataOutputStream.close();

	            } catch (IOException ex) {
	                Logger.getLogger(LaunchClientApp.class
	                        .getName()).log(Level.SEVERE, null, ex);
	            }
			 	
			 try {
	                outputStream.close();

	            } catch (IOException ex) {
	                Logger.getLogger(LaunchClientApp.class
	                        .getName()).log(Level.SEVERE, null, ex);
	            }
	            socket.close();

	        } catch (IOException ex) {
	            Logger.getLogger(LaunchClientApp.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	
	
	
}
