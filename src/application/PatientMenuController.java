package application;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PatientMenuController implements Initializable{
	
	
	Socket socket;
	private static PatientMenuController patient_controller;
	private logInController login_controller;
	private static Stage main_menu_stage;
	
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
    private Group homeButton;

    
    
    public static void setController(PatientMenuController controller) {
		patient_controller = controller;
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			Pane initialize_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
			menuPane.getChildren().removeAll();
			menuPane.getChildren().setAll(initialize_pane_fxml);
			
			enable_all_buttons();
			homeButton.setDisable(true);
			
		} catch (IOException init_error) {
			init_error.printStackTrace();
			alert_serverClosed_view();
		}
		
		
		
		
		optionsPane.setOnMouseEntered(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent event) {
				moreOptionsPane.setVisible(true);
				optionsPane.setVisible(false);
				menuPane.setEffect(new BoxBlur(4,4,4));
			}
		});
		
		moreOptionsPane.setOnMouseExited(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent arg0) {
				moreOptionsPane.setVisible(false);
				optionsPane.setVisible(true);
				menuPane.setEffect(null);
			}			
		});
		
		
		homeButton.setOnMouseClicked((MouseEvent event) -> {
			titlePane.setText("MENU");

			enable_all_buttons();
			homeButton.setDisable(true);
			try {
				Pane home_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(home_pane_fxml);
				
				
			} catch (IOException open_home_error) {
				open_home_error.printStackTrace();
				alert_serverClosed_view();
			}
			
		});
		
		
		
		userInfoButton.setOnMouseClicked((MouseEvent event) -> {
			titlePane.setText("Patient personal information");
			enable_all_buttons();
			userInfoButton.setDisable(true);
			try {
				Pane user_info_pane_fxml = FXMLLoader.load(getClass().getResource("PatientInfoView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(user_info_pane_fxml);
				
			} catch (IOException open_info_error) {
				open_info_error.printStackTrace();
				alert_serverClosed_view();
			}
			
		});
		
		healthParametersButton.setOnMouseClicked((MouseEvent event) -> {
			titlePane.setText("Health parameters");
			enable_all_buttons();
			healthParametersButton.setDisable(true);
			try {
				Pane health_pane_fxml = FXMLLoader.load(getClass().getResource("PatientHealthView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(health_pane_fxml);
				
			} catch (IOException open_info_error) {
				open_info_error.printStackTrace();
				alert_serverClosed_view();
			}
		});
		
		
		settingsButton.setOnMouseClicked((MouseEvent event) -> {
			titlePane.setText("Settings");
			enable_all_buttons();
			settingsButton.setDisable(true);
			try {
				Pane settings_pane_fxml = FXMLLoader.load(getClass().getResource("SettingsView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(settings_pane_fxml);
				
			} catch (IOException settings_error) {
				settings_error.printStackTrace();
				alert_serverClosed_view();
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
				
				// ---> To close connection to the server
				
					LaunchClientApp.dataOutputStream.writeUTF("end_client");
					LaunchClientApp.outputStream.flush();
			        LaunchClientApp.releaseResources(LaunchClientApp.dataInputStream, LaunchClientApp.dataOutputStream, LaunchClientApp.outputStream, LaunchClientApp.socket);
				
				
				
				
				
				
			} catch (IOException log_out_error) {
				log_out_error.printStackTrace();
				alert_serverClosed_view();
			}
			
		});
		
	}
	
	@FXML
    void close_app(MouseEvent event) throws IOException{
		
		// --------> Here closes the connection to server too
		LaunchClientApp.dataOutputStream.writeUTF("end_client");
		LaunchClientApp.outputStream.flush();
        LaunchClientApp.releaseResources(LaunchClientApp.dataInputStream, LaunchClientApp.dataOutputStream, LaunchClientApp.outputStream, LaunchClientApp.socket);

    	System.exit(0);
    }
	
	@FXML
	void minimize_window(MouseEvent event) {
		main_menu_stage = (Stage) anchorPane.getScene().getWindow();
		main_menu_stage.setIconified(true);
	}
	
	@FXML
    void resize_window(MouseEvent event) {
		main_menu_stage = (Stage) anchorPane.getScene().getWindow();
		main_menu_stage.setResizable(true);
    }
	
	void enable_all_buttons() {
		homeButton.setDisable(false);
		healthParametersButton.setDisable(false);
		userInfoButton.setDisable(false);
		homeButton.setDisable(false);
		settingsButton.setDisable(false);
	}
	
	void alert_serverClosed_view() {
		try {
			Pane initialize_pane_fxml = FXMLLoader.load(getClass().getResource("AlertView.fxml"));
			menuPane.getChildren().removeAll();
			menuPane.getChildren().setAll(initialize_pane_fxml);
			
			optionsPane.setDisable(true);
			
		} catch (IOException aler_open_error) {
			aler_open_error.printStackTrace();
		}
	}
	
	
	private static void releaseResources(OutputStream outputStream, Socket socket) {
        try {
            try {
                outputStream.close();

            } catch (IOException ex) {
                Logger.getLogger(LaunchClientApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(LaunchClientApp.class .getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	
}