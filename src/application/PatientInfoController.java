package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
    private ImageView editImageButton;

    @FXML
    private TreeTableView<MedicalRecordObject> recordsTreeView;
    
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
		
		
		
		// ---------> Tree List View <--------
		
		TreeTableColumn<MedicalRecordObject, String> reference_column = new TreeTableColumn<>("Reference number");
		reference_column.setPrefWidth(160);
		reference_column.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
						return param.getValue().getValue().referenceNumber;
					}
				});
		reference_column.setResizable(false);
		
		TreeTableColumn<MedicalRecordObject, String> ref_date = new TreeTableColumn<>("Date");
		ref_date.setPrefWidth(135);
		ref_date.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
						return param.getValue().getValue().recordDate;
					}
				});
		ref_date.setResizable(false);
		
		TreeTableColumn<MedicalRecordObject, Object> ecg_column = new TreeTableColumn<>("ECG");
		ecg_column.setPrefWidth(100);
		//ecg_column.setCellValueFactory({});
		ecg_column.setResizable(false);
		
		TreeTableColumn<MedicalRecordObject, String> eda_column = new TreeTableColumn<>("EDA");
		eda_column.setPrefWidth(100);
		//eda_column.setCellValueFactory();
		eda_column.setResizable(false);
		
		/*
		
		// El server nos devuelve el id de un medical record
		//---> search medical record by id y lo devolvemos y asignamos a class MedicalRecordObject (ver clase debajo) que son todo string
		
		List<MedicalRecord> records_list // = manager_object.List_all_medicalrecords();
				
			for(MedicalRecord medical_record: records_list) {
				records_objects.add(new MedicalRecordObject(medical_record.getReferenceNumber().toString(), medical_record.getRecordDate().toString(), medical_record.getBitalinoTestIncluded().toString()));//here goes the getters for each attribute
			}
			
			final TreeItem<MedicalRecordObject> root_records = new RecursiveTreeItem<MedicalRecordObject>(records_objects, RecursiveTreeObject::getChildren);
			recordsTreeView.getColumns().setAll(reference_column, ref_date, ecg_column, eda_column);
			recordsTreeView.setRoot(root_records);
			recordsTreeView.setShowRoot(false);
				
		*/
		
		
		saveButton.setOnMouseClicked((MouseEvent event) -> {
			LaunchClientApp.instruction = ("update_patient," + logInController.user_id);
		});
		
	}
	
	
	@FXML
	private void change_image(MouseEvent event) throws IOException{
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.bmp", "*.png", "*.jpg", "*.gif")); // limit chooser options to image files
		File file = fileChooser.showOpenDialog(new Stage());
		if(file != null) {
			String imagepath = file.toURI().toURL().toString();
			Image image = new Image(imagepath);
			userImage.setImage(image);
			
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Information Dialog");
	        alert.setHeaderText("No File was selected. Please Select a File");
	        alert.showAndWait();
		}
		
	}
	
	
	  @FXML
	    void sort_date_ascendent(ActionEvent event) {
		  	sortByButton.setText("Date - Newer to older");
	    }

	    @FXML
	    void sort_date_descendent(ActionEvent event) {
	    	sortByButton.setText("Date - Older to newer");
	    }
	
	
}

class MedicalRecordObject extends RecursiveTreeObject<MedicalRecordObject> {
	
	StringProperty referenceNumber;
	StringProperty recordDate;
	StringProperty empty;
	
    public MedicalRecordObject(String referenceNumber, String recordDate, String empty) {
    	this.referenceNumber = new SimpleStringProperty(referenceNumber);
    	this.recordDate = new SimpleStringProperty(recordDate);
    	this.empty = new SimpleStringProperty(empty);
    }
    
}
