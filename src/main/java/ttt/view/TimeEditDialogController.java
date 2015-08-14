package main.java.ttt.view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.ttt.task.Task;
import main.java.ttt.task.TaskManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TimeEditDialogController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeEditDialogController.class);

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
			String minutes = minutesField.getText();
			// If '+' first, add to time
			if (minutes.substring(0, 1).equals("+")) {
				long newTime = task.getElapsedMinutes() + Long.parseLong(minutes.substring(1)); 
				taskManager.editTime(
						task.getTaskId(),
						task.getTaskName(),
						newTime);
			// If '-' first, substract time
			} else if (minutes.substring(0, 1).equals("-")) {
				long newTime = task.getElapsedMinutes() - Long.parseLong(minutes.substring(1));
				taskManager.editTime(
						task.getTaskId(),
						task.getTaskName(),
						newTime);
			// Else set exact time
			} else {
				taskManager.editTime(task.getTaskId(), task.getTaskName(),
						Long.parseLong(minutes));
			}
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

		try {
			if (minutes == null) {
				errorMessage += "Not valid number: " + minutes;
			}

			// if (Long.parseLong(minutes) < 0) {
			// errorMessage += "Number is below 0: " + Long.parseLong(minutes);
			// }

			if (minutes.substring(0, 1) == "+"
					|| minutes.substring(0, 1) == "-") {
				if (isLong(minutes.substring(1))) {
					return true;
				}
			}

			if (errorMessage.length() == 0) {
				return true;
			} else {
				// TODO: better error
				LOGGER.error("Invalid input: " + errorMessage);
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO: better error
			LOGGER.error("Not a number");
			return false;
		}
	}

	private static boolean isLong(String s) {
		Scanner sc = new Scanner(s.trim());

		if (!sc.hasNextLong()) {
			sc.close();
			return false;
		}
		sc.nextLong();
		boolean result = !sc.hasNext();
		sc.close();
		return result;
	}

}
