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
	private Series dataECG;
	@SuppressWarnings("rawtypes")
	private Series dataEDA;
	
	
	@SuppressWarnings({ "exports", "rawtypes" })
	 public void setSeriesValues(Series dataECG, Series dataEDA) {
		this.dataECG = dataECG;
		this.dataEDA = dataEDA;
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
		
		
		if (dataECG==null) {
			ecgGraph.setVisible(false);
			
			edaGraph = new LineChart(new CategoryAxis(), new NumberAxis());
			edaGraph.getData().clear();
			edaGraph.getData().add(dataEDA);
			
		} else if(dataEDA==null) {
			edaGraph.setVisible(false);
			
			ecgGraph = new LineChart(new CategoryAxis(), new NumberAxis());
			ecgGraph.getData().clear();
			ecgGraph.getData().add(dataECG);
			
			System.out.println(dataECG.getData().toString());
		} else {
			ecgGraph = new LineChart(new CategoryAxis(), new NumberAxis());
			edaGraph = new LineChart(new CategoryAxis(), new NumberAxis());
			
			ecgGraph.getData().clear();
			edaGraph.getData().clear();
			
			ecgGraph.getData().add(dataECG);
			edaGraph.getData().add(dataEDA);
			
			System.out.println(dataECG.getData().toString());
		}
		
		
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

	@Override
	public String toString() {
		return "RecordingController [dataECG=" + dataECG.getData().toString() + ", dataEDA=" + dataEDA.getData().toString() + "]";
	}
	
	

}
