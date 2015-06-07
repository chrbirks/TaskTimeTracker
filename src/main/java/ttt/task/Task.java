/**
 * 
 */
package main.java.ttt.task;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import main.java.ttt.aux.LocalDateTimeAdapter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author chrbirks
 *
 */
public class Task {
	
	private StringProperty taskId;
	private StringProperty taskName;
	private ObjectProperty<LocalDateTime> time;
	private LongProperty elapsedMinutes;
	private DoubleProperty elapsedHours;
	
	// JABX unmarshaller needs default constructor?
	@SuppressWarnings("unused")
	private Task() {
		this.taskId = new SimpleStringProperty("Task123");
		this.taskName = new SimpleStringProperty("x");
		this.time = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
		this.elapsedMinutes = new SimpleLongProperty(this, "elapsedMinutes", 0);
		this.elapsedHours = new SimpleDoubleProperty(this, "elapsedHours", 0);
	}
	
	public Task(String taskId, String taskName, LocalDateTime time) {
		this.taskId = new SimpleStringProperty(taskId);
		this.taskName = new SimpleStringProperty(taskName);
		this.time = new SimpleObjectProperty<LocalDateTime>(time);
		this.elapsedMinutes = new SimpleLongProperty(this, "elapsedMinutes", 0);
		this.elapsedHours = new SimpleDoubleProperty(this, "elapsedHours", 0);
	}
	
	public void setTaskId(String id) {
		this.taskId.set(id);
	}
	
	public String getTaskId() {
		return taskId.get();
	}
	
	public StringProperty getTaskIdProperty() {
		return taskId;
	}
	
	public void setTaskName(String name) {
		this.taskName.set(name);
	}
	
	public String getTaskName() {
		return taskName.get();
	}
	
	public StringProperty getTaskNameProperty() {
		return taskName;
	}
	
	public void setTaskTime(LocalDateTime newTime) {
		time = new SimpleObjectProperty<LocalDateTime>(this, "time", newTime);
	}
	
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	public LocalDateTime getTaskTime() {
		return time.get();
	}
	
	public void setElapsedHours(double hours) {
		elapsedHours = new SimpleDoubleProperty(this, "elapsedHours", hours);
	}
	
	public double getElapsedHours() {
		return elapsedHours.get();
	}
	
	public DoubleProperty getElapsedHoursProperty() {
		return this.elapsedHours;
	}
	
	public void setElapsedMinutes(long minutes) {
		elapsedMinutes = new SimpleLongProperty(this, "elapsedMinutes", minutes);
	}
	
	public long getElapsedMinutes() {
		return elapsedMinutes.get();
	}
	
	public LongProperty getElapsedMinutesProperty() {
		return this.elapsedMinutes;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("taskId=");
		sb.append(taskId.get());
		sb.append(", taskName=");
		sb.append(taskName.get());
		sb.append(", time=");
		sb.append(time.get());
		sb.append(", totalHours=");
		sb.append(elapsedHours.get());
		sb.append(", totalMinutes=");
		sb.append(elapsedMinutes.get());
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
		result = prime * result	+ ((taskName == null) ? 0 : taskName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		
		if (taskId.get() == null) {
			if (other.taskId.get() != null)
				return false;
		} else if (!taskId.get().equals(other.taskId.get()))
			return false;
		
		if (taskName.get() == null) {
			if (other.taskName.get() != null)
				return false;
		} else if (!taskName.get().equals(other.taskName.get()))
			return false;
		
		return true;
	}

}
