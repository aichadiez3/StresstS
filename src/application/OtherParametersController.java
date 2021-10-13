package application;

import java.io.IOException;
import java.net.URL;
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

public class OtherParametersController implements Initializable {

	private static Stage main_stage;
	private Integer counter;
	private Integer seconds;
	private Float progress;
	
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

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		breathingRateImage.setOnMouseClicked((MouseEvent event1) -> {
			breathingRateImage.setVisible(false);
			
			
			startButton.setOnMouseClicked((MouseEvent event2) -> {
				disable_buttons();
				timerGroup.setVisible(true);
				
				counter = 0; //set the count to 0
				seconds = 0;
				progress = (float) 0.0;
				timerIndicator.setProgress(progress);
				
				Timer timer = new Timer(true);
				timer.scheduleAtFixedRate(new TimerTask() {
					
					@Override
				    public void run() {
						if (progress <= 1.0) {
							progress = (float) (progress + seconds/100);
							timerIndicator.setProgress(progress);
							seconds = seconds++;
						} else
							timer.cancel(); //stop the timer when 100secs have passed
				    }
				}, 0,1000); // 1000 means 1000ms, 1 second play before get executed 
				 				
		
				tapButton.setOnAction((ActionEvent event) -> {
					timeCounter.setText("" + counter);
					System.exit(0);
					counter();
				});
			});
			
		});
		
		
		pulseOximeterImage.setOnMouseClicked((MouseEvent event) -> {
			pulseOximeterImage.setVisible(false);
			oxygenSatSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
			heartRateSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 200));
		});
		
	}

	
	
	// ------> INCOMPLETE METHOD
	
	@FXML
    void save_parameters(ActionEvent event) {
		oxygenSatSpinner.getValue();
		heartRateSpinner.getValue();
		
		breathingRateImage.setVisible(true);
		pulseOximeterImage.setVisible(true);
		enable_buttons();
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
	 
	 void disable_buttons() {
		 startButton.setVisible(false);
		 saveButton.setDisable(true);
	 }
	 
	 void enable_buttons() {
		 startButton.setVisible(true);
		 saveButton.setDisable(false);
		 timerGroup.setVisible(false);
	 }
	
	 public void counter(){
	        counter++;
	        timeCounter.setText("" + counter);
	 }
	 
	 
}
