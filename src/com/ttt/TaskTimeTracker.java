package com.ttt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.ttt.task.Task;
import com.ttt.task.TaskManager;
import com.ttt.view.AddTaskDialogController;
import com.ttt.view.TaskEditDialogController;
import com.ttt.view.TaskOverviewController;
import com.ttt.view.TimeEditDialogController;

public class TaskTimeTracker extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	TaskManager taskManager = TaskManager.getInstance();

	private TaskOverviewController taskOverviewController;

	// ExecutorService
	private static final int N_THREADS_CORE_POOL = 1;
//	private static ScheduledExecutorService scheduler = Executors
//			.newScheduledThreadPool(N_THREADS_CORE_POOL);
	private static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(N_THREADS_CORE_POOL, new ThreadFactory(){
			      @Override
			      public Thread newThread(Runnable r){
			    	  return new Thread(r, "TimeKeeperScheduler");
				  }
			});
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("TaskTimeTracker");

		initRootLayout();

		showTaskOverview();
	}

	@Override
	public void stop() {
		System.out.println("Closing application");
		scheduler.shutdown();
		try {
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the task overview inside the root layout.
	 */
	public void showTaskOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource("view/TaskOverview.fxml"));
			AnchorPane taskOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(taskOverview);

			// Give the controller access to the main app.
			taskOverviewController = loader.getController();
			taskOverviewController.setTaskManager(taskManager);
			taskOverviewController.setScheduler(scheduler);
			taskOverviewController.setTaskTimeTracker(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Opens a dialog to add a task. If the user clicks OK, the changes are
	 * saved into a new Task object and true is returned.
	 * 
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showAddTaskDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource("view/AddTaskDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Task");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the task into the controller.
			AddTaskDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens a dialog to edit details for the specified task. If the user clicks
	 * OK, the changes are saved into the provided Task object and true is
	 * returned.
	 * 
	 * @param task
	 *            the Task object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showTaskEditDialog(Task task) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource("view/TaskEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Task");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the task into the controller.
			TaskEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTask(task);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * 
     */
	public boolean showTimeEditDialog(Task task) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource("view/TimeEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit time");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			TimeEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTime(task);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @param args are not used
	 */
	public static void main(String[] args) {

		TaskManager taskManager = TaskManager.getInstance();
		taskManager.addTask(123, "Jira", LocalDateTime.now());
		taskManager.addTask(456, "Confluence", LocalDateTime.now());
		taskManager.addTask(789, "Jenkins", LocalDateTime.now());
		taskManager.addTask(789, "Jenkins", LocalDateTime.now());

		launch(args);
	}

}
