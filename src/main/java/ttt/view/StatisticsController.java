package main.java.ttt.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.ttt.task.Constants;
import main.java.ttt.task.SettingsManager;
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
	
	protected String projectTaskName = null;
	
	protected String otherProjectTaskId = null;
	protected String otherProjectTaskName = null;
	
	protected String otherNonProjectTaskId = null;
	protected String otherNonProjectTaskName = null;
	
	ObservableList<String> categories = FXCollections.observableArrayList();
	
	 /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize() {
		projectTaskName = SettingsManager.getProjectRelatedName();
		
		otherProjectTaskId = SettingsManager.getOtherProjectRelatedId();
		otherProjectTaskName = SettingsManager.getOtherProjectRelatedName();
		
		otherNonProjectTaskId = SettingsManager.getOtherNonProjectRelatedId();
		otherNonProjectTaskName = SettingsManager.getOtherNonProjectRelatedName();
		
		categories.add(projectTaskName);
		categories.add(otherProjectTaskName);
		categories.add(otherNonProjectTaskName);
		
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
			if (t.getTaskId() == otherProjectTaskId) {
				if (categoryMap.get(otherNonProjectTaskName) != null) {
					old_time = categoryMap.get(otherNonProjectTaskName);
				} else {
					old_time = 0;
				}
				categoryMap.put(otherNonProjectTaskName, time+old_time);
			} else if (t.getTaskId() == otherNonProjectTaskId) {
				if (categoryMap.get(otherProjectTaskName) != null) {
					old_time = categoryMap.get(otherProjectTaskName);
				} else {
					old_time = 0;
				}
				categoryMap.put(otherProjectTaskName, time+old_time);
			} else {
				if (categoryMap.get(projectTaskName) != null) {
					old_time = categoryMap.get(projectTaskName);
				} else {
					old_time = 0;
				}
				categoryMap.put(projectTaskName, time+old_time);
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
