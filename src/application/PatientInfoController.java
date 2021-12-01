package application;

import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class PatientInfoController implements Initializable{

	private static TreeItem<MedicalRecordObject> medical_record;

	@FXML
    private Pane infoPane;

    @FXML
    private ImageView userImage;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> genderSelection;
    
    @FXML
    private ComboBox<String> insuranceSelection;

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField surnameLabel;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private Label ageLabel;
    
    @FXML
    private Label doctorLabel;
    
    @FXML
    private TextField telephoneField;
    
    @FXML
    private TextField emailField;

    @FXML
    private ImageView editImageButton;

    @FXML
    private JFXTreeTableView<MedicalRecordObject> recordsTreeView;
    
    @FXML
    private final ObservableList<MedicalRecordObject> records_objects = FXCollections.observableArrayList();

    @FXML
    private MenuButton sortByButton;

    @FXML
    private Spinner<Integer> weightSpinner;

    @FXML
    private Spinner<Integer> heightSpinner;

	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<String> gender = FXCollections.observableArrayList( "Male","Female");
		ObservableList<String> insurance_list = FXCollections.observableArrayList( "Anthem","Centene","UnitedHealth","Humana","HCSC","DKV","Sanitas","Maphre","AXA","Asisa","Adeslas","Caser","Allianz","Aegon","Other");
		heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(150, 250));
		weightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 200));
		genderSelection.setItems(gender);
		insuranceSelection.setItems(insurance_list);
		
		/*
		
		// ---------> Tree List View <--------
		
		JFXTreeTableColumn<MedicalRecordObject, String> reference_column = new JFXTreeTableColumn<>("Reference number");
		reference_column.setPrefWidth(120);
		reference_column.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
						return param.getValue().getValue().referenceNumber;
					}
				});
		reference_column.setResizable(false);
		
		JFXTreeTableColumn<MedicalRecordObject, String> ref_date = new JFXTreeTableColumn<>("Test date");
		ref_date.setPrefWidth(120);
		ref_date.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
						return param.getValue().getValue().recordDate;
					}
				});
		ref_date.setResizable(false);
		
		JFXTreeTableColumn<MedicalRecordObject, String> bitalino_column = new JFXTreeTableColumn<>("Includes Bitalino test");
		bitalino_column.setPrefWidth(100);
		bitalino_column.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
						return param.getValue().getValue().hasBitalinoTest;
					}
				});
		bitalino_column.setResizable(false);
		
		
		List<MedicalRecord> records_list // = manager_object.List_all_medicalrecords();
				
			for(MedicalRecord medical_record: records_list) {
				records_objects.add(new MedicalRecordObject(medical_record.getReferenceNumber().toString(), medical_record.getRecordDate().toString(), medical_record.getBitalinoTestIncluded().toString()));//here goes the getters for each attribute
			}
			
			final TreeItem<MedicalRecordObject> root_records = new RecursiveTreeItem<MedicalRecordObject>(records_objects, RecursiveTreeObject::getChildren);
			recordsTreeView.getColumns().setAll(reference_column, ref_date, bitalino_column);
			recordsTreeView.setRoot(root_records);
			recordsTreeView.setShowRoot(false);
				
				
		*/
		
		saveButton.setOnMouseClicked((MouseEvent event) -> {
			LaunchClientApp.instruction = ("update_patient," + logInController.user_id);
		});
		
	}
	
	
}

class MedicalRecordObject extends RecursiveTreeObject<MedicalRecordObject> {
	
	StringProperty referenceNumber;
	StringProperty recordDate;
	StringProperty hasBitalinoTest;
	
    public MedicalRecordObject(String referenceNumber, String recordDate, String hasBitalinoTest) {
    	this.referenceNumber = new SimpleStringProperty(referenceNumber);
    	this.recordDate = new SimpleStringProperty(recordDate);
    	this.hasBitalinoTest = new SimpleStringProperty(hasBitalinoTest);
    }
    
}
