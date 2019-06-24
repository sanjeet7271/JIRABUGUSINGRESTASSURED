package com.jiraBug.configreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @author sanjeetpandit
 *
 */
public class GlobalReaderForTask {
	public Properties property = null;
	public String hostURL;
	public String resourseURL, endPointURL;
	FileInputStream input = null;
	JsonNode json = null;
	ObjectMapper mapper;;
	public static Logger logger = Logger.getLogger(GlobalReaderForTask.class);

	/**
	 * 
	 * @throws IOException
	 */
	public GlobalReaderForTask() throws IOException {
		try {
			input = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/resources/config/configForTask.properties");
			property = new Properties();
			property.load(input);
			hostURL = property.getProperty("URL");
			resourseURL = property.getProperty("bugResourseURL");
			endPointURL = hostURL + resourseURL;
			input.close();
		} catch (FileNotFoundException e) {
			logger.error("Exception " + e);
			logger.error("Properties file not found.");
			Assert.fail("Properties file not found.");
		}
	}
}
