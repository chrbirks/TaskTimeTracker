package main.java.ttt.task;

public class Settings {
	
	public static String projectRelatedName = "MON";
	public static String otherProjectRelatedId = "1";
	public static String otherProjectRelatedName = "Projektrelateret";
	public static String otherNonProjectRelatedId = "0";
	public static String otherNonProjectRelatedName = "Ikke-projektrelateret";
	
	public static String logDir = "./logs";
	public static boolean automaticLogging = false;
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
		Settings.projectRelatedName = projectRelatedName;
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
		Settings.otherProjectRelatedId = otherProjectRelatedId;
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
		Settings.otherProjectRelatedName = otherProjectRelatedName;
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
		Settings.otherNonProjectRelatedId = otherNonProjectRelatedId;
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
		Settings.otherNonProjectRelatedName = otherNonProjectRelatedName;
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
		Settings.logDir = logDir;
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
		Settings.automaticLogging = automaticLogging;
	}
	
	
	
}
