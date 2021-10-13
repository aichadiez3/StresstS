package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BitalinoController implements Initializable{

	private static Stage main_stage;
	
	@FXML
    private Pane mainPane;
	
    @FXML
    private Group helpButton;
    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		helpButton.setOnMouseClicked((MouseEvent event) -> {
			// Here we display a pane with the steps to follow for connecting the bitalino board + sensors
		});
		
	}

	@FXML
    void return_window(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PatientHealthView.fxml"));
		main_stage = (Stage) mainPane.getScene().getWindow();
		main_stage.close();
		Main.getStage().getScene().setRoot(root);
    }
	
	 @FXML
	 void exit_window(MouseEvent event) {
		 main_stage = (Stage) mainPane.getScene().getWindow();
		 main_stage.close();
	  }
	
}
