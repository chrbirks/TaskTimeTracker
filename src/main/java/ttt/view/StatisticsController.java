package main.java.ttt.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.ttt.task.Constants;
import main.java.ttt.task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class StatisticsController {

	@FXML
	private BarChart<String, Double> barChart;

	@FXML
	private CategoryAxis xAxis;
	
	ObservableList<String> categories = FXCollections.observableArrayList();
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize() {
		categories.add(Constants.PROJECT_TASK_NAME);
		categories.add(Constants.OTHER_PROJECT_TASK_NAME);
		categories.add(Constants.OTHER_NON_PROJECT_TASK_NAME);
		
		xAxis.setCategories(categories);
	}
	
	/**
     * Sets the tasks to show the statistics for.
     * 
     * @param tasks
     */
	public void setTaskData(List<Task> tasks) {
		Map<String, Double> categoryMap = new HashMap<String, Double>();
		double old_time;
		
		// Loop through all tasks and store the time in the appropriate category
		for (Task t : tasks) {
			double time = t.getElapsedHours();
			switch (t.getTaskId()) {
			case Constants.OTHER_NON_PROJECT_TASK_ID : 
				if (categoryMap.get(Constants.OTHER_NON_PROJECT_TASK_NAME) != null) {
					old_time = categoryMap.get(Constants.OTHER_NON_PROJECT_TASK_NAME);
				} else {
					old_time = 0;
				}
				categoryMap.put(Constants.OTHER_NON_PROJECT_TASK_NAME, time+old_time);
				break;
			case Constants.OTHER_PROJECT_TASK_ID :
				if (categoryMap.get(Constants.OTHER_PROJECT_TASK_NAME) != null) {
					old_time = categoryMap.get(Constants.OTHER_PROJECT_TASK_NAME);
				} else {
					old_time = 0;
				}
				categoryMap.put(Constants.OTHER_PROJECT_TASK_NAME, time+old_time);
				break;
			default : 
				if (categoryMap.get(Constants.PROJECT_TASK_NAME) != null) {
					old_time = categoryMap.get(Constants.PROJECT_TASK_NAME);
				} else {
					old_time = 0;
				}
				categoryMap.put(Constants.PROJECT_TASK_NAME, time+old_time);
				break;
			}
		}
		
		XYChart.Series<String, Double> series = new XYChart.Series<>();
		
		// Create a XYChart series for each task category
		Iterator<Entry<String, Double>> itr = categoryMap.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();
			XYChart.Data xychart = new XYChart.Data<>();
			xychart.setXValue(entry.getKey());
			xychart.setYValue(entry.getValue());
			
			series.getData().add(xychart);
		}
		
		barChart.getData().add(series);
		
	}
}
