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

	private PatientMenuController menu_controller;
	
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
    private CheckBox yes_raising_thoughts;

    @FXML
    private CheckBox yes_stress;

    @FXML
    private CheckBox yes_overthinker;

    @FXML
    private CheckBox raising_thoughts;

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
    private CheckBox no_raising_thoughts;

    @FXML
    private CheckBox no_stress;

    @FXML
    private CheckBox no_overthinker;

    @FXML
    private Button submitButton;
  
    private LinkedList<Boolean> positive_res;
    private LinkedList<Boolean> negative_res;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		submitButton.setOnMouseClicked((MouseEvent event) -> {
			try {
				
				/*
				 * SAVE HERE ALL DATA FROM TESTS FOR FURTHER EVALUATION AND ASSOCIATE THE SYMPTOMS TO THE PATIEND MEDICAL RECORD
				 */
				
			    Boolean[] positive = {yes_sport.isSelected(), yes_socialize.isSelected(), yes_read.isSelected(), yes_sleep.isSelected(), yes_meditation.isSelected(), yes_hobbies.isSelected()};
			    Boolean[] negative = {yes_raising_thoughts.isSelected(), yes_stress.isSelected(), yes_overthinker.isSelected(), raising_thoughts.isSelected(), panic_attacks.isSelected(), sleeping_troubles.isSelected(),
			    		appetite_alterations.isSelected(), dissociation.isSelected(), heavy_fast_breathing.isSelected(), shaking.isSelected(), dizziness.isSelected(), fainting.isSelected(), no_sport.isSelected(), no_socialize.isSelected(), no_read.isSelected(),
			    		no_sleep.isSelected(), no_meditation.isSelected(), no_hobbies.isSelected(), no_raising_thoughts.isSelected(), no_stress.isSelected(), no_overthinker.isSelected()};

			    positive_res = (LinkedList<Boolean>) Arrays.asList(positive);
			    negative_res = (LinkedList<Boolean>) Arrays.asList(negative);
			    
				//LO MISMO, NO SE HACER LOS DATOS DEL LinkedList<Boolean> A String Y ADEMAS AQUI NO ESTAMOS HACIENDO NADA AUN
				LaunchClientApp.instruction = ("new_psycho," + positive_res.toString() + "," + negative_res.toString() + "," + PatientHealthController.record_id);
				
				//this.yes_sport.getId()
				
				Pane home_pane_fxml = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
				testPane.getChildren().removeAll();
				testPane.getChildren().setAll(home_pane_fxml);
				
			} catch (IOException home_error) {
				home_error.printStackTrace();
			}
		});
		
	}

}


