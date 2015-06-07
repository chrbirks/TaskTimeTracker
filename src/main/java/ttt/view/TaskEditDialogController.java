package main.java.ttt.view;

import main.java.ttt.task.Task;
import main.java.ttt.task.TaskManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskEditDialogController {
	@FXML
	private TextField idField;
	
	@FXML
	private TextField nameField;
	
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
        idField.requestFocus();
    }
    
    /**
     * Sets the task to be edited in the dialog.
     * 
     * @param task
     */
    public void setTask(Task task) {
    	this.task = task;
    	
    	idField.setText(task.getTaskId());
    	nameField.setText(task.getTaskName());
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
    		taskManager.editTask(
    				task.getTaskId(), 
    				task.getTaskName(), 
    			 	idField.getText(), 
    				nameField.getText());
    		
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

        if (idField.getText() == null || idField.getText().length() == 0) {
            errorMessage += "No valid number!"; 
        }
        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid name!"; 
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
