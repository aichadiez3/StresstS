package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RegistrationController implements Initializable{

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
    private ImageView returnButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView usernameOk;

    @FXML
    private ImageView emailOk;
    
    
    
    @FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void return_window(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
		Main.getStage().getScene().setRoot(root);
		nameField.clear();
		surnameField.clear();
		emailField.clear();
		usernameField.clear();
		usernameOk.setVisible(false);
		emailOk.setVisible(false);
		warning_username.setVisible(false);
		warning_username.setVisible(false);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    

	
}
