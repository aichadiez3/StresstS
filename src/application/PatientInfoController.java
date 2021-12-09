package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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

	public static Integer insurance_id;
	public static Integer patient_id;

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
    private Label doctorLabel;
    
    @FXML
    private TextField telephoneField;
    
    @FXML
    private ImageView editImageButton;

    @FXML
    private TreeTableView<MedicalRecordObject> recordsTreeView;
    
    @FXML
    private ObservableList<MedicalRecordObject> records_objects;

    @FXML
    private Spinner<Integer> weightSpinner;

    @FXML
    private Spinner<Integer> heightSpinner;

	
	@SuppressWarnings("unchecked")
	public void initialize(URL arg0, ResourceBundle arg1) {

		ObservableList<String> gender = FXCollections.observableArrayList( "Male","Female");
		heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(150, 250));
		weightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 200));
		genderSelection.setItems(gender);
		
		
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
		ecg_column.setPrefWidth(300);
		ecg_column.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
				return param.getValue().getValue().ecgURL;
			}
		});
		ecg_column.getStyleClass().add("tree-table-column");
		ecg_column.setResizable(false);
		
		TreeTableColumn<MedicalRecordObject, String> eda_column = new TreeTableColumn<>("EDA");
		eda_column.setPrefWidth(300);
		eda_column.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<MedicalRecordObject, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<MedicalRecordObject, String> param) {
				return param.getValue().getValue().edaURL;
			}
		});
		eda_column.getStyleClass().add("tree-table-column");
		eda_column.setResizable(false);
		
		
			list_all_medical_records();
			
			TreeItem<MedicalRecordObject> root = new RecursiveTreeItem<MedicalRecordObject>(records_objects, RecursiveTreeObject::getChildren);
			recordsTreeView.getColumns().setAll(ref_date, reference_column, ecg_column, eda_column);
			recordsTreeView.setRoot(root);
			recordsTreeView.setShowRoot(false);

		
		display_insurances();
		
		// ----------------> Predetermined parameters <----------------
		/*
		try {
			
			LaunchClientApp.dataOutputStream.writeUTF("search_patient_by_user_id,"+logInController.user_id);
			patient_id = Integer.parseInt(LaunchClientApp.dataInputStream.readUTF());
			
			
			LaunchClientApp.dataOutputStream.writeUTF("search_insurance_by_patient_id, " + String.valueOf(patient_id));
			insurance_id = Integer.parseInt(LaunchClientApp.dataInputStream.readUTF());
			LaunchClientApp.instruction = "search_doctor_by_insurance," + String.valueOf(insurance_id);
			LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
			doctorLabel.setText("Doctor: " + LaunchClientApp.dataInputStream.readUTF());
		
			// SHOW NAME AND SURNAME OF THE PATIENT IN THE VISUAL TEXTFIELD OF THE APP, already not null parameters of patient 
		
			
			
			
		
		} catch (IOException read_info_error) {
			read_info_error.printStackTrace();
		}
		*/
		
		
		
		
		saveButton.setOnMouseClicked((MouseEvent event) -> {
			
			doctorLabel.setText("");
			
				try {
					LaunchClientApp.dataOutputStream.writeUTF("search_insurance_by_name," + String.valueOf(insuranceSelection.getValue()));
					insurance_id = Integer.parseInt(LaunchClientApp.dataInputStream.readUTF());
					System.out.println("insurance returned: "+insurance_id);
					
					LaunchClientApp.instruction = "search_doctor_by_insurance," + String.valueOf(insurance_id);
					LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
					String doctor_name = LaunchClientApp.dataInputStream.readUTF();
					doctorLabel.setText("Doctor: " + doctor_name);
					
					LaunchClientApp.instruction = "update_patient," + String.valueOf(logInController.user_id)
					+ "," + (birthDatePicker.getValue()).toString()
					+ "," + String.valueOf(heightSpinner.getValue())+","+ String.valueOf(weightSpinner.getValue())
					+","+ String.valueOf(genderSelection.getValue())+","+telephoneField.getText()+ ","+ String.valueOf(insurance_id);
					
					LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
					
				} catch (IOException update_patient_error) {
					update_patient_error.printStackTrace();
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
	
	
	void display_insurances() {
		try {
			LaunchClientApp.dataOutputStream.writeUTF("list_all_insurances");
			LaunchClientApp.feedback = LaunchClientApp.dataInputStream.readUTF();
			
			List<String> names = new ArrayList<String>();
			String[] insurances = LaunchClientApp.feedback.split(", ");
			
			for(int j=0; j <= insurances.length-1; j++) {
				if(j==0) {
					insurances[j] = insurances[j].replace("[", "");
				}
				if(j==insurances.length-1) {
					insurances[j]= insurances[j].replace("]", "");
				}
				names.add(insurances[j]);
			}
			
			ObservableList<String> insurance_list = FXCollections.observableArrayList(names);
			insuranceSelection.setItems(insurance_list);	
			
			} catch (IOException list_error) {
				list_error.printStackTrace();
			}
	}
	
	void list_all_medical_records() {
		String[] elements = null, parameter=null;
		try {
		LaunchClientApp.dataOutputStream.writeUTF("list_all_medical_records");
		
		LaunchClientApp.feedback = LaunchClientApp.dataInputStream.readUTF();
		System.out.println("The feedback is: " + LaunchClientApp.feedback);
		elements = LaunchClientApp.feedback.split(" ");
		
		List<MedicalRecordObject> list = new ArrayList<MedicalRecordObject>();
		
		for (int i = 0; i < elements.length; i++) {
			System.out.println("element " + i +" -> " + elements[i]);
			parameter = elements[i].split(",");		
			if(i==0) {
				parameter[0] = parameter[0].replace("[", "");
			}
			if(i==elements.length-1) {
				parameter[3]= parameter[3].replace("]", "");
			}
			MedicalRecordObject object = new MedicalRecordObject(parameter[0], parameter[1], parameter[2], parameter[3]);
			list.add(object);
		}
		
		records_objects = FXCollections.observableArrayList(list);
		
		} catch(IOException list_error) {
			list_error.printStackTrace();
		}
	}
	
}

class MedicalRecordObject extends RecursiveTreeObject<MedicalRecordObject> {
	
	StringProperty referenceNumber;
	StringProperty recordDate;
	StringProperty ecgURL;
	StringProperty edaURL;
	
    public MedicalRecordObject(String recordDate, String referenceNumber, String ecgURL, String edaURL) {
    	this.referenceNumber = new SimpleStringProperty(referenceNumber);
    	this.recordDate = new SimpleStringProperty(recordDate);
    	this.ecgURL = new SimpleStringProperty(ecgURL);
    	this.edaURL = new SimpleStringProperty(edaURL);
    }
    
}
