package serverApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ServerController implements Initializable {

	private SQLiteManager controller;
	
	@FXML
    private Pane serverScene;

    @FXML
    private Group startButton;

    @FXML
    private Group stopButton;

    
    private static Stage main_menu_stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		
		controller = new SQLiteManager();
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			
			
			try {
				ServerSocket serverSocket = new ServerSocket(9000);
	            while (true) {
	                //This executes when we have a client
	                Socket socket = serverSocket.accept();
	                new Thread(new ServerClient(socket)).start();
	                
	            }
	        } catch (IOException e) {
	        	Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, e);
			}
			
			controller.Connect();
			controller.CreateTables();
			controller = null;
			
			
			// close the scene but the server still waiting for connections
			main_menu_stage = (Stage) serverScene.getScene().getWindow();
			main_menu_stage.setIconified(true);
		});
		
		stopButton.setOnMouseClicked((MouseEvent event) -> {
			controller.Close_connection();
			//incluir aqui las release resources
			System.exit(0);
		});
	}
    
}
