package application;

import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import application.LaunchClientApp;

public class RegistrationController implements Initializable{

	private logInController controller;
	private PatientMenuController patient_controller;
	private static Stage main_menu_stage;
    public static Integer user_id;

	
	@FXML
    private Pane registrationPane;
	
	@FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label warning_email;

    @FXML
    private TextField emailField;
    
    @FXML
    private Button signInButton;

    @FXML
    private Label warning_username;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView usernameOk;

    @FXML
    private ImageView emailOk;
    
    @FXML
    private ImageView returnButton;
    
    @FXML
    private Group loadingAnimation;
    
    @FXML
    private Group okayVerification;
    
    @FXML
    private Group warning;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField passwordField2;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.loadingAnimation.setVisible(false);
		
		signInButton.setOnAction((ActionEvent event) -> {
			try {
				
				// queda añadir una funcion que compruebe en la database que no existe este usuario
				
				if (passwordField.getText().equals(passwordField2.getText()) & !usernameField.equals(null) & !emailField.equals(null) & !nameField.equals(null) 
						& !surnameField.equals(null)) {
					

					// Connection to web database
					/*
					Class.forName("com.mysql.jdbc.Driver");
					Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/anxipharma","root","");
					
					//esto de abajo es el metodo Insert_new_user que ya tenemos en el SQLiteMethods
					
					PreparedStatement ps_user = dbConnection.prepareStatement("insert into user(user_name,password,email) values(?,?,?);");
					ps_user.setString(1,usernameField.getText());
					ps_user.setString(2,passwordField.getText());
					ps_user.setString(3,emailField.getText());
					ps_user.executeUpdate();
					
					PreparedStatement ps_patient = dbConnection.prepareStatement("insert into patient(name,surname) values(?,?);");
					ps_patient.setString(1,nameField.getText());
					ps_patient.setString(2,surnameField.getText());
					ps_patient.executeUpdate();
					
					*/
					
					//send the instruction through sockets
					LaunchClientApp.instruction = "new_user," + usernameField.getText() + "," + passwordField.getText() + "," + emailField.getText();
					user_id = Integer.parseInt(LaunchClientApp.feedback);
					LaunchClientApp.instruction = "new_patient,"  + user_id + "," + nameField.getText() + "," + surnameField.getText();
				}
				
			// ---> Load new patient menu scene
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientMenuView.fxml"));
			Parent root = (Parent) loader.load();
			this.patient_controller = new PatientMenuController();
			this.patient_controller = loader.getController();
			Stage stage = new Stage();
			
			// --> Loads a charging state view
			PauseTransition wait = new PauseTransition(Duration.seconds(2));
			wait.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					//System.exit(0);
					stage.setAlwaysOnTop(true);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setScene(new Scene(root));
					stage.show();
					
					// ---> To close the log in stage icon 
					main_menu_stage = (Stage) registrationPane.getScene().getWindow();
					main_menu_stage.close();
				}
	        });
			
			wait.play();
			this.registrationPane.setEffect(new BoxBlur(4,4,4));
			this.registrationPane.setDisable(true);
			
			// --> This opens a spinner for charging simulation purposes
			
			this.loadingAnimation.setVisible(true);
			
			
			} catch (IOException create_account_error) {
				create_account_error.printStackTrace();
			}
			
		});
		
	}
    
	
	@FXML
	private void validate_password(MouseEvent event) {
		
		if(!passwordField.getText().contentEquals("") & !passwordField2.getText().contentEquals("") & passwordField.getText().equals(passwordField2.getText())) {
			okayVerification.setVisible(true);
			warning.setVisible(false);
			signInButton.setDisable(false);
			
		} else {
			signInButton.setDisable(true);
			okayVerification.setVisible(false);
			warning.setVisible(true);
		}
		
	}
	
	
	@FXML
	void return_window(MouseEvent event) throws IOException  {
		Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
		LaunchClientApp.getStage().getScene().setRoot(root);
		
	}
	
	@FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }
	
}
