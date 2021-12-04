package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientHealthController implements Initializable{

	@SuppressWarnings("unused")
	private OtherParametersController parameters_controller;
	private PatientHealthController health_controller;
	private static Stage main_stage;
	public static Integer bitalino_id;
	Random random = new Random();
	Integer ref_number;
	public static Integer record_id;
	
	@FXML
    private Pane healthPane;

    @FXML
    private Pane basicParametersButton;

    @FXML
    private Pane bitalinoButton;

    @FXML
    private Pane testButton;
    
    @FXML
    private ProgressBar progressBar;
	
    @FXML
    private Button startButton;


	public static Stage getMain_stage() {
		return main_stage;
	}

	
	// ----------> GETTERS AND SETTERS <-------------
	
	public PatientHealthController getHealth_controller() {
		return health_controller;
	}

	public void setHealth_controller(PatientHealthController health_controller) {
		this.health_controller = health_controller;
	}



	public Pane getBasicParametersButton() {
		return basicParametersButton;
	}
	public void setBasicParametersButton(Pane basicParametersButton) {
		this.basicParametersButton = basicParametersButton;
	}

	public Pane getBitalinoButton() {
		return bitalinoButton;
	}
	public void setBitalinoButton(Pane bitalinoButton) {
		this.bitalinoButton = bitalinoButton;
	}

	public Pane getTestButton() {
		return testButton;
	}
	public void setTestButton(Pane testButton) {
		this.testButton = testButton;
	}


	public void initialize(URL arg0, ResourceBundle arg1) {

		progressBar.setVisible(false);
		
		basicParametersButton.setDisable(true);
			basicParametersButton.setOpacity(0.3);
		bitalinoButton.setDisable(true);
			bitalinoButton.setOpacity(0.3);
		testButton.setDisable(true);
			testButton.setOpacity(0.3);
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			
			//CREO UN REF_NUMBER RANDOM Y ME CREO UN NUEVO MED_RECORD DONDE GRABAR LOS DATOS QUE VOY A MEDIR
			ref_number = (int)Math.floor(Math.random()*(2147483647-1000000000)+1000000000);
			
		/*
			LaunchClientApp.instruction = "new_medical_record," + Date.valueOf(LocalDate.now()) + "," + ref_number.toString() + "," + null;
			System.out.println(LaunchClientApp.instruction);
			
			//ESTO DEVUELVE NULL porque el server no nos esta devolviendo datos, algo falla con ese dataOutputStream
			record_id = Integer.parseInt(LaunchClientApp.feedback);
			System.out.println(LaunchClientApp.feedback);
		*/
			
			basicParametersButton.setDisable(false);
				basicParametersButton.setOpacity(1);
			bitalinoButton.setDisable(false);
				bitalinoButton.setOpacity(1);
			testButton.setDisable(false);
				testButton.setOpacity(1);
			startButton.setVisible(false);
			progressBar.setVisible(true);
		});
		
		
		bitalinoButton.setOnMouseClicked((MouseEvent event) -> {
			
			try {
				Pane bitalino_pane_fxml = FXMLLoader.load(getClass().getResource("BitalinoView.fxml"));
				healthPane.getChildren().removeAll();
				healthPane.getChildren().setAll(bitalino_pane_fxml);
				
				
				// CUANDO ABRE ESTE NUEVO PANEL, AUTOMÁTICAMENTE DEBE CREAR UN NUEVO BITALINO_TEST
				LaunchClientApp.instruction = "new_bitalino_test";
				bitalino_id = Integer.parseInt(LaunchClientApp.feedback);
				
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
		
		testButton.setOnMouseClicked((MouseEvent event) -> {
			try {
				Pane test_pane_fxml = FXMLLoader.load(getClass().getResource("AnxietyTestView.fxml"));
				healthPane.getChildren().removeAll();
				healthPane.getChildren().setAll(test_pane_fxml);
				
				
			} catch (IOException test_error) {
				test_error.printStackTrace();
			}
		});
		
	}

}
