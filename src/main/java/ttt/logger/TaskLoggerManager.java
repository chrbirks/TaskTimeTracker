package main.java.ttt.logger;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import main.java.ttt.task.Constants;
import main.java.ttt.task.TaskManager;

public class TaskLoggerManager {
	
	private TaskManager taskManager;
	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> scheduledFuture;
	
	private TaskLogger taskLogger;
	
	public TaskLoggerManager(TaskManager taskManager, ScheduledExecutorService scheduler) {
		this.taskManager = taskManager;
		this.scheduler = scheduler;
		
		taskLogger = new TaskLogger(taskManager);
		
		scheduledFuture = scheduler.scheduleAtFixedRate(taskLogger, 0,
				Constants.LOGGING_INTERVAL, SECONDS);
	}
	
	

}
