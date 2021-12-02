package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Platform;

public class OtherParametersController implements Initializable {

	private PatientHealthController health_controller;
	private static Stage main_stage;
	private Integer counter;
	private double timelapse;
	private double progress;
	Random random = new Random();
	String ref_number = null;
	public Integer record_id;
			


	
	 @FXML
	    private Pane mainPane;

	    @FXML
	    private Group helpButton;

	    @FXML
	    private Spinner<Integer> oxygenSatSpinner;

	    @FXML
	    private Spinner<Integer> heartRateSpinner;

	    @FXML
	    private ImageView pulseOximeterImage;

	    @FXML
	    private Button saveButton;

	    @FXML
	    private Group counterGroup;

	    @FXML
	    private ImageView breathingRateImage;

	    @FXML
	    private Button startButton;

	    @FXML
	    private Group timerGroup;

	    @FXML
	    private Label timeCounter;

	    @FXML
	    private ProgressIndicator timerIndicator;

	    @FXML
	    private Button tapButton;


		public void setHealt_controller(PatientHealthController health_controller) {
			this.health_controller = health_controller;
		}



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		breathingRateImage.setOnMouseClicked((MouseEvent event1) -> {
			breathingRateImage.setVisible(false);
			
			
			startButton.setOnMouseClicked((MouseEvent event2) -> {
				ref_number = String.format("%04d", random.nextInt(10000));
				LaunchClientApp.instruction = "new_medical_record," + Date.valueOf(LocalDate.now()) + "," + ref_number + "," + null;
				record_id = Integer.parseInt(LaunchClientApp.feedback);
				
				startButton.setVisible(false);
				timerGroup.setVisible(true);
				
				counter = 0; //set the count to 0
				timelapse = 0.01;
				progress = 0.0;
				timerIndicator.setProgress(progress);
				
				Timer timer = new Timer();
			    timer.scheduleAtFixedRate(new TimerTask() {
			        public void run() {
			        	Platform.setImplicitExit(false);
			        	
			            if(progress >= 1.0) {
			                timer.cancel();
			                timer.purge();
			            } else {
			            	progress += (double) timelapse;
			            	Platform.runLater(() -> timerIndicator.setProgress(progress*3.33)); //for simulating 30secs of recording
			            }
			                
			        }
			    }, 0, 1000);
			
		
				tapButton.setOnAction((ActionEvent event) -> {
					counter();
				});
			});
			
		});
		
		
		pulseOximeterImage.setOnMouseClicked((MouseEvent event) -> {
			pulseOximeterImage.setVisible(false);
			oxygenSatSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
			heartRateSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 200));
		});
		
		
		saveButton.setOnMouseClicked((MouseEvent event3) -> {
			LaunchClientApp.instruction = ("new_physical," + oxygenSatSpinner + "," + heartRateSpinner + "," + Date.valueOf(LocalDate.now()) + "," + record_id);
		});
		
	}

	
	
	// ------> INCOMPLETE METHOD
	
	@FXML
    void save_parameters(ActionEvent event) {
		// Get all values
		oxygenSatSpinner.getValue();
		heartRateSpinner.getValue();
		
		// Enable buttons and images as in restart
		timerGroup.setVisible(false);
		startButton.setVisible(true);
		breathingRateImage.setVisible(true);
		pulseOximeterImage.setVisible(true);

		//Close the emergent window
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PatientHealthView.fxml"));
			main_stage = (Stage) mainPane.getScene().getWindow();
			main_stage.close();
			LaunchClientApp.getStage().getScene().setRoot(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }
	
	@FXML
    void return_window(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("PatientHealthView.fxml"));
		main_stage = (Stage) mainPane.getScene().getWindow();
		main_stage.close();
		LaunchClientApp.getStage().getScene().setRoot(root);
    }
	
	 @FXML
	 void exit_window(MouseEvent event) {
		 main_stage = (Stage) mainPane.getScene().getWindow();
		 main_stage.close();
	  }
	 
	 
	 public void counter(){
	        counter++;
	        timeCounter.setText("" + counter);
	 }
	 
	 
}
