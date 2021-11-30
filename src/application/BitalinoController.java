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

	private static Stage main_stage;
	public HelpConnectionController help_controller;
	@SuppressWarnings("exports")
	public static Frame[] frame;
	private String macAddress;
	private Integer SamplingRate;
	Bitalino bitalino = null;
	
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
    private LineChart<Number, Number> ecgGraph;

    @FXML
    private LineChart<Number, Number> edaGraph;
    
    @FXML
    private ComboBox<String> freqSelection;
    
	
	@SuppressWarnings({ "unchecked", "removal", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // que es lo que quiero poner al iniciar ese panel
		
		ecgGraph.resize(1060, 232);
		edaGraph.resize(1060, 232);
		
		ObservableList<String> freqs = FXCollections.observableArrayList( "10","100","1000" );
		freqSelection.setItems(freqs);
		macAddressField.setText("20:17:11:20:50:75");
		//pasarle el MAC address
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
			ecgGraph.setVisible(true);
			edaGraph.setVisible(true);
			
	        try {
	        	set_buttons_invisible();
	        	
	        	final NumberAxis xAxis = new NumberAxis();
	            final NumberAxis yAxis = new NumberAxis();
	            //creating the chart
	            ecgGraph = new LineChart<Number, Number>(xAxis, yAxis);
	            edaGraph = new LineChart<Number,Number>(xAxis,yAxis);
	        	
	        	
	            bitalino = new Bitalino();
	            Vector<RemoteDevice> devices = bitalino.findDevices();

	            bitalino.open(this.macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channels A2 (ECG) and A3 (EDA)
	            int[] channelsToAcquire = {1,2};
	            bitalino.start(channelsToAcquire);
	            
	            ecgGraph.getData().clear();
	            edaGraph.getData().clear();
	            
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
	            
	            ecgGraph.getData().addAll(dataECG);
	            edaGraph.getData().addAll(dataEDA);
	            
	            
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
	        
	        //te abre el menu del test psicologico que lo haces visible cuando aabas de grabar el bitlino
	        proceedButton.setVisible(true); //<----------- ESTO HAY Q QUITARLO
	        
	        proceedButton.setOnMouseClicked((MouseEvent e)->{
	        	try {
	    			Pane test_pane_fxml = FXMLLoader.load(getClass().getResource("AnxietyTestView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(test_pane_fxml);
	    			
	    		} catch (IOException ev1) {
	    			// TODO Auto-generated catch block
	    			ev1.printStackTrace();
	    		}
	        });

		});
		
		edaTestButton.setOnMouseClicked((MouseEvent event) -> {

			edaGraph.setVisible(true);
			edaGraph.resize(1060, 477);
			chargingIndicator.setVisible(true);
			
	        try {
	        	
	        	set_buttons_invisible();
	            bitalino = new Bitalino();
	            
	            Vector<RemoteDevice> devices = bitalino.findDevices();
				
	            bitalino.open(this.macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channel A3 (EDA)
	            int[] channelsToAcquire = {2};
	            bitalino.start(channelsToAcquire);
	            
	            edaGraph.getData().clear();
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
	            
	            edaGraph.getData().addAll(dataEDA);
	            
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
	    			Pane test_pane_fxml = FXMLLoader.load(getClass().getResource("AnxietyTestView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(test_pane_fxml);
	    			
	    		} catch (IOException ev1) {
	    			// TODO Auto-generated catch block
	    			ev1.printStackTrace();
	    		}
	        });
		});
		
		ecgTestButton.setOnMouseClicked((MouseEvent event) -> {
			 
			ecgGraph.setVisible(true);
			ecgGraph.resize(1060, 477);
			chargingIndicator.setVisible(true);
			
	        try {
	            set_buttons_invisible();
	            bitalino = new Bitalino();
	            
	            Vector<RemoteDevice> devices = bitalino.findDevices();
				
	            bitalino.open(macAddress, this.SamplingRate);
	            
	            // ----> Start acquisition on analog channels A2 (ECG)
	            int[] channelsToAcquire = {1};
	            bitalino.start(channelsToAcquire);
	            
	            ecgGraph.getData().clear();
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
	                }
	            }
	            //stop acquisition
	            chargingIndicator.setVisible(false);
	            bitalino.stop();
	            
	            ecgGraph.getData().addAll(dataECG);
	            
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
	    			Pane test_pane_fxml = FXMLLoader.load(getClass().getResource("AnxietyTestView.fxml"));
	    			menuPane.getChildren().removeAll();
	    			menuPane.getChildren().setAll(test_pane_fxml);
	    			
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
	}
	 
	 
}
