package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class logInController implements Initializable {

    @FXML
    private Button logInButton;

    @FXML
    private TextField passwordField;

    @FXML
    private Label signInButton;

    @FXML
    private TextField usernameField;

    @FXML
    private ImageView exitButton;
    
    @FXML
    private Label exit;

    @FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void open_registration(MouseEvent event) {
    	System.exit(0);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
