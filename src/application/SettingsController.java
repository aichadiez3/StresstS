package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SettingsController implements Initializable {

    @FXML
    private Pane healthPane;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField emailField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}