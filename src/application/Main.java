package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;


public class Main extends Application {
	
	private static Stage stage;
	private double xOffset = 0;
	private double yOffset = 0;
	
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
			
			
			// ----> This is to be able to move the window
			
			root.setOnMousePressed(new EventHandler<MouseEvent> () {

				@Override
				public void handle(MouseEvent arg0) {
					xOffset = arg0.getSceneX();
					yOffset = arg0.getSceneY();
				}
				
			});
			
			root.setOnMouseDragged(new EventHandler<MouseEvent> () {

				@Override
				public void handle(MouseEvent arg0) {
					stage.setX(arg0.getSceneX() - xOffset);
					stage.setY(arg0.getSceneY() - yOffset);
				}
				
			});
			
		} catch(IOException fatal_error) {
			fatal_error.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
