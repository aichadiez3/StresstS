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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.*;

public class BitalinoController implements Initializable{

	private static Stage main_stage;
	public HelpConnectionController help_controller;
	@SuppressWarnings("exports")
	public static Frame[] frame;
	public String macAddress;
	public Integer SamplingRate;
	Bitalino bitalino = null;
	
	@FXML
    private AnchorPane menuPane;

    @FXML
    private Group allOptions;

    @FXML
    private Group edaTestButton;

    @FXML
    private Group ecgTestButton;

    @FXML
    private Group startButton;

    @FXML
    private Group helpButton;

    @FXML
    private Group proceedButton;

    @FXML
    private Pane configurationPane;

    @FXML
    private Group nextButton;

    @FXML
    private TextField macAddressField;
    
    @FXML
    private ImageView infoMACaddress;

    @FXML
    private ImageView MACimage;

    @FXML
    private Group chargingIndicator;

    @FXML
    private LineChart<Integer, Integer> ecgGraph;

    @FXML
    private LineChart<Integer, Integer> edaGraph;
    
    @FXML
    private ComboBox<String> freqSelection;
    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ecgGraph.resize(1060, 232);
		edaGraph.resize(1060, 232);
		
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
			this.SamplingRate=Integer.parseInt(freqSelection.getValue());
			ecgGraph.setVisible(true);
			edaGraph.setVisible(true);
			
	        try {
	        	set_buttons_invisible();
	        	
	            bitalino = new Bitalino();
	            Vector<RemoteDevice> devices = bitalino.findDevices();

	            bitalino.open(macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channels A2 (ECG) and A3 (EDA)
	            int[] channelsToAcquire = {1, 2};
	            bitalino.start(channelsToAcquire);
	            

	            //Read in total 100 times
	            for (int j = 0; j < 100; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                	// To get a JavaFX LineChart component to display any lines, you must provide it with a data series.
	                	// A data series is a list of data points. Each data point contains an X value and a Y value.
	                	XYChart.Series dataECG=new XYChart.Series<>();
	                	int x_axis=(j * block_size + i)/SamplingRate;
	                	
	                	dataECG.getData().add(new XYChart.Data<Integer, Integer>(x_axis, frame[i].analog[1]));
	                	ecgGraph.getData().add(dataECG);
	                	
	                	XYChart.Series<Integer,Integer> dataEDA=new XYChart.Series<>();
	                	dataEDA.getData().add(new XYChart.Data<Integer, Integer>(x_axis, frame[i].analog[2]));
	                	edaGraph.getData().add(dataEDA);
	                }
	            }
	            //stop acquisition
	            chargingIndicator.setVisible(false);
	            bitalino.stop();
	            
	            
	        } catch (BitalinoException ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (Throwable ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (bitalino != null) {
	                    bitalino.close();   //close bluetooth connection
	                    proceedButton.setVisible(true);
	                }
	            } catch (BitalinoException ex) {
	                Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	        
	        
	        proceedButton.setVisible(true); //<----------- ESTO HAY Q QUITARLO
	        
	        proceedButton.setOnMouseClicked((MouseEvent e)->{
	        	try {
	    			Pane health_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(health_pane_fxml);
	    			
	    		} catch (IOException ev1) {
	    			// TODO Auto-generated catch block
	    			ev1.printStackTrace();
	    		}
	        });

		});
		
		edaTestButton.setOnMouseClicked((MouseEvent event) -> {
			this.SamplingRate=Integer.parseInt(freqSelection.getValue());
			edaGraph.setVisible(true);
			edaGraph.resize(1060, 477);
			
	        try {
	        	
	        	set_buttons_invisible();
	            bitalino = new Bitalino();
	            
	            Vector<RemoteDevice> devices = bitalino.findDevices();
				
	            bitalino.open(macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channel A3 (EDA)
	            int[] channelsToAcquire = {2};
	            bitalino.start(channelsToAcquire);
	            
	            
	            //Read in total 100 times
	            for (int j = 0; j < 100; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;
	                frame = bitalino.read(block_size);

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                	
	                	XYChart.Series<Integer,Integer> dataEDA=new XYChart.Series<>();
	                	dataEDA.getData().add(new XYChart.Data<Integer, Integer>((j * block_size + i)/this.SamplingRate, frame[i].analog[2]));
	                	edaGraph.getData().add(dataEDA);
	                }
	            }
	            //stop acquisition
	            chargingIndicator.setVisible(false);
	            bitalino.stop();
	        } catch (BitalinoException ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (Throwable ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (bitalino != null) {
	                    bitalino.close();   //close bluetooth connection
	                    proceedButton.setVisible(true);
	                }
	            } catch (BitalinoException ex) {
	                Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }

	        proceedButton.setVisible(true); //<----------- ESTO HAY Q QUITARLO
	        
	        proceedButton.setOnMouseClicked((MouseEvent ev)->{
	        	try {
	    			Pane health_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(health_pane_fxml);
	    			
	    		} catch (IOException ev1) {
	    			// TODO Auto-generated catch block
	    			ev1.printStackTrace();
	    		}
	        });
		});
		
		ecgTestButton.setOnMouseClicked((MouseEvent event) -> {
			this.SamplingRate=Integer.parseInt(freqSelection.getValue());
			ecgGraph.setVisible(true);
			ecgGraph.resize(1060, 477);
			
	        try {
	            set_buttons_invisible();
	            bitalino = new Bitalino();
	            
	            Vector<RemoteDevice> devices = bitalino.findDevices();
				
	            bitalino.open(macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channels A2 (ECG)
	            int[] channelsToAcquire = {1};
	            bitalino.start(channelsToAcquire);
	            
	            
	            //Read in total 100 times
	            for (int j = 0; j < 100; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;
	                frame = bitalino.read(block_size);

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                	
	                	XYChart.Series<Integer,Integer> dataECG=new XYChart.Series<>();
	                	dataECG.getData().add(new XYChart.Data<Integer, Integer>((j * block_size + i)/this.SamplingRate, frame[i].analog[1]));
	                	ecgGraph.getData().add(dataECG);
	                }
	            }
	            //stop acquisition
	            chargingIndicator.setVisible(false);
	            bitalino.stop();
	        } catch (BitalinoException ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (Throwable ex) {
	            Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	        } finally {
	            try {
	                if (bitalino != null) {
	                    bitalino.close();   //close bluetooth connection
	                    proceedButton.setVisible(true);
	                }
	            } catch (BitalinoException ex) {
	                Logger.getLogger(BitalinoController.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	        
	        proceedButton.setVisible(true); //<----------- ESTO HAY Q QUITARLO
	        
	        proceedButton.setOnMouseClicked((MouseEvent e)->{
	        	//Close the stage
	    		try {
	    			Pane health_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(health_pane_fxml);
	    			
	    		} catch (IOException ev1) {
	    			// TODO Auto-generated catch block
	    			ev1.printStackTrace();
	    		}
	        });

		});
		
		helpButton.setOnMouseClicked((MouseEvent event) -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpElectrodesView.fxml"));
				Parent root = (Parent) loader.load();
				this.help_controller = new HelpConnectionController();
				this.help_controller = loader.getController();
				
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(new Scene(root));
				stage.show();
				
			} catch (IOException open_help_error) {
				open_help_error.printStackTrace();
			}
		});
		
	}

	
	void set_buttons_invisible() {
		ecgTestButton.setVisible(false);
		edaTestButton.setVisible(false);
		startButton.setVisible(false);
		chargingIndicator.setVisible(true);
	}
	 
	 
}
