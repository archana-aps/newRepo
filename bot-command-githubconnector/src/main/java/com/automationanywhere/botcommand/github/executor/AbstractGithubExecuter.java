package com.automationanywhere.botcommand.github.executor;

import static com.automationanywhere.botcommand.github.util.Constant.MESSAGES;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.egit.github.core.client.GitHubClient;

import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.botcommand.github.service.GithubCommandService;
import com.automationanywhere.botcommand.github.util.Constant;
import com.automationanywhere.botcommand.github.util.Utils;

public abstract class AbstractGithubExecuter {
	
	private static final Logger LOGGER = LogManager.getLogger(AbstractGithubExecuter.class);

	public GithubCommandService getGithubCommandExecutor(Map<String, Object> sessionMap) {

		validateUserSessionExists(sessionMap);

		Object commandExecutor = sessionMap.get(Constant.COMMAND_SERVICE);

		if (commandExecutor instanceof GithubCommandService)
			return (GithubCommandService) (commandExecutor);

		throw new BotCommandException(MESSAGES.getString("Github.User.Invalid"));
	}

	public void validateUserSessionExists(Map<String, Object> sessionMap) {
		
		LOGGER.info("Checking the presence of user in the sessionMap\n");

		if (sessionMap.containsKey(Constant.COMMAND_SERVICE))
		{
			LOGGER.info("User exists in sessionMap");
			return;
		}

		try {
			LOGGER.info("User does not exist in session Map. Checking the presence of user credentials...");
			Utils.createGithubDirectory();
			String filePath = Utils.getGithubUserCredentialsFilePath();
			LOGGER.info("Credentials file path is: " + filePath);

			if (!new File(filePath).exists())
				throw new BotCommandException(MESSAGES.getString("Github.User.NotExists", "logged-in user"));

			Properties propertiesReader = Utils.readFileSystemProperties(filePath);

			String token = propertiesReader.getProperty("Token");
			
			if(token == null) {
				throw new Exception();
			}

			System.out.println("Token is " + token);
			
			GitHubClient client = getGithubClient(token);
			
			GithubCommandService githubCommandService = new GithubCommandExecuter(client);
			sessionMap.put(Constant.COMMAND_SERVICE, githubCommandService);

		} catch (BotCommandException e) {
			LOGGER.error(e);
			throw new BotCommandException(e.getMessage());
		} catch (Exception exception) {
			LOGGER.error(MESSAGES.getString("JiraCloud.User.CredentialsNotFound") + exception + "\n");
			throw new BotCommandException(MESSAGES.getString("JiraCloud.User.CredentialsNotFound"));
		}

	}
	
	private GitHubClient getGithubClient(String token) {
		GitHubClient client = new GitHubClient();
    	client.setOAuth2Token(token);
    	
    	return client;
	}

}
