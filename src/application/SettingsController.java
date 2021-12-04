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
import javafx.scene.input.MouseEvent;
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
    
    @FXML
    private Group okayVerification;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		saveButton.setDisable(true);
		
		
		saveButton.setOnAction((ActionEvent event) -> {
			try {
				
				LaunchClientApp.instruction = ("search_user_by_id, " + logInController.user_id);
				user_id = Integer.parseInt(LaunchClientApp.feedback);
				
			// QUEDA VERIFICAR SI EL NUEVO NOMBRE DE USUARIO NO EXISTE !!
			
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

	@FXML
	private void validate_password(MouseEvent event) {
		
		if(!passwordField.getText().equals(null) & !confirmPasswordField.getText().equals(null) & passwordField.getText().equals(confirmPasswordField.getText())) {
			okayVerification.setVisible(true);
			warning.setVisible(false);
			saveButton.setDisable(false);
			
		} else {
			saveButton.setDisable(true);
			okayVerification.setVisible(false);
			warning.setVisible(true);
		}
		
	}
}