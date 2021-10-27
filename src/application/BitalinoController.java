package application;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.bluetooth.RemoteDevice;

import Bitalino.Bitalino;
import Bitalino.BitalinoException;
import Bitalino.Frame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class BitalinoController implements Initializable{

	private static Stage main_stage;
	@SuppressWarnings("exports")
	public static Frame[] frame;
	public String macAddress;
	public Integer SamplingRate;
	
	@FXML
    private AnchorPane menuPane;

    @FXML
    private Group allOptions;

    @FXML
    private Group edaTestButton;

    @FXML
    private Group ecgTestButton;

    @FXML
    private ImageView startButton;
    
    @FXML
    private Group helpButton;
    
    @FXML
    private ImageView MACimage;

    @FXML
    private ImageView infoMACaddress;

    @FXML
    private Pane configurationPane;

    @FXML
    private Group nextButton;

    @FXML
    private TextField macAddressField;

    @FXML
    private LineChart<Integer, Integer> ecgGraph;

    @FXML
    private LineChart<Integer, Integer> edaGraph;
    
    @FXML
    private ComboBox<String> freqSelection;
    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList<String> freqs = FXCollections.observableArrayList( "10","100","1000" );
		freqSelection.setItems(freqs);
		macAddressField.setText("20:17:11:20:50:75");
		
		infoMACaddress.setOnMouseEntered(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent arg0) {
				MACimage.setVisible(true);
				configurationPane.setEffect(new BoxBlur(4,4,4));
				MACimage.setEffect(null);
			}
			
		});
		
		infoMACaddress.setOnMouseExited(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent arg0) {
				MACimage.setVisible(false);
				configurationPane.setEffect(null);
			}
			
		});
		
		
		nextButton.setOnMouseClicked((MouseEvent event) -> {
			configurationPane.setVisible(false);
			allOptions.setVisible(true);
			helpButton.setDisable(false);
            this. macAddress = macAddressField.getText();
            this.SamplingRate = Integer.parseInt(this.freqSelection.getValue());
            menuPane.setEffect(null);
		});
		
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			ecgGraph.setVisible(true);
			edaGraph.setVisible(true);
			edaTestButton.setVisible(false);
			ecgTestButton.setVisible(false);
			
			Bitalino bitalino = null;
	        try {
	            bitalino = new Bitalino();
	            Vector<RemoteDevice> devices = bitalino.findDevices();
	            System.out.println(devices);

	            bitalino.open(macAddress, this.SamplingRate);

	            // ----> Start acquisition on analog channels A2 (ECG) and A3 (EDA)
	            int[] channelsToAcquire = {1, 2};
	            bitalino.start(channelsToAcquire);

	            //Read in total 10000000 times
	            for (int j = 0; j < 10000000; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;
	                frame = bitalino.read(block_size);

	                System.out.println("size block: " + frame.length);

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                    System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
	                            + frame[i].analog[0] + " "
	                            + frame[i].analog[1] + " "
	                    //  + frame[i].analog[2] + " "
	                    //  + frame[i].analog[3] + " "
	                    //  + frame[i].analog[4] + " "
	                    //  + frame[i].analog[5]
	                    );

	                }
	            }
	            //stop acquisition
	            bitalino.stop();
	        } catch (BitalinoException ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (Throwable ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (bitalino != null) {
	                    bitalino.close();   //close bluetooth connection
	                }
	            } catch (BitalinoException ex) {
	                Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }

		});
		
		edaTestButton.setOnMouseClicked((MouseEvent event) -> {
			edaGraph.setVisible(true);
			edaTestButton.setVisible(false);
			startButton.setVisible(false);
			
			Bitalino bitalino = null;
	        try {
	            bitalino = new Bitalino();
	            Vector<RemoteDevice> devices = bitalino.findDevices();
	            System.out.println(devices);

	            bitalino.open(macAddress, this.SamplingRate);

	            // ----> Start acquisition on analog channel A3 (EDA)
	            int[] channelsToAcquire = {2};
	            bitalino.start(channelsToAcquire);

	            //Read in total 10000000 times
	            for (int j = 0; j < 10000000; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;
	                frame = bitalino.read(block_size);

	                System.out.println("size block: " + frame.length);

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                    System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
	                            + frame[i].analog[0] + " "
	                            + frame[i].analog[1] + " "
	                    //  + frame[i].analog[2] + " "
	                    //  + frame[i].analog[3] + " "
	                    //  + frame[i].analog[4] + " "
	                    //  + frame[i].analog[5]
	                    );

	                }
	            }
	            //stop acquisition
	            bitalino.stop();
	        } catch (BitalinoException ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (Throwable ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (bitalino != null) {
	                    bitalino.close();   //close bluetooth connection
	                }
	            } catch (BitalinoException ex) {
	                Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }

			
			
		});
		
		ecgTestButton.setOnMouseClicked((MouseEvent event) -> {
			ecgGraph.setVisible(true);
			ecgTestButton.setVisible(false);
			startButton.setVisible(false);
			
			Bitalino bitalino = null;
	        try {
	            bitalino = new Bitalino();
	            Vector<RemoteDevice> devices = bitalino.findDevices();
	            System.out.println(devices);

	            bitalino.open(macAddress, this.SamplingRate);

	            // ----> Start acquisition on analog channels A2 (ECG)
	            int[] channelsToAcquire = {1};
	            bitalino.start(channelsToAcquire);

	            //Read in total 10000000 times
	            for (int j = 0; j < 10000000; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;
	                frame = bitalino.read(block_size);

	                System.out.println("size block: " + frame.length);

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                    System.out.println((j * block_size + i) + " seq: " + frame[i].seq + " "
	                            + frame[i].analog[0] + " "
	                            + frame[i].analog[1] + " "
	                    //  + frame[i].analog[2] + " "
	                    //  + frame[i].analog[3] + " "
	                    //  + frame[i].analog[4] + " "
	                    //  + frame[i].analog[5]
	                    );

	                }
	            }
	            //stop acquisition
	            bitalino.stop();
	        } catch (BitalinoException ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (Throwable ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (bitalino != null) {
	                    bitalino.close();   //close bluetooth connection
	                }
	            } catch (BitalinoException ex) {
	                Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }

		});
		
		helpButton.setOnMouseClicked((MouseEvent event) -> {
			// Here we display a pane with the steps to follow for connecting the bitalino board + sensors
		});
		
	}

	@FXML
    void return_window(MouseEvent event) throws IOException {
		/*
		Parent root = FXMLLoader.load(getClass().getResource("PatientHealthView.fxml"));
		main_stage = (Stage) mainPane.getScene().getWindow();
		main_stage.close();
		Main.getStage().getScene().setRoot(root);
		*/
		Pane health_pane_fxml = FXMLLoader.load(getClass().getResource("PatientHealthView.fxml"));
		menuPane.getChildren().removeAll();
		menuPane.getChildren().setAll(health_pane_fxml);
    }
	 
	 
}
