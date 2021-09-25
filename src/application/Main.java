package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	private static Stage stage;
	
	public static Stage getStage() {
		return stage;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("LogInView.fxml"));
			primaryStage.setTitle("Log in page");
			Scene scene = new Scene(root);
			scene.getStylesheets().addAll(getClass().getResource("dark-theme.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			stage = primaryStage;
			primaryStage.show();
			
		} catch(IOException fatal_error) {
			fatal_error.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
