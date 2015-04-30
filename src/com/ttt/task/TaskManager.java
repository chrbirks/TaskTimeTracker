package com.ttt.task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.ttt.TaskTimeTracker;
import com.ttt.aux.DoubleRound;
import com.ttt.model.TaskSetWrapper;

public class TaskManager {

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
		addTask(task.getTaskNumber(),
				task.getTaskName(),
				task.getTaskTime());
	}
	
	public void addTask(int taskNumber, String taskName,LocalDateTime time) {
		Task newTask = new Task(taskNumber, taskName, time);
		try {
			System.out.println("New task: " + newTask.toString());
			System.out.println("Contains task: " + taskSet.contains(newTask));
			if (!taskSet.contains(newTask)) {
				System.out.println("Adding task");
				taskSet.add(newTask);
			} else {
				// TODO: Throw AlreadyExistesException
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public Task getTask(int taskNumber, String taskName) {
		Task refTask = new Task(taskNumber, taskName, LocalDateTime.now());
		System.out.println("Searching for Task: " + refTask.toString());
		
		Iterator<Task> i = taskSet.iterator();
		System.out.println("hasNext: " + i.hasNext());
		while (i.hasNext()) {
			Task task = i.next();
			if (task.equals(refTask)) {
				System.out.println("Getting task: " + task.toString());
				return task;
			}
		}
		System.out.println("Task not found");
		return null;
	}
	
	public boolean editTask(int taskNumber, String taskName, int newNumber, String newName) {
		Task refTask = new Task(taskNumber, taskName, LocalDateTime.now());
		System.out.println("Editing task: " + refTask);
		
		Iterator<Task> i = taskSet.iterator();
		System.out.println("hasNext: " + i.hasNext());
		while (i.hasNext()) {
			Task task = i.next();
			if (task.equals(refTask)) {
				System.out.println("Editing values for task: " + task.toString());
				task.setTaskNumber(newNumber);
				task.setTaskName(newName);
				System.out.println("Task is now: " + task.toString());
				return true;
			}
		}
		System.out.println("Task not found");
		return false;
	}
	
	public void resetAllTimes() {
		System.out.println("Resetting times");
		Iterator<Task> i = taskSet.iterator();
		while (i.hasNext()) {
			Task task = i.next();
			task.setElapsedHours(0);
			task.setElapsedMinutes(0);
		}
	}
	
	public boolean editTime(int taskNumber, String taskName, long minutes) {
		Task refTask = new Task(taskNumber, taskName, LocalDateTime.now());
		System.out.println("Editing task: " + refTask);
		
		Iterator<Task> i = taskSet.iterator();
		System.out.println("hasNext: " + i.hasNext());
		while (i.hasNext()) {
			Task task = i.next();
			if (task.equals(refTask)) {
				System.out.println("Editing time for task: " + task.toString());
				task.setElapsedMinutes(minutes);
				task.setElapsedHours(DoubleRound.doubleRound(minutes/60.0, 2));
				System.out.println("Task is now: " + task.toString());
				return true;
			}
		}
		System.out.println("Task not found");
		return false;
	}
	
	public boolean removeTask(int taskNumber, String taskName) {
		return taskSet.remove(this.getTask(taskNumber, taskName));
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
	    	System.out.println("ERROR: could not load data from file: " + file.getPath());
	    	e.printStackTrace();
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
	    	System.out.println("ERROR: could not save data to file: " + file.getPath());
	    	e.printStackTrace();
	    }
	}
	
}
