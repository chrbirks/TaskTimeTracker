package main.java.ttt.view;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import main.java.ttt.TaskTimeTracker;
import main.java.ttt.task.Task;
import main.java.ttt.task.TaskManager;
import main.java.ttt.task.TimeKeeper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TaskOverviewController {

	@FXML
	private TableView<Task> taskTable;

	@FXML
	private TableColumn<Task, Integer> numberColumn;

	@FXML
	private TableColumn<Task, String> nameColumn;

	@FXML
	private TableColumn<Task, Long> minutesColumn;

	@FXML
	private TableColumn<Task, Double> hoursColumn;

	// Reference to main application
	private TaskTimeTracker taskTimeTracker;
	private TaskManager taskManager;

	// ExecutorService
	private static final int POLL_INTERVAL = 1;
	private ScheduledExecutorService scheduler;
	private static ScheduledFuture<?> scheduledFuture = null;

	private static Runnable timeKeeper = null;

	// Constructor
	public TaskOverviewController() {

	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		// Initialize the task table
		numberColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getTaskNumberProperty().asObject());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getTaskNameProperty());
		minutesColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getElapsedMinutesProperty().asObject());
		hoursColumn.setCellValueFactory(cellData -> cellData.getValue()
				.getElapsedHoursProperty().asObject());

		// Listen for selection changes and update time when task is changed
		taskTable
				.getSelectionModel()
				.selectedItemProperty()
				.addListener(
						(observable, oldTask, newTask) -> handleTaskChange(
								oldTask, newTask));

	}

	/**
	 * Is called by the main application to give a reference back to
	 * TaskManager.
	 * 
	 * @param taskManager
	 */
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
		// Add observable list data to the table
		taskTable.setItems(taskManager.getTaskSet());
	}

	/**
	 * Is called by the main application to give a reference back to the
	 * ExecutorService thread pool.
	 * 
	 * @param scheduler
	 */
	public void setScheduler(ScheduledExecutorService scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param taskTimeTracker
	 */
	public void setTaskTimeTracker(TaskTimeTracker taskTimeTracker) {
		this.taskTimeTracker = taskTimeTracker;
	}

	/**
	 * Starts a thread that keeps track on how long the task has been running
	 * 
	 * @param previousTask
	 * @param activeTask
	 */
	public void handleTaskChange(Task oldTask, Task activeTask) {
		System.out.println("timeKeeper startet");

		// Stop currently running timeKeeper thread
		if (scheduledFuture != null) {
			System.out.println("Cancelling thread");
			scheduledFuture.cancel(true);
		}

		if (activeTask != null) {
			System.out.println("New task is: " + activeTask.toString());

			taskTable.getColumns().get(0).setVisible(false);
			taskTable.getColumns().get(0).setVisible(true);

			timeKeeper = new TimeKeeper(activeTask, this);

			scheduledFuture = scheduler.scheduleAtFixedRate(timeKeeper, 0,
					POLL_INTERVAL, SECONDS);

		}
		// TEST:
		if (activeTask != null) {
			System.out.println("activeTask: " + activeTask.toString());
		}

	}

	/**
	 * Handle add task
	 */
	@FXML
	private void handleAddTask() {
		taskTimeTracker.showAddTaskDialog();
	}

	/**
	 * Handle edit task
	 */
	@FXML
	private void handleEditTask() {
		Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			System.out.println("Editing task: " + selectedTask.toString());
			boolean okClicked = taskTimeTracker
					.showTaskEditDialog(selectedTask);
			if (okClicked) {
				// TODO
				// showTaskDetails(selectedTask);
				// timeKeeper(oldTask, newTAsk);
			} else {
				// Nothing selected
				// TODO: better error
				System.out.println("ERROR: no task selected");
			}
		}
	}

	/**
	 * Handle edit time
	 */
	@FXML
	private void handleEditTime() {
		Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
		if (selectedTask != null) {
			System.out.println("Editing time: " + selectedTask.toString());
			boolean okClicked = taskTimeTracker
					.showTimeEditDialog(selectedTask);
			if (okClicked) {
				handleTaskChange(selectedTask, selectedTask);
			} else {
				// Nothing selected
				// TODO: better error
				System.out.println("ERROR: no task selected");
			}
		}
	}
	
	/**
	 * Handle stop timer
	 */
	@FXML
	private void handleStopTimer() {
		taskTable.getSelectionModel().clearSelection();
	}
	
	/**
	 * Handle statistics button
	 */
	@FXML
	private void handleStatistics() {
		taskTimeTracker.showStatistics();
	}
	
	/**
	 * Handle reset all times button
	 */
	@FXML
	private void handleResetTimes() {
		taskTable.getSelectionModel().clearSelection();
		taskManager.resetAllTimes();
	}

	/**
	 * Handle delete button
	 */
	@FXML
	private void handleRemoveTask() {

		int selectedIndex = taskTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {

			// Remove from taskSet
			System.out.println("Removing task from taskSet: "
					+ taskTable.getSelectionModel().selectedItemProperty()
							.getValue().getTaskNumber()
					+ " , "
					+ taskTable.getSelectionModel().selectedItemProperty()
							.getValue().getTaskName());
			taskManager.removeTask(taskTable.getSelectionModel()
					.selectedItemProperty().getValue().getTaskNumber(),
					taskTable.getSelectionModel().selectedItemProperty()
							.getValue().getTaskName());

		} else {
			// TODO: better error
			System.out.println("ERROR: no task selected");
		}

	}
}
