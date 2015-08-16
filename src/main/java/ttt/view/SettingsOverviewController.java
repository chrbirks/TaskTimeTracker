/**
 * 
 */
package main.java.ttt.view;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.ttt.task.Settings;

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
		
		this.projectRelatedNameField.setText(Settings.getProjectRelatedName());
		
		this.otherProjectRelatedIdField.setText(Settings.getOtherProjectRelatedId());
		this.otherProjectRelatedNameField.setText(Settings.getOtherProjectRelatedName());
		
		this.otherNonProjectRelatedIdField.setText(Settings.getOtherNonProjectRelatedId());
		this.otherNonProjectRelatedTextField.setText(Settings.getOtherNonProjectRelatedName());
		
		this.logDirField.setText(Settings.getLogDir());
		this.automaticLoggingBox.setSelected(Settings.isAutomaticLogging());
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
		Settings.setProjectRelatedName(projectRelatedNameField.getText());
		
		Settings.setOtherProjectRelatedId(otherProjectRelatedIdField.getText());
		Settings.setOtherProjectRelatedName(otherProjectRelatedNameField.getText());

		Settings.setOtherNonProjectRelatedId(otherNonProjectRelatedIdField.getText());
		Settings.setOtherNonProjectRelatedName(otherNonProjectRelatedTextField.getText());
		
		Settings.setLogDir(logDirField.getText());
		Settings.setAutomaticLogging(automaticLoggingBox.isSelected());
		
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
