package main.java.ttt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Container for settings variables. Changed through the Settings menu.
 * The default values are loaded if no settings file is found.
 * 
 * @author chrbirks
 *
 */
@XmlRootElement(namespace = "main.java.ttt")
public class Settings {
	
	@XmlElement(name = "projectRelatedName")
	protected String projectRelatedName = "MON";
	
	@XmlElement(name = "otherProjectRelatedId")
	protected String otherProjectRelatedId = "1";
	@XmlElement(name = "otherProjectRelatedName")
	protected String otherProjectRelatedName = "Projektrelateret";
	
	@XmlElement(name = "otherNonProjectRelatedId")
	protected String otherNonProjectRelatedId = "0";
	@XmlElement(name = "otherNonProjectRelatedName")
	protected String otherNonProjectRelatedName = "Ikke-projektrelateret";
	
	@XmlElement(name = "logDir")
	protected String logDir = "./logs";
	@XmlElement(name = "automaticLogging")
	protected boolean automaticLogging = false;
	
}
