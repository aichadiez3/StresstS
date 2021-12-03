package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SettingsController implements Initializable {

	private Integer user_id;
	
    @FXML
    private Pane mainPane;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField emailField;
    
    @FXML
    private Group warning;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//------> No implementado q espere a que todos los campos estén rellenados para hacerlo habil
		
		Boolean check = validate_password();
		System.out.println(check);
		
		saveButton.setOnAction((ActionEvent event) -> {
			try {
				LaunchClientApp.instruction = ("change_user_info, " + passwordField.getText() + ", " + emailField.getText() + ", " + logInController.user_id);
				user_id = Integer.parseInt(LaunchClientApp.feedback);
				
				Pane home_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
				mainPane.getChildren().removeAll();
				mainPane.getChildren().setAll(home_pane_fxml);
			
			} catch (IOException home_error) {
				home_error.printStackTrace();
			}
		});
		
	}

	
	public boolean validate_password() {
		
		while(true) {
			
			if(passwordField.getText()!=null & confirmPasswordField.getText()!=null & passwordField.getText().equals(confirmPasswordField.getText())) {
			
				saveButton.setDisable(false);
				return true;
				
			} else {
				warning.setVisible(true);
				return false;
			}
		}
	}
}