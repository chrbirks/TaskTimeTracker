package main.java.ttt.logger;

import java.io.File;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.ttt.SettingsManager;
import main.java.ttt.task.TaskManager;

public class TaskLogger implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskLogger.class);
	
	private TaskManager taskManager;
	private String filepath;
	
	public TaskLogger(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	@Override
	public void run() {
		filepath = getDailyLogfileName();
		
		if (SettingsManager.isAutomaticLogging()) {
			LOGGER.debug("Automatic logging to file: " + filepath);
			taskManager.saveTaskDataToFile(new File(filepath));
		}
	}
	
	/**
	 * Return name of logfile with current date.
	 * For example: "./logs/2015-08-22.xml"
	 * @return String
	 */
	public static final String getDailyLogfileName() {
		StringBuilder sb = new StringBuilder();
		sb.append(SettingsManager.getLogDir());
		sb.append("/");
		sb.append(LocalDate.now().toString());
		sb.append(".xml"); // TODO: Get file type from settings
		return sb.toString();
	}

}
