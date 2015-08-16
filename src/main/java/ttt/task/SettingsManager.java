package main.java.ttt.task;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(namespace = "main.java.ttt.task")
public class SettingsManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingsManager.class);
	
	public static SettingsManager settingsManager = new SettingsManager();
	
	@XmlElement(name = "projectRelatedName")
	private static String projectRelatedName = "MON";
	@XmlElement(name = "otherProjectRelatedId")
	private static String otherProjectRelatedId = "1";
	@XmlElement(name = "otherProjectRelatedName")
	private static String otherProjectRelatedName = "Projektrelateret";
	@XmlElement(name = "otherNonProjectRelatedId")
	private static String otherNonProjectRelatedId = "0";
	@XmlElement(name = "otherNonProjectRelatedName")
	private static String otherNonProjectRelatedName = "Ikke-projektrelateret";
	
	@XmlElement(name = "logDir")
	private static String logDir = "./logs";
	@XmlElement(name = "automaticLogging")
	private static boolean automaticLogging = false;
	
	// Private constructor for singleton class
	private SettingsManager() {
		
	}
	
	public static SettingsManager getInstance() {
		return settingsManager;
	}
	
	/**
	 * Save settings to file
	 */
	public void saveSettingsToFile() {
		File file = new File(Constants.SETTINGS_FILE_LOCATION);
		try {
	        JAXBContext context = JAXBContext
	                .newInstance(SettingsManager.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Marshalling and saving XML to the file.
	        m.marshal(this, file);

	    } catch (Exception e) { // catches ANY exception
//	        Alert alert = new Alert(AlertType.ERROR);
//	        alert.setTitle("Error");
//	        alert.setHeaderText("Could not save data");
//	        alert.setContentText("Could not save data to file:\n" + file.getPath());
//
//	        alert.showAndWait();
	    	LOGGER.error("Could not save settings to file: " + file.getPath(), e);
	    }
	}
	
	/**
	 * Retrieve settings from file
	 */
	public static void getSettingsFromFile() {
		File file = new File(Constants.SETTINGS_FILE_LOCATION);
		try {
			JAXBContext context = JAXBContext
					.newInstance(SettingsManager.class);
			Unmarshaller um = context.createUnmarshaller();
			SettingsManager settings2 = (SettingsManager) um.unmarshal(file);
			setProjectRelatedName(settings2.getProjectRelatedName());
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}
//	Unmarshaller um = context.createUnmarshaller();
//    Bookstore bookstore2 = (Bookstore) um.unmarshal(new FileReader(BOOKSTORE_XML));
//    ArrayList<Book> list = bookstore2.getBooksList();
//    for (Book book : list) {
//      System.out.println("Book: " + book.getName() + " from "
//          + book.getAuthor());
	
	/**
	 * @return the projectRelatedName
	 */
	public static String getProjectRelatedName() {
		return projectRelatedName;
	}
	/**
	 * @param projectRelatedName the projectRelatedName to set
	 */
	public static void setProjectRelatedName(String projectRelatedName) {
		SettingsManager.projectRelatedName = projectRelatedName;
	}
	/**
	 * @return the otherProjectRelatedId
	 */
	public static String getOtherProjectRelatedId() {
		return otherProjectRelatedId;
	}
	/**
	 * @param otherProjectRelatedId the otherProjectRelatedId to set
	 */
	public static void setOtherProjectRelatedId(String otherProjectRelatedId) {
		SettingsManager.otherProjectRelatedId = otherProjectRelatedId;
	}
	/**
	 * @return the otherProjectRelatedName
	 */
	public static String getOtherProjectRelatedName() {
		return otherProjectRelatedName;
	}
	/**
	 * @param otherProjectRelatedName the otherProjectRelatedName to set
	 */
	public static void setOtherProjectRelatedName(String otherProjectRelatedName) {
		SettingsManager.otherProjectRelatedName = otherProjectRelatedName;
	}
	/**
	 * @return the otherNonProjectRelatedId
	 */
	public static String getOtherNonProjectRelatedId() {
		return otherNonProjectRelatedId;
	}
	/**
	 * @param otherNonProjectRelatedId the otherNonProjectRelatedId to set
	 */
	public static void setOtherNonProjectRelatedId(String otherNonProjectRelatedId) {
		SettingsManager.otherNonProjectRelatedId = otherNonProjectRelatedId;
	}
	/**
	 * @return the otherNonProjectRelatedName
	 */
	public static String getOtherNonProjectRelatedName() {
		return otherNonProjectRelatedName;
	}
	/**
	 * @param otherNonProjectRelatedName the otherNonProjectRelatedName to set
	 */
	public static void setOtherNonProjectRelatedName(String otherNonProjectRelatedName) {
		SettingsManager.otherNonProjectRelatedName = otherNonProjectRelatedName;
	}
	/**
	 * @return the logDir
	 */
	public static String getLogDir() {
		return logDir;
	}
	/**
	 * @param logDir the logDir to set
	 */
	public static void setLogDir(String logDir) {
		SettingsManager.logDir = logDir;
	}
	/**
	 * @return the automaticLogging
	 */
	public static boolean isAutomaticLogging() {
		return automaticLogging;
	}
	/**
	 * @param automaticLogging the automaticLogging to set
	 */
	public static void setAutomaticLogging(boolean automaticLogging) {
		SettingsManager.automaticLogging = automaticLogging;
	}
	
	
	
}
