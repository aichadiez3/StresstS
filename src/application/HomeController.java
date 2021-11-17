package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class HomeController implements Initializable{

	@FXML
    private Pane menuPane;

    @FXML
    private Label dayLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label countCheckUpLabel;
    
    
	public HomeController() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


		TimeLocalZoneSetter time=new TimeLocalZoneSetter();
		
		timeLabel.setText(time.getTimeString());
		dayLabel.setText(time.getDateString());
		
		
		
	}

}
