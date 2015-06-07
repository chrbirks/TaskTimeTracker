package main.java.ttt.view;

import java.time.LocalDateTime;

import main.java.ttt.task.TaskManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTaskDialogController {
	@FXML
	private TextField idField;

	@FXML
	private TextField nameField;

	private Stage dialogStage;
	// private Task task;
	private boolean okClicked = false;

	TaskManager taskManager = TaskManager.getInstance();

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
		idField.requestFocus();
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
	private void handleAdd() {
		if (isInputValid()) {
			System.out.println("Adding task");
			taskManager.addTask(
					idField.getText(),
					nameField.getText(),
					LocalDateTime.now());

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (idField.getText() == null
				|| idField.getText().length() == 0) {
			errorMessage += "No valid ID string!\n";
		}
		if (nameField.getText() == null || nameField.getText().length() == 0) {
			errorMessage += "No valid name!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// TODO: better error
			System.out.println("ERROR: invalid input");
			return false;
		}
	}

}
