package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private MenuButton sortByButton;

    @FXML
    private Spinner<Integer> weightSpinner;

    @FXML
    private Spinner<Integer> heightSpinner;

	
	@SuppressWarnings("unchecked")
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
		try {
		LaunchClientApp.dataOutputStream.writeUTF("list_all_insurances");
		LaunchClientApp.feedback = LaunchClientApp.dataInputStream.readUTF();
		List<String> names = new ArrayList<String>();
		names = Arrays.asList(LaunchClientApp.feedback.split(", "));
		
		ObservableList<String> insurance_list = FXCollections.observableArrayList(names);
		insuranceSelection.setItems(insurance_list);	
		
		} catch (IOException list_error) {
			list_error.printStackTrace();
		}
		
		ObservableList<String> gender = FXCollections.observableArrayList( "Male","Female");
		heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(150, 250));
		weightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(30, 200));
		genderSelection.setItems(gender);
		
		/*
		 
		try {
			// SHOW NAME AND SURNAME OF THE PATIENT IN THE VISUAL TEXTFIELD OF THE APP, already not null parameters of patient 
			
			LaunchClientApp.dataOutputStream.writeUTF("search_patient_by_user_id,"+logInController.user_id);
			String patient_id = LaunchClientApp.dataInputStream.readUTF();
			//nameLabel.setText();
			
			LaunchClientApp.dataOutputStream.writeUTF("");
			
		
		} catch (IOException read_info_error) {
			read_info_error.printStackTrace();
		}
		
		*/
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
			LaunchClientApp.dataOutputStream.writeUTF("list_all_medical_records");
			LaunchClientApp.feedback = LaunchClientApp.dataInputStream.readUTF();
			
			String[] elements = LaunchClientApp.feedback.split(" ");
			
			List<MedicalRecordObject> list = new ArrayList<MedicalRecordObject>();
			
			for (int i = 0; i < elements.length; i++) {
				String[] parameter = elements[i].split(",");		
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
			
			
			TreeItem<MedicalRecordObject> root = new RecursiveTreeItem<MedicalRecordObject>(records_objects, RecursiveTreeObject::getChildren);
			recordsTreeView.getColumns().setAll(ref_date, reference_column, ecg_column, eda_column);
			recordsTreeView.setRoot(root);
			recordsTreeView.setShowRoot(false);

			
			
		} catch (IOException list_records_error) {
			list_records_error.printStackTrace();
		}
		
		
		saveButton.setOnMouseClicked((MouseEvent event) -> {
			
			
				try {
					LaunchClientApp.dataOutputStream.writeUTF("search_insurance_by_name," + String.valueOf(insuranceSelection.getValue()));
					Integer insurance_id = Integer.parseInt(LaunchClientApp.dataInputStream.readUTF());
					
					LaunchClientApp.instruction = "update_patient," + String.valueOf(logInController.user_id)
					+ "," + (birthDatePicker.getValue()).toString()
					+ "," + String.valueOf(heightSpinner.getValue())+","+ String.valueOf(weightSpinner.getValue())
					+","+ String.valueOf(genderSelection.getValue())+","+telephoneField.getText()+ ","+ String.valueOf(insurance_id);
					
					System.out.println(LaunchClientApp.instruction);
					
					LaunchClientApp.dataOutputStream.writeUTF(LaunchClientApp.instruction);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
	
    public MedicalRecordObject(String recordDate, String referenceNumber, String ecgURL, String edaURL) {
    	this.referenceNumber = new SimpleStringProperty(referenceNumber);
    	this.recordDate = new SimpleStringProperty(recordDate);
    	this.ecgURL = new SimpleStringProperty(ecgURL);
    	this.edaURL = new SimpleStringProperty(edaURL);
    }
    
}
