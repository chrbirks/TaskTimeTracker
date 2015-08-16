/**
 * 
 */
package main.java.ttt.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.ttt.TaskTimeTracker;
import main.java.ttt.task.SettingsManager;

/**
 * @author chrbirks
 *
 */
public class SettingsOverviewController {
	
	@FXML
	private TextField projectRelatedNameField;
	@FXML
	private TextField otherProjectRelatedIdField;
	@FXML
	private TextField otherProjectRelatedNameField;
	@FXML
	private TextField otherNonProjectRelatedIdField;
	@FXML
	private TextField otherNonProjectRelatedTextField;
	@FXML
	private TextField logDirField;
	@FXML
	private CheckBox automaticLoggingBox;
	
	private Stage dialogStage;
	
	private boolean okClicked = false;
	
	private SettingsManager settingsManager = SettingsManager.getInstance();

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
		
		settingsManager.getSettingsFromFile();
		
		this.projectRelatedNameField.setText(SettingsManager.getProjectRelatedName());
		
		this.otherProjectRelatedIdField.setText(SettingsManager.getOtherProjectRelatedId());
		this.otherProjectRelatedNameField.setText(SettingsManager.getOtherProjectRelatedName());
		
		this.otherNonProjectRelatedIdField.setText(SettingsManager.getOtherNonProjectRelatedId());
		this.otherNonProjectRelatedTextField.setText(SettingsManager.getOtherNonProjectRelatedName());
		
		this.logDirField.setText(SettingsManager.getLogDir());
		this.automaticLoggingBox.setSelected(SettingsManager.isAutomaticLogging());
	}
	
	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
//		if (isInputValid()) {
//			LOGGER.debug("Adding task");
//			taskManager.addTask(
//					idField.getText(),
//					nameField.getText(),
//					LocalDateTime.now());
//
//			okClicked = true;
//			dialogStage.close();
//		}
		
		/*
		 * Read settings from GUI
		 */
		SettingsManager.setProjectRelatedName(projectRelatedNameField.getText());
		
		SettingsManager.setOtherProjectRelatedId(otherProjectRelatedIdField.getText());
		SettingsManager.setOtherProjectRelatedName(otherProjectRelatedNameField.getText());

		SettingsManager.setOtherNonProjectRelatedId(otherNonProjectRelatedIdField.getText());
		SettingsManager.setOtherNonProjectRelatedName(otherNonProjectRelatedTextField.getText());
		
		SettingsManager.setLogDir(logDirField.getText());
		SettingsManager.setAutomaticLogging(automaticLoggingBox.isSelected());
		
		settingsManager.saveSettingsToFile();
		
		okClicked = true;
		dialogStage.close();
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
}
