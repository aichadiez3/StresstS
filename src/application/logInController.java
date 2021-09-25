package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class logInController implements Initializable {

	
	@FXML
    private Pane logInPanel;
	
	@FXML
    private Pane registerPane;
	
	@FXML
    private Button logInButton;

    @FXML
    private TextField passwordField;

    @FXML
    private Label signInButton;

    @FXML
    private TextField usernameField;

    @FXML
    private ImageView exitButton;
    

    public logInController() {
		super();
		// TODO Auto-generated constructor stub
	}
    
   
	public Pane getRegisterPane() {
		return registerPane;
	}


	@FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void open_registration(MouseEvent event) throws IOException {
    	Pane registration_pane_fxml = FXMLLoader.load(getClass().getResource("RegistrationView.fxml"));
    	registerPane.setVisible(true);
    	registerPane.getChildren().removeAll();
		registerPane.getChildren().setAll(registration_pane_fxml);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}
