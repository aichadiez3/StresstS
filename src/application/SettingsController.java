package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class SettingsController implements Initializable {

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		saveButton.setOnAction((ActionEvent event) -> {
try {
				
				/*
				 * SAVE HERE ALL DATA FROM TESTS FOR FURTHER EVALUATION AND ASSOCIATE THE SYMPTOMS TO THE PATIEND MEDICAL RECORD
				 */
				//LO MISMO, NO SE HACER LOS DATOS DEL LinkedList<Boolean> A String Y ADEMAS AQUI NO ESTAMOS HACIENDO NADA AUN
				LaunchClientApp.instruction = ("new_psycho," );
				
				//this.yes_sport.getId()
				
				Pane home_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
				mainPane.getChildren().removeAll();
				mainPane.getChildren().setAll(home_pane_fxml);
				
			} catch (IOException home_error) {
				home_error.printStackTrace();
			}
		});
		
	}

}