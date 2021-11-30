package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class RecordingController implements Initializable {
	
	@SuppressWarnings("rawtypes")
	private static  Series dataECG;
	@SuppressWarnings("rawtypes")
	private static Series dataEDA;
	
	
	@SuppressWarnings({ "exports", "rawtypes" })
	 public static void setSeriesValues(Series ECG, Series EDA) {
		dataECG = ECG;
		dataEDA = EDA;
	}
	
	public RecordingController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@FXML
	private Pane mainPane;
	
    @FXML
    private Group proceedButton;
	
	@FXML
    private LineChart<Number, Number> ecgGraph;

    @FXML
    private LineChart<Number, Number> edaGraph;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
			ecgPlot(dataECG);
			edaPlot(dataEDA);
		
		
        proceedButton.setOnMouseClicked((MouseEvent e)->{
        	try {
    			Pane psico_pane_fxml = FXMLLoader.load(getClass().getResource("AnxietyTestView.fxml"));
    			mainPane.getChildren().removeAll();
    			mainPane.getChildren().setAll(psico_pane_fxml);
    			
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        });
        
		
	}

	public LineChart ecgPlot(Series dataECG) {
		try {
			ecgGraph = new LineChart(new CategoryAxis(), new NumberAxis());
			
			ecgGraph.getData().clear();
			ecgGraph.setCreateSymbols(false);
			ecgGraph.getData().add(dataECG);
			
			return ecgGraph;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public LineChart edaPlot(Series dataEDA) {
		try {
			edaGraph = new LineChart(new CategoryAxis(), new NumberAxis());
			
			edaGraph.getData().clear();
			edaGraph.setCreateSymbols(false);
			edaGraph.getData().add(dataEDA);
			
			return edaGraph;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	

}
