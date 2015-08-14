package main.java.ttt.task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.ttt.TaskTimeTracker;
import main.java.ttt.auxil.DoubleRound;
import main.java.ttt.model.TaskSetWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);

	private static TaskManager instance;

	private ObservableList<Task> taskSet = FXCollections.observableArrayList();
	
	private TaskManager() {
	}
	
	public static synchronized TaskManager getInstance() {
		if (instance == null)
			instance = new TaskManager();
		return instance;
	}
	
	public void addTask(Task task) {
		addTask(task.getTaskId(),
				task.getTaskName(),
				task.getTaskTime());
	}
	
	public void addTask(String taskId, String taskName,LocalDateTime time) {
		Task newTask = new Task(taskId, taskName, time);
		try {
			LOGGER.debug("New task: " + newTask.toString());
			LOGGER.debug("Contains task: " + taskSet.contains(newTask));
			if (!taskSet.contains(newTask)) {
				LOGGER.debug("Adding task");
				taskSet.add(newTask);
			} else {
				// TODO: Throw AlreadyExistesException
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public Task getTask(String taskId, String taskName) {
		Task refTask = new Task(taskId, taskName, LocalDateTime.now());
		LOGGER.debug("Searching for Task: " + refTask.toString());
		
		Iterator<Task> i = taskSet.iterator();
		LOGGER.debug("hasNext: " + i.hasNext());
		while (i.hasNext()) {
			Task task = i.next();
			if (task.equals(refTask)) {
				LOGGER.debug("Getting task: " + task.toString());
				return task;
			}
		}
		LOGGER.debug("Task not found");
		return null;
	}
	
	public boolean editTask(String taskId, String taskName, String newId, String newName) {
		Task refTask = new Task(taskId, taskName, LocalDateTime.now());
		LOGGER.debug("Editing task: " + refTask);
		
		Iterator<Task> i = taskSet.iterator();
		LOGGER.debug("hasNext: " + i.hasNext());
		while (i.hasNext()) {
			Task task = i.next();
			if (task.equals(refTask)) {
				LOGGER.debug("Editing values for task: " + task.toString());
				task.setTaskId(newId);
				task.setTaskName(newName);
				LOGGER.debug("Task is now: " + task.toString());
				return true;
			}
		}
		LOGGER.debug("Task not found");
		return false;
	}
	
	public void resetAllTimes() {
		LOGGER.debug("Resetting times");
		Iterator<Task> i = taskSet.iterator();
		while (i.hasNext()) {
			Task task = i.next();
			task.setElapsedHours(0);
			task.setElapsedMinutes(0);
		}
	}
	
	public boolean editTime(String taskId, String taskName, long minutes) {
		Task refTask = new Task(taskId, taskName, LocalDateTime.now());
		LOGGER.debug("Editing task: " + refTask);
		
		Iterator<Task> i = taskSet.iterator();
		LOGGER.debug("hasNext: " + i.hasNext());
		while (i.hasNext()) {
			Task task = i.next();
			if (task.equals(refTask)) {
				LOGGER.debug("Editing time for task: " + task.toString());
				task.setElapsedMinutes(minutes);
				task.setElapsedHours(DoubleRound.doubleRound(minutes/60.0, 2));
				LOGGER.debug("Task is now: " + task.toString());
				return true;
			}
		}
		LOGGER.debug("Task not found");
		return false;
	}
	
	public boolean removeTask(String taskId, String taskName) {
		return taskSet.remove(this.getTask(taskId, taskName));
	}
	
	public void clearTaskSet() {
		taskSet.clear();
	}
	
	public ObservableList<Task> getTaskSet() {
		return taskSet;
	}
	
	public int getTaskSetSize() {
		return taskSet.size();
	}
	
	@Override
	public String toString() {
		return "TaskManager [taskSet=" + taskSet + "]";
	}
	
	@Override
	public TaskManager clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	/**
	 * Returns the task file preference, i.e. the file that was last opened.
	 * The preference is read from the OS specific registry. If no such
	 * preference can be found, null is returned.
	 * 
	 * @return
	 */
	public File getTaskFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(TaskTimeTracker.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	/**
	 * Sets the file path of the currently loaded file. The path is persisted in
	 * the OS specific registry.
	 * 
	 * @param file the file or null to remove the path
	 */
	public void setTaskFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(TaskTimeTracker.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
//	        primaryStage.setTitle("TaskApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
//	        primaryStage.setTitle("TaskApp");
	    }
	}
	
	/**
	 * Loads task data from the specified file. The current task data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadTaskDataFromFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(TaskSetWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        TaskSetWrapper wrapper = (TaskSetWrapper) um.unmarshal(file);

	        taskSet.clear();
	        taskSet.addAll(wrapper.getTasks());

	        // Save the file path to the registry.
	        setTaskFilePath(file);

	    } catch (Exception e) { // catches ANY exception
//	        Alert alert = new Alert(AlertType.ERROR);
//	        alert.setTitle("Error");
//	        alert.setHeaderText("Could not load data");
//	        alert.setContentText("Could not load data from file:\n" + file.getPath());
//
//	        alert.showAndWait();
	    	LOGGER.error("Could not load data from file: " + file.getPath(), e);
	    }
	}
	
	/**
	 * Saves the current task data to the specified file.
	 * 
	 * @param file
	 */
	public void saveTaskDataToFile(File file) {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(TaskSetWrapper.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Wrapping our task data.
	        TaskSetWrapper wrapper = new TaskSetWrapper();
	        wrapper.setTasks(taskSet);

	        // Marshalling and saving XML to the file.
	        m.marshal(wrapper, file);

	        // Save the file path to the registry.
	        setTaskFilePath(file);
	    } catch (Exception e) { // catches ANY exception
//	        Alert alert = new Alert(AlertType.ERROR);
//	        alert.setTitle("Error");
//	        alert.setHeaderText("Could not save data");
//	        alert.setContentText("Could not save data to file:\n" + file.getPath());
//
//	        alert.showAndWait();
	    	LOGGER.error("Could not save data to file: " + file.getPath(), e);
	    }
	}
	
	/**
	 * Get task data from the specified file. Will be returned as list
	 * 
	 * @param file
	 */
	public List<Task> getTaskDataFromFile(File file) {
		TaskSetWrapper wrapper = null;
		
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(TaskSetWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        wrapper = (TaskSetWrapper) um.unmarshal(file);
	        
	    } catch (Exception e) { // catches ANY exception
//	        Alert alert = new Alert(AlertType.ERROR);
//	        alert.setTitle("Error");
//	        alert.setHeaderText("Could not load data");
//	        alert.setContentText("Could not load data from file:\n" + file.getPath());
//
//	        alert.showAndWait();
	    	LOGGER.error("Could not load data from file: " + file.getPath(), e);
	    }
	    return wrapper.getTasks();
	}
	
}
