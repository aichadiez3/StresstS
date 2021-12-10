package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AlertController implements Initializable {

	 @FXML
	 private Label textLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runnable = new Runnable() {
            int countdownStarter = 5;

            public void run() {

            		countdownStarter--;
	                textLabel.setText("The application will stop in a few seconds... ");
	                
	                if (countdownStarter < 0) {
	                	textLabel.setText("Application end");
	                    scheduler.shutdown();
	
	        			System.exit(1);
	                }
	                
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
	}

}
