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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SettingsController implements Initializable {

	private static Integer user_id;
	
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
    private Group okayMessage;
    
    @FXML
    private Group okayVerification;
    
    @FXML
    private Label warningText;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		saveButton.setDisable(true);
		
		
		saveButton.setOnAction((ActionEvent event) -> {
			try {
				
				// COMPROBATIONS ARE ALREADY DONE IN FXML FUNCTIONS
				
				LaunchClientApp.instruction = ("search_user_by_id, " + logInController.user_id);
				LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
				user_id = Integer.parseInt(LaunchClientApp.dataInputStream.readUTF());
				System.out.println(user_id);
				
				LaunchClientApp.dataOutputStream.writeUTF("change_user_info, " + passwordField.getText() + "," + emailField.getText() + "," + logInController.user_id);
				System.out.println("User updated " + LaunchClientApp.dataInputStream.readUTF());
				
				Pane home_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
				mainPane.getChildren().removeAll();
				mainPane.getChildren().setAll(home_pane_fxml);
						
			
			} catch (IOException home_error) {
				home_error.printStackTrace();
			}
		});
		
	}

	@FXML
	void validate_password(MouseEvent event) {
		
		if(!passwordField.getText().equals(null) & !confirmPasswordField.getText().equals(null) & passwordField.getText().equals(confirmPasswordField.getText())) {
			okayVerification.setVisible(true);
			warningText.setText("ERROR! The password must match");
			warning.setVisible(false);
			saveButton.setDisable(false);
			okayMessage.setVisible(false);
			
		} else {
			saveButton.setDisable(true);
			okayVerification.setVisible(false);
			warning.setVisible(true);
			okayMessage.setVisible(false);
		}
		
	}
	
	@FXML
	void validate_email(MouseEvent event) throws IOException {
		if(!emailField.getText().equals("")) {
			
			LaunchClientApp.dataOutputStream.writeUTF("search_existent_email,"+emailField.getText());
			
			if (LaunchClientApp.dataInputStream.readUTF().equals("wrong")) {
				warning.setVisible(true);
				warningText.setText("Email already exists!");
				saveButton.setDisable(true);
				okayMessage.setVisible(false);
			} else {
				okayMessage.setVisible(true);
				warning.setVisible(false);
			}
			
		} else {
			okayMessage.setVisible(false);
			warning.setVisible(false);
		}
	}
	
}