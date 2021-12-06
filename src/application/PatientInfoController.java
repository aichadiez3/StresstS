package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
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

	
	@SuppressWarnings("unchecked")
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
		reference_column.getStyleClass().add("tree-table-column");
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
		ref_date.getStyleClass().add("tree-table-column");
		ref_date.setResizable(false);
		
		TreeTableColumn<MedicalRecordObject, String> ecg_column = new TreeTableColumn<>("ECG");
		ecg_column.setPrefWidth(100);
		ecg_column.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
				return param.getValue().getValue().ecgURL;
			}
		});
		ecg_column.getStyleClass().add("tree-table-column");
		ecg_column.setResizable(false);
		
		TreeTableColumn<MedicalRecordObject, String> eda_column = new TreeTableColumn<>("EDA");
		eda_column.setPrefWidth(100);
		eda_column.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
				return param.getValue().getValue().edaURL;
			}
		});
		eda_column.getStyleClass().add("tree-table-column");
		eda_column.setResizable(false);
		
		
		try {
			LaunchClientApp.instruction = "list_all_medical_records";
			LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*	List<MedicalRecord> records_list = LaunchClientApp.feedback;
			for(MedicalRecord medical_record: records_list) {
				records_objects.add(new MedicalRecordObject(medical_record.getReferenceNumber().toString(), medical_record.getRecordDate().toString(), 
		//---> ESTO NO ES CORRECTO	//medical_record.getBitalinoTestId().toString()));
				
 // Get el bitalino_id no nos sirve. Lo necesitamos para llamar a servidor y obtener el ecg y el eda asociados a ese id
				
				LaunchClientApp.instruction = "search_associated_ecg, " + medical_record.getBitalinoTestId().toString();
				Integer ecg_id = Integer.parseInt(LaunchClientApp.feedback);
				
				LaunchClientApp.instruction = "search_associated_eda, " + medical_record.getBitalinoTestId().toString();
				Integer eda_id = Integer.parseInt(LaunchClientApp.feedback);
			}
		*/	
			
		/* 
		 --------------> Posible solución para List? 
		 * Bucle con una lita de feedback que separe por cada 4 parámetros y asigne un elemento de cada vez a la lista
		 * El tamaño debería ser de 4 parámetros (id, date, referenceNumber, bitalino_test_id)
		 */
		
	
			final TreeItem<MedicalRecordObject> root_records = new RecursiveTreeItem<MedicalRecordObject>(records_objects, RecursiveTreeObject::getChildren);
			recordsTreeView.getColumns().setAll(ref_date, reference_column, ecg_column, eda_column);
			recordsTreeView.setRoot(root_records);
			recordsTreeView.setShowRoot(false);
				
		
		
		
		saveButton.setOnMouseClicked((MouseEvent event) -> {
			
				/*
				LaunchClientApp.instruction = "update_patient," + logInController.user_id +","+nameLabel.getText()+","+surnameLabel.getText()
				+ "," + java.sql.Date.valueOf(birthDatePicker.getValue()).toString() + "," +ageLabel.getText()
				+ "," + String.valueOf(heightSpinner.getValue())+","+ String.valueOf(weightSpinner.getValue())
				+","+genderSelection.getValue()+","+telephoneField.getText() + ","+insuranceSelection.getValue();
				*/
				Integer insurance_id = null;
			
				try {
					LaunchClientApp.dataOutputStream.writeUTF("search_insurance_by_name," + insuranceSelection.getValue());
					insurance_id = Integer.parseInt(LaunchClientApp.dataInputStream.readUTF());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
						
				LaunchClientApp.instruction = "update_patient," + logInController.user_id +","+nameLabel.getText()+","+surnameLabel.getText()
				+ "," + (birthDatePicker.getValue()).toString()
				+ "," + String.valueOf(heightSpinner.getValue())+","+ String.valueOf(weightSpinner.getValue())
				+","+genderSelection.getValue()+","+telephoneField.getText() + ","+ insurance_id.toString();
				
				System.out.println(LaunchClientApp.instruction);
				
				try {
					LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			
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
		void set_age() {	// ESTA FUNCION DA ERROR
			
			if(!ageLabel.getText().equals("")) {
				LocalDate birth_date = birthDatePicker.getValue();
				Instant instant = Instant.from(birth_date.atStartOfDay(ZoneId.systemDefault()));
				//LocalDate date = birth_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				Period period = Period.between(birth_date, LocalDate.now());
			}
		}
	
	
	  @FXML
	    void sort_date_ascendent(ActionEvent event) {
		  	sortByButton.setText("Date - Newer to older");
		  	//LaunchClientApp.instruction="search_record_by_date_ascendent";
		  	//LaunchClientApp.feedback; //--> Doesn't return data (void type)
		  	
	    }

	    @FXML
	    void sort_date_descendent(ActionEvent event) {
	    	sortByButton.setText("Date - Older to newer");
	    	//LaunchClientApp.instruction="search_record_by_date_descendent";
		  	//LaunchClientApp.feedback; //--> Doesn't return data (void type)
	    }
	
	
}

class MedicalRecordObject extends RecursiveTreeObject<MedicalRecordObject> {
	
	StringProperty referenceNumber;
	StringProperty recordDate;
	StringProperty ecgURL;
	StringProperty edaURL;
	
    public MedicalRecordObject(String referenceNumber, String recordDate, String ecgURL, String edaURL) {
    	this.referenceNumber = new SimpleStringProperty(referenceNumber);
    	this.recordDate = new SimpleStringProperty(recordDate);
    	this.ecgURL = new SimpleStringProperty(ecgURL);
    	this.edaURL = new SimpleStringProperty(edaURL);
    }
    
}
