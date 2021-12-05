package application;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class logInController implements Initializable {

	private PatientMenuController patient_controller;
	private RegistrationController registration_controller;
	private static Stage main_menu_stage;
	
	@FXML
    private AnchorPane anchorPane;
	
	@FXML
    private Pane logInPanel;
	
	@FXML
    private Pane registerPane;
	
	@FXML
    private Button logInButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label signInButton;

    @FXML
    private TextField usernameField;

    @FXML
    private ImageView exitButton;
    
    @FXML
    private Group warning;
    
    public static Integer user_id;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logInButton.setOnAction((ActionEvent event) -> {
			try {
				
				// Charge the new Menu scene
				FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientMenuView.fxml"));
				Parent root = (Parent) loader.load();
				this.patient_controller = new PatientMenuController();
				this.patient_controller = loader.getController();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(new Scene(root));
				stage.show();
				
				// ---> To close the log in stage icon 
				main_menu_stage = (Stage) anchorPane.getScene().getWindow();
				main_menu_stage.close();
					
				
			} catch (Exception log_in_error) {
				log_in_error.printStackTrace();
			}
			
		});
		
		signInButton.setOnMouseClicked((MouseEvent event) -> {
			try {
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationView.fxml"));
				Parent root = (Parent) loader.load();
				this.registration_controller = new RegistrationController();
				this.registration_controller = loader.getController();
				
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(new Scene(root));
				stage.show();
				
				// ---> To close the menu window
				
				main_menu_stage = (Stage) anchorPane.getScene().getWindow();
				main_menu_stage.close();
			
			} catch(IOException sign_in_error) {
				sign_in_error.printStackTrace();
			}
			
		});
		
	}
	
	
	@FXML
    void check_user_existence(MouseEvent event) {
		//LaunchClientApp.instruction = ("search_user_by_userName," + usernameField.getText());
		//user_id = Integer.parseInt(LaunchClientApp.feedback);
		
		
		
		if (!usernameField.getText().contentEquals(null) & !passwordField.getText().contentEquals(null) & user_id!=null) {
			logInButton.setDisable(false);
			warning.setVisible(false);
		} else {
			warning.setVisible(true);
			logInButton.setDisable(true);
		}
    }
	
	@FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }

	
}
