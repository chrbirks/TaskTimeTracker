package main.java.ttt;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import main.java.ttt.task.Task;
import main.java.ttt.task.TaskManager;
import main.java.ttt.view.AddTaskDialogController;
import main.java.ttt.view.RootLayoutController;
import main.java.ttt.view.TaskEditDialogController;
import main.java.ttt.view.TaskOverviewController;
import main.java.ttt.view.TimeEditDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

//import com.atlassian.jira.rest.*;
//import com.atlassian.jira.rest.client.JiraRestClient;
//import com.atlassian.jira.rest.client.JiraRestClientFactory;
//import com.atlassian.jira.rest.client.domain.Issue;
//import com.atlassian.jira.rest.client.domain.Transition;
//import com.sun.jersey.*;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.CustomFieldOption;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;


public class TaskTimeTracker extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	TaskManager taskManager = TaskManager.getInstance();

	private TaskOverviewController taskOverviewController;

	// Locations for Eclipse
//	private static final String ROOT_LAYOUT_LOCATION = "/main/resources/ttt/view/RootLayout.fxml";
//	private static final String TASK_OVERVIEW_LOCATION = "/main/resources/ttt/view/TaskOverview.fxml";
//	private static final String ADDTASKDIALOG_LOCATION = "/main/resources/ttt/view/AddTaskDialog.fxml";
//	private static final String EDITTASKDIALOG_LOCATION = "/main/resources/ttt/view/TaskEditDialog.fxml";
//	private static final String TIMEEDITDIALOG_LOCATION = "/main/resources/ttt/view/TimeEditDialog.fxml";
	
	// Locations for Maven jar
	private static final String ROOT_LAYOUT_LOCATION = "/ttt/view/RootLayout.fxml";
	private static final String TASK_OVERVIEW_LOCATION = "/ttt/view/TaskOverview.fxml";
	private static final String ADDTASKDIALOG_LOCATION = "/ttt/view/AddTaskDialog.fxml";
	private static final String EDITTASKDIALOG_LOCATION = "/ttt/view/TaskEditDialog.fxml";
	private static final String TIMEEDITDIALOG_LOCATION = "/ttt/view/TimeEditDialog.fxml";
    
	
    // ExecutorService
	private static final int N_THREADS_CORE_POOL = 1;
	private static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(N_THREADS_CORE_POOL, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, "TimeKeeperScheduler");
				}
			});
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("TaskTimeTracker");

		initRootLayout();

		showTaskOverview();
	}

	@Override
	public void stop() {
		System.out.println("Closing application");
		scheduler.shutdown();
		try {
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the root layout and tries to load the last opened task file.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource(ROOT_LAYOUT_LOCATION));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			// Give the controller access to the main app.
			RootLayoutController controller = loader.getController();
			controller.setTaskTimeTracker(this);
			controller.setTaskManager(taskManager);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Try to load last opened person file.
		File file = taskManager.getTaskFilePath();
		if (file != null && file.exists() && !file.isDirectory()) {
			taskManager.loadTaskDataFromFile(file);
		}
	}

	/**
	 * Shows the task overview inside the root layout.
	 */
	public void showTaskOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource(TASK_OVERVIEW_LOCATION));
			AnchorPane taskOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(taskOverview);

			// Give the controller access to the main app.
			taskOverviewController = loader.getController();
			taskOverviewController.setTaskManager(taskManager);
			taskOverviewController.setScheduler(scheduler);
			taskOverviewController.setTaskTimeTracker(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * Opens a dialog to add a task. If the user clicks OK, the changes are
	 * saved into a new Task object and true is returned.
	 * 
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showAddTaskDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource(ADDTASKDIALOG_LOCATION));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Task");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the task into the controller.
			AddTaskDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Opens a dialog to edit details for the specified task. If the user clicks
	 * OK, the changes are saved into the provided Task object and true is
	 * returned.
	 * 
	 * @param task
	 *            the Task object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showTaskEditDialog(Task task) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource(EDITTASKDIALOG_LOCATION));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Task");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the task into the controller.
			TaskEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTask(task);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
     * 
     */
	public boolean showTimeEditDialog(Task task) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(TaskTimeTracker.class
					.getResource(TIMEEDITDIALOG_LOCATION));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit time");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			TimeEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setTime(task);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param args
	 *            are not used
	 */
	public static void main(String[] args) {
		
//		final JiraRestClientFactory factory = new JiraRestClientFactory();
//        final URI jiraServerUri = new URI("http://localhost:8090/jira");
//        final JiraRestClient restClient = factory.createWithBasicHttpAuthentication(jiraServerUri, "yourusername", "yourpassword");
//        final NullProgressMonitor pm = new NullProgressMonitor();
//        final Issue issue = restClient.getIssueClient().getIssue("TST-1", pm);
// 
//        System.out.println(issue);
// 
//        // now let's vote for it
//        restClient.getIssueClient().vote(issue.getVotesUri(), pm);
// 
//        // now let's watch it
//        restClient.getIssueClient().watch(issue.getWatchers().getSelf(), pm);
// 
//        // now let's start progress on this issue
//        final Iterable<Transition> transitions = restClient.getIssueClient().getTransitions(issue.getTransitionsUri(), pm);
//        final Transition startProgressTransition = getTransitionByName(transitions, "Start Progress");
//        restClient.getIssueClient().transition(issue.getTransitionsUri(), new TransitionInput(startProgressTransition.getId()), pm);
// 
//        // and now let's resolve it as Incomplete
//        final Transition resolveIssueTransition = getTransitionByName(transitions, "Resolve Issue");
//        Collection<FieldInput> fieldInputs = Arrays.asList(new FieldInput("resolution", "Incomplete"));
//        final TransitionInput transitionInput = new TransitionInput(resolveIssueTransition.getId(), fieldInputs, Comment.valueOf("My comment"));
//        restClient.getIssueClient().transition(issue.getTransitionsUri(), transitionInput, pm);
		
		///////////////////////////////////////////
		
		BasicCredentials creds = new BasicCredentials("admin", "xxx");
//        JiraClient jira = new JiraClient("https://ctest1.atlassian.net", creds);
		
//        try {
//            /* Retrieve issue TEST-123 from JIRA. We'll get an exception if this fails. */
//            Issue issue = jira.getIssue("TEST-123");
//
//            /* Print the issue key. */
//            System.out.println(issue);
//
//            /* You can also do it like this: */
//            System.out.println(issue.getKey());
//
//            /* Vote for the issue. */
//            issue.vote();
//
//            /* And also watch it. Add Robin too. */
//            issue.addWatcher(jira.getSelf());
//            issue.addWatcher("robin");
//
//            /* Open the issue and assign it to batman. */
//            issue.transition()
//                .field(Field.ASSIGNEE, "batman")
//                .execute("Open");
//
//            /* Add two comments, with one limited to the developer role. */
//            issue.addComment("No problem. We'll get right on it!");
//            issue.addComment("He tried to send a whole Internet!", "role", "Developers");
//
//            /* Print the reporter's username and then the display name */
//            System.out.println("Reporter: " + issue.getReporter());
//            System.out.println("Reporter's Name: " + issue.getReporter().getDisplayName());
//
//            /* Print existing labels (if any). */
//            for (String l : issue.getLabels())
//                System.out.println("Label: " + l);
//
//            /* Change the summary and add two labels to the issue. The double-brace initialiser
//               isn't required, but it helps with readability. */
//            issue.update()
//                .field(Field.SUMMARY, "tubes are clogged")
//                .field(Field.LABELS, new ArrayList() {{
//                    addAll(issue.getLabels());
//                    add("foo");
//                    add("bar");
//                }})
//                .field(Field.PRIORITY, Field.valueById("1")) /* you can also set the value by ID */
//                .execute();
//
//            /* You can also update values with field operations. */
//            issue.update()
//                .fieldAdd(Field.LABELS, "baz")
//                .fieldRemove(Field.LABELS, "foo")
//                .execute();
//
//            /* Print the summary. We have to refresh first to pickup the new value. */
//            issue.refresh();
//            System.out.println("New Summary: " + issue.getSummary());
//
//            /* Now let's start progress on this issue. */
//            issue.transition().execute("Start Progress");
//
//            /* Pretend customfield_1234 is a text field. Get the raw field value... */
//            Object cfvalue = issue.getField("customfield_1234");
//
//            /* ... Convert it to a string and then print the value. */
//            String cfstring = Field.getString(cfvalue);
//            System.out.println(cfstring);
//
//            /* And finally, change the value. */
//            issue.update()
//                .field("customfield_1234", "new value!")
//                .execute();
//
//            /* Pretend customfield_5678 is a multi-select box. Print out the selected values. */
//            List<CustomFieldOption> cfselect = Field.getResourceArray(
//                CustomFieldOption.class,
//                issue.getField("customfield_5678"),
//                jira.getRestClient()
//            );
//            for (CustomFieldOption cfo : cfselect)
//                System.out.println("Custom Field Select: " + cfo.getValue());
//
//            /* Print out allowed values for the custom multi-select box. */
//            List<CustomFieldOption> allowedValues = jira.getCustomFieldAllowedValues("customfield_5678", "TEST", "Task");
//            for (CustomFieldOption customFieldOption : allowedValues)
//                System.out.println(customFieldOption.getValue());
//
//            /* Set two new values for customfield_5678. */
//            issue.update()
//                .field("customfield_5678", new ArrayList() {{
//                    add("foo");
//                    add("bar");
//                    add(Field.valueById("1234")); /* you can also update using the value ID */
//                }})
//                .execute();
//
//            /* Add an attachment */
//            File file = new File("C:\\Users\\John\\Desktop\\screenshot.jpg");
//            issue.addAttachment(file);
//
//            /* And finally let's resolve it as incomplete. */
//            issue.transition()
//                .field(Field.RESOLUTION, "Incomplete")
//                .execute("Resolve Issue");
//
//            /* Create a new issue. */
//            Issue newIssue = jira.createIssue("TEST", "Bug")
//                .field(Field.SUMMARY, "Bat signal is broken")
//                .field(Field.DESCRIPTION, "Commissioner Gordon reports the Bat signal is broken.")
//                .field(Field.REPORTER, "batman")
//                .field(Field.ASSIGNEE, "robin")
//                .execute();
//            System.out.println(newIssue);
//
//            /* Link to the old issue */
//            newIssue.link("TEST-123", "Dependency");
//
//            /* Create sub-task */
//            Issue subtask = newIssue.createSubtask()
//                .field(Field.SUMMARY, "replace lightbulb")
//                .execute();
//
//            /* Search for issues */
//            Issue.SearchResult sr = jira.searchIssues("assignee=batman");
//            System.out.println("Total: " + sr.total);
//            for (Issue i : sr.issues)
//                System.out.println("Result: " + i);
//
//        } catch (JiraException ex) {
//            System.err.println(ex.getMessage());
//
//            if (ex.getCause() != null)
//                System.err.println(ex.getCause().getMessage());
//        }
    

		
		

//		TaskManager taskManager = TaskManager.getInstance();
//		taskManager.addTask(123, "Jira", LocalDateTime.now());
//		taskManager.addTask(456, "Confluence", LocalDateTime.now());
//		taskManager.addTask(789, "Jenkins", LocalDateTime.now());
//		taskManager.addTask(789, "Jenkins", LocalDateTime.now());

		launch(args);
	}
	
//	private static Transition getTransitionByName(Iterable<Transition> transitions, String transitionName) {
//        for (Transition transition : transitions) {
//            if (transition.getName().equals(transitionName)) {
//                return transition;
//            }
//        }
//        return null;
//    }

}