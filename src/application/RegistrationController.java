package application;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegistrationController implements Initializable{

	private logInController controller;
	private PatientMenuController patient_controller;
	private static Stage main_menu_stage;
	
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
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		signInButton.setOnAction((ActionEvent event) -> {
			try {
				// AÑADIR AQUI LA CREACION DE USUARIO A DATABASE
			
			// ---> Load new patient menu scene
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
			main_menu_stage = (Stage) registrationPane.getScene().getWindow();
			main_menu_stage.close();
			
			} catch (IOException create_account_error) {
				create_account_error.printStackTrace();
			}
			
		});
		
	}
    
	@FXML
	void return_window(MouseEvent event) throws IOException  {
		Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
		Main.getStage().getScene().setRoot(root);
		
	}
	
	@FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }
	
}
