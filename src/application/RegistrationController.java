package application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
    
    @FXML
    private Group loadingAnimation;
    
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
					
					// AÑADIR AQUI LA CREACION DE USUARIO A DATABASE
					// Cuando esté hecho, añadir el código de debajo en este if
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
	void return_window(MouseEvent event) throws IOException  {
		Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
		LaunchClientApp.getStage().getScene().setRoot(root);
		
	}
	
	@FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }
	
}
