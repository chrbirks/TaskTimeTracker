package main.java.ttt.task;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.ttt.view.TaskOverviewController;

public final class TimeKeeper implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeKeeper.class);
	
	private Task activeTask;
	
	@SuppressWarnings("unused")
	private TaskOverviewController taskOverviewController;
	
	private LocalDateTime taskStartTime;
	private String elapsedHours;
	private long elapsedMinutes;
	private long elapsedSeconds;
	private String origHours;
	private long origMinutes;
		
	// Constructor
	public TimeKeeper(Task activeTask, TaskOverviewController taskOverviewController) {
		this.activeTask = activeTask;
		this.taskOverviewController = taskOverviewController;
		
		// Reset time stamp on active task
		resetTime(activeTask);
		
		// Store previously spend time on task
		origHours = activeTask.getElapsedHours();
		origMinutes = activeTask.getElapsedMinutes();
	}
	
	@Override
	public void run() {
		// Update elapsed for active task
		calcTimeDiff(activeTask);
	}
	
	/**
	 * Calculate elapsed time
	 * @param task
	 */
	private void calcTimeDiff(Task task) {
		// Calculate time since task was activated
		taskStartTime = task.getTaskTime();
		elapsedSeconds = taskStartTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);
		elapsedMinutes = taskStartTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
//		elapsedHours = DoubleRound.doubleRound(elapsedMinutes/60.0f, 2);
		BigDecimal hours = new BigDecimal(elapsedMinutes/60.0).setScale(2, RoundingMode.HALF_EVEN);
		elapsedHours = hours.toString();
		LOGGER.debug("Elapsed time (h:m:s): " + elapsedHours + ":" + elapsedMinutes + ":" + elapsedSeconds);
		
		// Update internal times in task
		task.setElapsedMinutes(origMinutes + elapsedMinutes);
		BigDecimal totalHours = new BigDecimal(origHours)
				.add(new BigDecimal(elapsedHours))
				.setScale(2, RoundingMode.HALF_EVEN);
//		LOGGER.debug("***** {}", origHours);
//		LOGGER.debug("***** {}", elapsedHours);
//		LOGGER.debug("***** {}", totalHours.toString());
		task.setElapsedHours(totalHours);
		LOGGER.debug("Total elapsed time (h:m): " + elapsedHours.toString() + ":" + task.getElapsedMinutes());
	}

	private void resetTime(Task task) {
		task.setTaskTime(LocalDateTime.now());
		LOGGER.debug("Resetting time to: " + task.getTaskTime());
	}
	

}
