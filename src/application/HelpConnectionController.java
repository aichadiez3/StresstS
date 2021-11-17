package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelpConnectionController implements Initializable {

	private static Stage main_stage;
	
    @FXML
    private Pane mainPane;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	
    @FXML
    void exit_window(MouseEvent event) {
    	main_stage = (Stage) mainPane.getScene().getWindow();
		main_stage.close();
    }
    
}
