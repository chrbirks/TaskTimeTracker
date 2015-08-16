package main.java.ttt;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.ttt.task.Constants;

public class SettingsManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingsManager.class);
	
	public static SettingsManager settingsManager = new SettingsManager();
	public static Settings settings = new Settings();
	
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
	                .newInstance(Settings.class);
	        Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        // Marshalling and saving XML to the file.
	        m.marshal(settings, file);

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
	public void getSettingsFromFile() {
		File file = new File(Constants.SETTINGS_FILE_LOCATION);
		try {
			JAXBContext context = JAXBContext
					.newInstance(Settings.class);
			Unmarshaller um = context.createUnmarshaller();
			Settings settingsFromFile = (Settings) um.unmarshal(file);

			setProjectRelatedName(settingsFromFile.projectRelatedName);
			setOtherProjectRelatedId(settingsFromFile.otherProjectRelatedId);
			setOtherProjectRelatedName(settingsFromFile.otherProjectRelatedName);
			setOtherNonProjectRelatedId(settingsFromFile.otherNonProjectRelatedId);
			setOtherNonProjectRelatedName(settingsFromFile.otherNonProjectRelatedName);
			setLogDir(settingsFromFile.logDir);
			setAutomaticLogging(settingsFromFile.automaticLogging);
			
		} catch (UnmarshalException e) {
			LOGGER.warn("Unable to open settings file. Using default values.", e);
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
		return settings.projectRelatedName;
	}
	/**
	 * @param projectRelatedName the projectRelatedName to set
	 */
	public static void setProjectRelatedName(String projectRelatedName) {
		settings.projectRelatedName = projectRelatedName;
	}
	/**
	 * @return the otherProjectRelatedId
	 */
	public static String getOtherProjectRelatedId() {
		return settings.otherProjectRelatedId;
	}
	/**
	 * @param otherProjectRelatedId the otherProjectRelatedId to set
	 */
	public static void setOtherProjectRelatedId(String otherProjectRelatedId) {
		settings.otherProjectRelatedId = otherProjectRelatedId;
	}
	/**
	 * @return the otherProjectRelatedName
	 */
	public static String getOtherProjectRelatedName() {
		return settings.otherProjectRelatedName;
	}
	/**
	 * @param otherProjectRelatedName the otherProjectRelatedName to set
	 */
	public static void setOtherProjectRelatedName(String otherProjectRelatedName) {
		settings.otherProjectRelatedName = otherProjectRelatedName;
	}
	/**
	 * @return the otherNonProjectRelatedId
	 */
	public static String getOtherNonProjectRelatedId() {
		return settings.otherNonProjectRelatedId;
	}
	/**
	 * @param otherNonProjectRelatedId the otherNonProjectRelatedId to set
	 */
	public static void setOtherNonProjectRelatedId(String otherNonProjectRelatedId) {
		settings.otherNonProjectRelatedId = otherNonProjectRelatedId;
	}
	/**
	 * @return the otherNonProjectRelatedName
	 */
	public static String getOtherNonProjectRelatedName() {
		return settings.otherNonProjectRelatedName;
	}
	/**
	 * @param otherNonProjectRelatedName the otherNonProjectRelatedName to set
	 */
	public static void setOtherNonProjectRelatedName(String otherNonProjectRelatedName) {
		settings.otherNonProjectRelatedName = otherNonProjectRelatedName;
	}
	/**
	 * @return the logDir
	 */
	public static String getLogDir() {
		return settings.logDir;
	}
	/**
	 * @param logDir the logDir to set
	 */
	public static void setLogDir(String logDir) {
		settings.logDir = logDir;
	}
	/**
	 * @return the automaticLogging
	 */
	public static boolean isAutomaticLogging() {
		return settings.automaticLogging;
	}
	/**
	 * @param automaticLogging the automaticLogging to set
	 */
	public static void setAutomaticLogging(boolean automaticLogging) {
		settings.automaticLogging = automaticLogging;
	}	
	
	
}
