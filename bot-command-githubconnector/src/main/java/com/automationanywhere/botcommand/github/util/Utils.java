package com.automationanywhere.botcommand.github.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automationanywhere.botcommand.exception.BotCommandException;

public class Utils {

	private static final String GITHUB = "github";

	private static final String CONNECTOR = "Connector";

	private static final String NATIVE_FOLDER_PATH = System.getenv("PROGRAMDATA").concat(File.separator)
			.concat("AutomationAnywhere");

	private static final String GITHUB_CREDENTIAL_FILE_NAME = "github-credentials.properties";
	
	private static final Logger LOGGER = LogManager.getLogger(Utils.class);

	/**
	 * This method getCredentialFilePathAsString returns user credentials file path
	 * as a string. @param userEmailAddress user email address.
	 * 
	 * @return path of user credentials file. eg-
	 *         "C:\ProgramData\AutomationAnywhere\office365Auth\ABCcredentials.json".
	 */
	public static String getGithubUserCredentialsFilePath() {
		return new StringBuilder().append(NATIVE_FOLDER_PATH).append(File.separator).append(CONNECTOR)
				.append(File.separator).append(GITHUB).append(File.separator).append(GITHUB_CREDENTIAL_FILE_NAME)
				.toString();
	}
	
//	public static boolean isEmailValid(String email) {
//		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
//				+ "A-Z]{2,7}$";
//
//		Pattern pat = Pattern.compile(emailRegex);
//		if (email == null)
//			return false;
//		return pat.matcher(email).matches();
//	}

	public static Properties readFileSystemProperties(String file) {

		try {
			Properties properties = new Properties();
			// load default properties first
			try (InputStream inputStream = new FileInputStream(new File(file))) {
				if (inputStream != null) {
					properties.load(inputStream);
				}
			}
			return properties;
		} catch (Exception exception) {
			throw new BotCommandException("Internal error");
		}

	}
	
	public static File createGithubDirectory() throws IOException {
		LOGGER.debug("Creating a directory ...");
		File aaFile = new File(NATIVE_FOLDER_PATH);
		if (!aaFile.exists()) {
			aaFile.mkdir();
			LOGGER.debug(" native folder created...");
		} else {
			LOGGER.debug(" native folder already exists...");
		}
		LOGGER.info(" Generate User file...");

		File generatedDir = new File(NATIVE_FOLDER_PATH, CONNECTOR);

		if (!generatedDir.mkdir()) {
			LOGGER.debug("package " + CONNECTOR + "folder already exists");
		}

		generatedDir = new File(generatedDir, GITHUB);

		if (!generatedDir.mkdir()) {
			LOGGER.debug("package " + CONNECTOR + "folder already exists");
		}

		return generatedDir;
	}

	public static String getFileExtension(String fileName) {
		int lastIndexOf = fileName.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return fileName.substring(lastIndexOf);
	}

	public static String getFileNameWithExtension(String fileName) {
		int lastIndexOf = fileName.lastIndexOf(File.separator);
		if (lastIndexOf == -1 || lastIndexOf >= fileName.length()) {
			return ""; // empty extension
		}
		return fileName.substring(lastIndexOf + 1);
	}

}
