/**
 * 
 */
package com.ttt.task;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.ttt.aux.LocalDateTimeAdapter;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * @author chrbirks
 *
 */
public class Task {
	
	private IntegerProperty taskNumber;
	private StringProperty taskName;
	private ObjectProperty<LocalDateTime> time;
	private LongProperty elapsedMinutes;
	private DoubleProperty elapsedHours;
	
	// JABX unmarshaller needs default constructor?
	@SuppressWarnings("unused")
	private Task() {
		this.taskNumber = new SimpleIntegerProperty(999);
		this.taskName = new SimpleStringProperty("x");
		this.time = new SimpleObjectProperty<LocalDateTime>(LocalDateTime.now());
		this.elapsedMinutes = new SimpleLongProperty(this, "elapsedMinutes", 0);
		this.elapsedHours = new SimpleDoubleProperty(this, "elapsedHours", 0);
	}
	
	public Task(int taskNumber, String taskName, LocalDateTime time) {
		this.taskNumber = new SimpleIntegerProperty(taskNumber);
		this.taskName = new SimpleStringProperty(taskName);
		this.time = new SimpleObjectProperty<LocalDateTime>(time);
		this.elapsedMinutes = new SimpleLongProperty(this, "elapsedMinutes", 0);
		this.elapsedHours = new SimpleDoubleProperty(this, "elapsedHours", 0);
	}
	
	public void setTaskNumber(int number) {
		this.taskNumber.set(number);
	}
	
	public int getTaskNumber() {
		return this.taskNumber.get();
	}
	
	public IntegerProperty getTaskNumberProperty() {
		return this.taskNumber;
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
		sb.append("taskNumber=");
		sb.append(taskNumber.get());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((taskName == null) ? 0 : taskName.hashCode());
		result = prime * result + taskNumber.get();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (taskName.get() == null) {
			if (other.taskName.get() != null)
				return false;
		} else if (!taskName.get().equals(other.taskName.get()))
			return false;
		if (taskNumber.get() != other.taskNumber.get())
			return false;
		return true;
	}


}
