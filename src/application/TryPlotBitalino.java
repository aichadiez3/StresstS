package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Vector;

import javax.bluetooth.RemoteDevice;

import Bitalino.Bitalino;
import Bitalino.BitalinoException;
import Bitalino.Frame;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TryPlotBitalino  extends Application {

	public static Frame[] frame;
	String macAddress="20:17:11:20:50:75";
	Integer SamplingRate=1000;
	Bitalino bitalino = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		
		
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time(in s)");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("ECG");

        LineChart<Float, Integer> ecgGraph = new LineChart(xAxis, yAxis);
        
        try {
            bitalino = new Bitalino();
            Vector<RemoteDevice> devices = bitalino.findDevices();

            bitalino.open(this.macAddress, this.SamplingRate);

            VBox vbox = new VBox(ecgGraph);

            Scene scene = new Scene(vbox, 400, 200);
            
            primaryStage.setScene(scene);
            primaryStage.setHeight(300);
            primaryStage.setWidth(1200);

            primaryStage.show();

            // ----> Start acquisition on analog channel A3 (EDA)
            int[] channelsToAcquire = {1};
            bitalino.start(channelsToAcquire);

            //Read in total 100 times
            for (int j = 0; j < 50; j++) {

                //Each time read a block of 10 samples 
                int block_size=10;
                frame = bitalino.read(block_size);

                //Print the samples
                for (int i = 0; i < frame.length; i++) {
                	
                	XYChart.Series dataEDA=new XYChart.Series();
                	System.out.println(frame[i].analog[1]);
                	dataEDA.getData().add(new XYChart.Data((j * block_size + i), frame[i].analog[0]));
                	ecgGraph.getData().add(dataEDA);
                	
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

        
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }

}
