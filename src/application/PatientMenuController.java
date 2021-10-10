package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientMenuController implements Initializable{

	private static Stage main_menu_stage;
	private logInController login_controller;
	
	@FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane menuPane;

    @FXML
    private Pane optionsPane;

    @FXML
    private Label titlePane;

    @FXML
    private Pane moreOptionsPane;

    @FXML
    private Group logoutButton;

    @FXML
    private Group userInfoButton;

    @FXML
    private Group healthParametersButton;

    @FXML
    private Group settingsButton;

    @FXML
    private Group statisticsButton;

    @FXML
    private Group homeButton;
    
    
	public Pane getMenuPane() {
		return menuPane;
	}
	public void setMenuPane(Pane menuPane) {
		this.menuPane = menuPane;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		optionsPane.setOnMouseEntered(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent event) {
				moreOptionsPane.setVisible(true);
				optionsPane.setVisible(false);
			}
		});
		
		moreOptionsPane.setOnMouseExited(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent arg0) {
				moreOptionsPane.setVisible(false);
				optionsPane.setVisible(true);
			}			
		});
		
		/***
		homeButton.setOnMouseClicked((MouseEvent event) -> {
			titlePane.setText("MENU");
			try {
				Pane menu_pane_fxml = FXMLLoader.load(getClass().getResource("PatientMenuView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(menu_pane_fxml);
				
			} catch (IOException open_menu_error) {
				open_menu_error.printStackTrace();
			}
		});
		
		**/
		
		userInfoButton.setOnMouseClicked((MouseEvent event) -> {
			titlePane.setText("Patient personal information");
			try {
				Pane user_info_pane_fxml = FXMLLoader.load(getClass().getResource("PatientInfoView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(user_info_pane_fxml);
				
			} catch (IOException open_info_error) {
				open_info_error.printStackTrace();
			}
			
		});
		
		healthParametersButton.setOnMouseClicked((MouseEvent event) -> {
			titlePane.setText("Health parameters");
			try {
				Pane health_pane_fxml = FXMLLoader.load(getClass().getResource("PatientInfoView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(health_pane_fxml);
				
			} catch (IOException open_info_error) {
				open_info_error.printStackTrace();
			}
		});
		
		
		logoutButton.setOnMouseClicked((MouseEvent event) -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInView.fxml"));
				Parent root = (Parent) loader.load();
				this.login_controller = new logInController();
				this.login_controller = loader.getController();
				
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(new Scene(root));
				stage.show();
				
				// ---> To close the menu window
				
				main_menu_stage = (Stage) anchorPane.getScene().getWindow();
				main_menu_stage.close();
				
				
			} catch (IOException log_out_error) {
				log_out_error.printStackTrace();
			}
			
		});
		
	}
	
	@FXML
    void close_app(MouseEvent event) {
    	System.exit(0);
    }
	
	@FXML
	void minimize_window(MouseEvent event) {
		main_menu_stage = (Stage) anchorPane.getScene().getWindow();
		main_menu_stage.setIconified(true);
	}
	

}