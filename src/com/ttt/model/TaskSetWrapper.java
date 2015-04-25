package com.ttt.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.ttt.task.Task;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 */
@XmlRootElement(name="tasks")
public class TaskSetWrapper {
	
	private List<Task> tasks;
	
	@XmlElement(name="task")
	public List<Task> getTasks() {
		return tasks;
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
