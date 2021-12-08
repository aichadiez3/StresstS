package application;

import java.io.IOException;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class AnxietyTestController implements Initializable {

	private Boolean[] positive;
	
	@FXML
    private Pane testPane;

    @FXML
    private CheckBox yes_sport;

    @FXML
    private CheckBox yes_socialize;

    @FXML
    private CheckBox yes_read;

    @FXML
    private CheckBox yes_sleep;

    @FXML
    private CheckBox yes_meditation;

    @FXML
    private CheckBox yes_hobbies;

    @FXML
    private CheckBox no_stress;

    @FXML
    private CheckBox no_racing_thoughts;

    @FXML
    private CheckBox no_overthinker;

    @FXML
    private CheckBox racing_thoughts;

    @FXML
    private CheckBox panic_attacks;

    @FXML
    private CheckBox sleeping_troubles;

    @FXML
    private CheckBox appetite_alterations;

    @FXML
    private CheckBox dissociation;

    @FXML
    private CheckBox heavy_fast_breathing;

    @FXML
    private CheckBox shaking;

    @FXML
    private CheckBox dizziness;

    @FXML
    private CheckBox fainting;

    @FXML
    private CheckBox no_sport;

    @FXML
    private CheckBox no_socialize;

    @FXML
    private CheckBox no_read;

    @FXML
    private CheckBox no_sleep;

    @FXML
    private CheckBox no_meditation;

    @FXML
    private CheckBox no_hobbies;

    @FXML
    private CheckBox yes_stress;

    @FXML
    private CheckBox yes_racing_thoughts;

    @FXML
    private CheckBox yes_overthinker;

    @FXML
    private Button submitButton;
  
    private LinkedList<String> positive_things = new LinkedList<String>();
    private LinkedList<String> negative_things = new LinkedList<String>();
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		positive_things.add("I sport weekly");
		positive_things.add("I usually socialize everyday (family, friends...)");
		positive_things.add("I often have time to read or to watch movies");
		positive_things.add("I sleep between 7-8 hours everyday");
		positive_things.add("I sometimes do meditation before or after sleep");
		positive_things.add("I can make some time during the week for my hobbies");
		
		negative_things.add("I sometimes have racing thoughts during the day");
		negative_things.add("I often panic when I'm under a lot of stress");
		negative_things.add("I consider that I tend to overthink too much");
		negative_things.add("Racing thoughts");
		negative_things.add("Panic attacks");
		negative_things.add("Sleeping troubles");
		negative_things.add("Appetite alterations");
		negative_things.add("Dissociation");
		negative_things.add("Heavy and fast breathing");
		negative_things.add("Shaking");
		negative_things.add("Dizziness");
		negative_things.add("Fainting");
		negative_things.add("I don't practice any sport");
		negative_things.add("I usually prefer to be on my own");
		negative_things.add("I never read or watch movies");
		negative_things.add("I sleep less than 7-8 hours everyday");
		negative_things.add("I never meditate");
		negative_things.add("I never make some time during the week for my hobbies");
		negative_things.add("I never have racing thoughts");
		negative_things.add("I usually feel confortable under stress");
		negative_things.add("I never have racing thoughts");
		
		LinkedList<String> positive_res = new LinkedList<String>();
		LinkedList<String> negative_res = new LinkedList<String>();
		
		submitButton.setOnMouseClicked((MouseEvent event) -> {
			try {
				
			    Boolean[] positive = {yes_sport.isSelected(), yes_socialize.isSelected(), yes_read.isSelected(), yes_sleep.isSelected(), yes_meditation.isSelected(), yes_hobbies.isSelected()};
			    Boolean[] negative = {yes_racing_thoughts.isSelected(), yes_stress.isSelected(), yes_overthinker.isSelected(), racing_thoughts.isSelected(), panic_attacks.isSelected(), sleeping_troubles.isSelected(),
			    		appetite_alterations.isSelected(), dissociation.isSelected(), heavy_fast_breathing.isSelected(), shaking.isSelected(), dizziness.isSelected(), fainting.isSelected(), no_sport.isSelected(), no_socialize.isSelected(), no_read.isSelected(),
			    		no_sleep.isSelected(), no_meditation.isSelected(), no_hobbies.isSelected(), no_racing_thoughts.isSelected(), no_stress.isSelected(), no_overthinker.isSelected()};

			    
			    for(int i = 0; i<positive.length; i++) {
			    	if(positive[i]==true) {
			    		positive_res.add(positive_things.get(i));
			    	}
			    }
			    
			    for(int i = 0; i<negative.length; i++) {
			    	if(negative[i]==true) {
			    		negative_res.add(negative_things.get(i));
			    	}
			    }

			    LaunchClientApp.dataOutputStream.writeUTF("new_psycho," + positive_res + negative_res + "," + String.valueOf(PatientHealthController.record_id));
				
				
			} catch (IOException home_error) {
				home_error.printStackTrace();
			}
			
			
			try {
				Pane home_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
				testPane.getChildren().removeAll();
				testPane.getChildren().setAll(home_pane_fxml);
			} catch (IOException open_home_error) {
				open_home_error.printStackTrace();
			}
			
		});
		
	}

}


