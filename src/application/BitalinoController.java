package application;

import java.io.FileWriter;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.bluetooth.RemoteDevice;
import javax.persistence.criteria.Root;

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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
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

	public HelpConnectionController help_controller;
	@SuppressWarnings("exports")
	public static Frame[] frame;
	private String macAddress;
	private Integer SamplingRate;
	Bitalino bitalino = null;
	Integer ecgId, edaId;
	private String[] root;
	
	
	@SuppressWarnings("rawtypes")
	private Series dataECG = new XYChart.Series();
	@SuppressWarnings("rawtypes")
	private Series dataEDA = new XYChart.Series();
	private Float x_axis;
	private Integer medianValue=500;
	
	
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
    private ComboBox<String> freqSelection;
    
    
    
	public BitalinoController() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMacAddress() {
		return macAddress;
	}
	public Integer getSamplingRate() {
		return SamplingRate;
	}
	
	
	@SuppressWarnings({ "unchecked", "removal", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // que es lo que quiero poner al iniciar ese panel
		
		
		ObservableList<String> freqs = FXCollections.observableArrayList( "10","100","1000" );
		freqSelection.setItems(freqs);
		macAddressField.setText("20:17:11:20:50:75");
		//pasarle el MAC address as follows
		//macAddressField.getText();
		
		
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
			
			chargingIndicator.setVisible(true);
			
	        try {
	        	set_buttons_invisible();
	        	
	        	
	            bitalino = new Bitalino();
	            Vector<RemoteDevice> devices = bitalino.findDevices();

	            bitalino.open(this.macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channels A2 (ECG) and A3 (EDA)
	            int[] channelsToAcquire = {1,2};
	            bitalino.start(channelsToAcquire);
	            
	            dataECG=new XYChart.Series();
	            dataEDA=new XYChart.Series();
	            
	            //Read in total 100 times
	            for (int j = 0; j < 100; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;
	                frame = bitalino.read(block_size);

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                	// To get a JavaFX LineChart component to display any lines, you must provide it with a data series.
	                	// A data series is a list of data points. Each data point contains an X value and a Y value.
	                	x_axis= new Float((j * block_size + i + 0.16667)/SamplingRate);

	                	dataECG.getData().add(new XYChart.Data(x_axis.floatValue(), frame[i].analog[0] - medianValue));
	                	
	                	dataEDA.getData().add(new XYChart.Data(x_axis.floatValue(), frame[i].analog[1]));
	                }
	            }
	            //stop acquisition
	            chargingIndicator.setVisible(false);
	            bitalino.stop();
	            
	            RecordingController.setSeriesValues(dataECG, dataEDA);
	            
	            
	            try {
	            	
	            root = auto_save_bitalino_data(dataECG.getData().toString(), dataEDA.getData().toString()).split(";");
	            
	            LaunchClientApp.instruction="new_ecg, " + root[0].toString() + "," + PatientHealthController.bitalino_id;
	            LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
	            LaunchClientApp.dataOutputStream.writeUTF("new_eda, " + root[1].toString() + "," + PatientHealthController.bitalino_id);

	    			Pane test_pane_fxml = FXMLLoader.load(getClass().getResource("PatientHealthView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(test_pane_fxml);
	    		} catch (IOException init_error) {
	    			init_error.printStackTrace();
	    		}
	            
	            
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

			chargingIndicator.setVisible(true);
			
	        try {
	        	
	        	set_buttons_invisible();
	            bitalino = new Bitalino();
	            
	            
	            Vector<RemoteDevice> devices = bitalino.findDevices();
				
	            bitalino.open(this.macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channel A3 (EDA)
	            int[] channelsToAcquire = {2};
	            bitalino.start(channelsToAcquire);
	            
	            dataEDA=new XYChart.Series();
	            
	            //Read in total 100 times
	            for (int j = 0; j < 100; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;
	                frame = bitalino.read(block_size);

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                	
	                	x_axis= new Float((j * block_size + i + 0.16667)/SamplingRate);	                	
	                	dataEDA.getData().add(new XYChart.Data(x_axis.floatValue(), frame[i].analog[1]));
	                }
	            }
	            //stop acquisition
	            chargingIndicator.setVisible(false);
	            bitalino.stop();
	            
	            RecordingController.setSeriesValues(null, dataEDA);
	            
	            root = auto_save_bitalino_data(null, dataEDA.getData().toString()).split(";");
	            LaunchClientApp.dataOutputStream.writeUTF("new_eda, " + root[1].toString() + "," + PatientHealthController.bitalino_id);
	            
	            
	            System.out.println(edaId);
	            // Once the recording stops, it opens the linecharts corresponding to the tests
	            
	            try {
	    			Pane test_pane_fxml = FXMLLoader.load(getClass().getResource("AnxietyTextView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(test_pane_fxml);
	    		} catch (IOException init_error) {
	    			init_error.printStackTrace();
	    		}
	            
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
			
			try { 
			
			chargingIndicator.setVisible(true);
			
	        
	            set_buttons_invisible();
	            bitalino = new Bitalino();
	            
	            
	            
	            Vector<RemoteDevice> devices = bitalino.findDevices();
				
	            bitalino.open(macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channels A2 (ECG)
	            int[] channelsToAcquire = {1};
	            bitalino.start(channelsToAcquire);
	            
	            
	            dataECG=new XYChart.Series();
	            
	            //Read in total 100 times
	            for (int j = 0; j < 100; j++) {

	                //Each time read a block of 10 samples 
	                int block_size=10;
	                frame = bitalino.read(block_size);

	                //Print the samples
	                for (int i = 0; i < frame.length; i++) {
	                	
	                	x_axis= new Float((j * block_size + i + 0.16667)/SamplingRate);
	                	dataECG.getData().add(new XYChart.Data(x_axis.floatValue(), frame[i].analog[0] - medianValue));
	                	//System.out.println(String.valueOf(x_axis.floatValue()) + " value:" + String.valueOf(frame[i].analog[0]-medianValue));
	                }
	            }
	            //stop acquisition
	            chargingIndicator.setVisible(false);
	            bitalino.stop();
	            
	            RecordingController.setSeriesValues(dataECG, null);
	            
	            root = auto_save_bitalino_data(dataECG.getData().toString(), null).split(";");
	       
	            LaunchClientApp.instruction="new_ecg, " + root[0].toString() + "," + PatientHealthController.bitalino_id;
	            LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
	            
	            
	            try {
	    			Pane test_pane_fxml = FXMLLoader.load(getClass().getResource("AnxietyTestView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(test_pane_fxml);
	    		} catch (IOException init_error) {
	    			init_error.printStackTrace();
	    		}
	            
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
	}
	 
	 
	String auto_save_bitalino_data(String dataEcg, String dataEda) {
		try {
			FileWriter writer1,writer2;
			String ecg_root="", eda_root="";
			
			if(dataEcg!=null) {
			  ecg_root ="./src/.bitalinoResults/ecg_results_" +PatientHealthController.ref_number +".txt";
			  writer1 = new FileWriter(ecg_root);
		      writer1.write(dataEcg);
		      writer1.close();
			}
		      
			if(dataEda!=null) {
				eda_root = "./src/.bitalinoResults/eda_results_" +PatientHealthController.ref_number +".txt";
		      writer2 = new FileWriter(eda_root);
		      writer2.write(dataEda);
		      writer2.close();
			} 
		      System.out.println("Successfully wrote to the file.");
		      String output=ecg_root+";"+eda_root;
		    return output;
		    
		} catch (IOException file_creation_error) {
		      System.out.println("An error occurred.");
		      file_creation_error.printStackTrace();
		      return null;
		}
	}
	
}
