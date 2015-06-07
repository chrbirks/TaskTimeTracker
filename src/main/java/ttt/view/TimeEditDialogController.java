package main.java.ttt.view;

import main.java.ttt.task.Task;
import main.java.ttt.task.TaskManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TimeEditDialogController {

	@FXML
	private TextField minutesField;

	private Stage dialogStage;
	private Task task;
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
		minutesField.requestFocus();
	}

	/**
	 * Sets the time to be edited in the dialog.
	 * 
	 * @param task
	 */
	public void setTime(Task task) {
		this.task = task;

		// Get minutes from text field
		minutesField.setText(Long.toString(task.getElapsedMinutes()));
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
	private void handleOK() {
		if (isInputValid()) {
			taskManager.editTime(task.getTaskNumber(), task.getTaskName(),
					Long.parseLong(minutesField.getText()));

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
		String minutes = minutesField.getText();

		if (minutes == null) {
			errorMessage += "Not valid number: " + minutes;
		}

		if (Long.parseLong(minutes) < 0) {
			errorMessage += "Number is below 0: " + Long.parseLong(minutes);
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// TODO: better error
			System.out.println("ERROR: invalid input: " + errorMessage);
			return false;
		}
	}

}
