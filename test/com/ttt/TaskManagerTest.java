package com.ttt;

import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ttt.task.Task;
import com.ttt.task.TaskManager;

public class TaskManagerTest {
	
//	private static final Logger logger = LoggerFactory.getLogger(TaskManagerTest.class);
	
	String defTaskName = "Confluence";
	int defTaskNumber = 512;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * 
	 */
	// TODO: Move to TaskTest
	@Test
	public void test_Task_equals() {
		TaskManager taskManager = TaskManager.getInstance();
		taskManager.clearTaskSet();
		LocalDateTime time = LocalDateTime.now();
		
		Task task1 = new Task(defTaskNumber, defTaskName, time);
		Task task2 = new Task(defTaskNumber, defTaskName, time);
		assertTrue(task1.equals(task2));
		assertEquals(task1, task2);
		
		Task task3 = new Task((int) 11, defTaskName, time);
		assertFalse(task1.equals(task3));
		assertNotEquals(task1, task3);
		
		Task task4 = new Task(defTaskNumber, "xxx", time);
		assertFalse(task1.equals(task4));
		assertNotEquals(task1, task4);
		
		Date date = new Date(1213);
		LocalDateTime newTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		
		Task task5 = new Task(defTaskNumber, defTaskName, newTime);
		assertTrue(task1.equals(task5));
		assertEquals(task1, task5);
	}

	/**
	 * 
	 */
	@Test
	public void test_TaskManager_is_singleton() {
		TaskManager taskManager = TaskManager.getInstance();
		TaskManager taskManager2 = TaskManager.getInstance();
		
		assertTrue(taskManager.equals(taskManager2));
	}
	
	/**
	 * 
	 */
	@Test//(expected=CloneNotSupportedException.class)
	public void test_TaskManager_is_uncloneable() {
		TaskManager taskManager = TaskManager.getInstance();
		taskManager.clearTaskSet();
		
		try {
			@SuppressWarnings("unused")
			TaskManager taskManager2 = taskManager.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Exception");
		} catch (Exception e) {
			System.out.println("Other exception");
		}
		System.out.println("No exception");
	}
	
	/**
	 * 
	 */
	@Test
	public void test_TaskManager_addTask_getTask_simpel() {
		TaskManager taskManager = TaskManager.getInstance();
		taskManager.clearTaskSet();
		LocalDateTime time = LocalDateTime.now();
		
		Task expected = new Task(defTaskNumber, defTaskName, time);
		taskManager.addTask(expected);
		
		System.out.println("tasknumber: " + expected.getTaskNumber());
		System.out.println("taskname: " + expected.getTaskName());

		Task actual = taskManager.getTask(
				expected.getTaskNumber(),
				expected.getTaskName());
		
		System.out.println("expected: " + expected);
		System.out.println("actual: " + actual);
		
		assertEquals(expected, actual);
		assertEquals("More or less than 1 Tasks in set: ", 1, taskManager.getTaskSetSize());
	}
	
	/**
	 * 
	 */
	@Test
	public void test_TaskManager_addTask_getTask() {
		TaskManager taskManager = TaskManager.getInstance();
		taskManager.clearTaskSet();
		LocalDateTime time = LocalDateTime.now();
		
		Task expected = new Task(defTaskNumber, defTaskName, time);
		taskManager.addTask(expected);
		
		Task notExpected = new Task((int) 123, "Jira", time);
		taskManager.addTask(notExpected);
		
		System.out.println("tasknumber: " + expected.getTaskNumber());
		System.out.println("taskname: " + expected.getTaskName());

		Task actual = taskManager.getTask(
				expected.getTaskNumber(),
				expected.getTaskName());
		
		System.out.println("expected: " + expected);
		System.out.println("actual: " + actual);
		
		assertEquals(expected, actual);
		assertEquals("More or less than 2 Tasks in set: ", 2, taskManager.getTaskSetSize());
	}
	
	/**
	 * 
	 */
	@Test
	public void test_TaskManager_addTask_identical_tasks() {
		TaskManager taskManager = TaskManager.getInstance();
		taskManager.clearTaskSet();
		LocalDateTime time = LocalDateTime.now();
		
		Task expected = new Task(defTaskNumber, defTaskName, time);
		taskManager.addTask(expected);
		taskManager.addTask(expected);

		Task actual = taskManager.getTask(
				expected.getTaskNumber(),
				expected.getTaskName());
		
		assertEquals(expected, actual);
		assertEquals("More or less than 1 Tasks in set: ", 1, taskManager.getTaskSetSize());
	}
	
	/**
	 * 
	 */
	@Test
	public void test_TaskManager_get_nonexisting_task() {
		TaskManager taskManager = TaskManager.getInstance();
		taskManager.clearTaskSet();
		LocalDateTime time = LocalDateTime.now();
		
		Task newTask = new Task(defTaskNumber, defTaskName, time);
		
		Task actual = taskManager.getTask(
				newTask.getTaskNumber(),
				newTask.getTaskName());
		
		assertNull(actual);
		assertEquals("More or less than 0 Tasks in set: ", 0, taskManager.getTaskSetSize());
	}
	
	/**
	 * 
	 */
	@Test
	public void test_TaskManager_removeTask() {
		TaskManager taskManager = TaskManager.getInstance();
		taskManager.clearTaskSet();
		LocalDateTime time = LocalDateTime.now();
		
		taskManager.addTask(defTaskNumber, defTaskName, time);
		assertEquals(1, taskManager.getTaskSetSize());
		
		boolean result = taskManager.removeTask(defTaskNumber, defTaskName);
		assertTrue(result);
		assertEquals("More or less than 0 Tasks in set: ", 0, taskManager.getTaskSetSize());
		
		
		result = taskManager.removeTask((int) 321, "Other Task");
		assertFalse("removeTask did not return false", result);
	}
	
	/**
	 * 
	 */
	@Test
	public void test_TaskManager_edit_task() {
		TaskManager taskManager = TaskManager.getInstance();
		taskManager.clearTaskSet();
		LocalDateTime time = LocalDateTime.now();
		
		Task expected = new Task(defTaskNumber, defTaskName, time);
		taskManager.addTask(expected);
		assertEquals(1, taskManager.getTaskSetSize());
		
		expected = new Task(1, "x", time);
		taskManager.editTask(defTaskNumber, defTaskName, 1, "x");
		Task acutal = taskManager.getTask(1, "x");
		assertEquals(expected, acutal);
		assertEquals(1, taskManager.getTaskSetSize());
	}

}

