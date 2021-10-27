package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientHealthController implements Initializable{

	private OtherParametersController parameters_controller;
	private BitalinoController bitalino_controller;
	private static Stage main_stage;
	
	@FXML
    private Pane healthPane;

    @FXML
    private Pane basicParametersButton;

    @FXML
    private Pane bitalinoButton;

    @FXML
    private Pane voiceButton;
	


	public static Stage getMain_stage() {
		return main_stage;
	}

	
	public void initialize(URL arg0, ResourceBundle arg1) {

		bitalinoButton.setOnMouseClicked((MouseEvent event) -> {
			
			try {
				Pane bitalino_pane_fxml = FXMLLoader.load(getClass().getResource("BitalinoView.fxml"));
				healthPane.getChildren().removeAll();
				healthPane.getChildren().setAll(bitalino_pane_fxml);
				
			} catch (IOException open_bitalino_error) {
				open_bitalino_error.printStackTrace();
			}
			
		});
		
		
		basicParametersButton.setOnMouseClicked((MouseEvent event) -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("OtherParametersView.fxml"));
				Parent root = (Parent) loader.load();
				this.parameters_controller = new OtherParametersController();
				this.parameters_controller = loader.getController();
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(new Scene(root));
				stage.show();
				
			} catch (IOException open_parameters_error) {
				open_parameters_error.printStackTrace();
			}
		});
		
		
	}

}
