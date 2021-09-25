package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class logInController implements Initializable {

	@FXML
    private Pane logInPanel;
	
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
    

    @FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }

    @FXML
    void open_registration(MouseEvent event) throws IOException {
		Pane registration_panel_fxml = FXMLLoader.load(getClass().getResource("RegistrationView.fxml"));
		logInPanel.getChildren().removeAll();
		logInPanel.getChildren().setAll(registration_panel_fxml);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
