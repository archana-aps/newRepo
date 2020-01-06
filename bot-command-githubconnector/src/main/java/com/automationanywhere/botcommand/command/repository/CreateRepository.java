package com.automationanywhere.botcommand.command.repository;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automationanywhere.botcommand.github.executor.AbstractGithubExecuter;
import com.automationanywhere.botcommand.github.service.GithubCommandService;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.Sessions;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;

@BotCommand
@CommandPkg(name = "createRepository", label = "Create Repository", description = "Create a Repository on GitHub", icon = "pkg.svg")
public class CreateRepository extends AbstractGithubExecuter {
	
	@Sessions
	private Map<String, Object> sessionMap;

	private static final Logger LOGGER = LogManager.getLogger(CreateRepository.class);

	@Execute
	public void execute(
			@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "Name", description = "Enter the name of the Repository") @NotEmpty String name,
			@Idx(index = "2", type = AttributeType.TEXT) @Pkg(label = "Description", description = "Enter the description of the Repository") String description,
			@Idx(index = "1", type = AttributeType.BOOLEAN) @Pkg(label = "Is Private", description = "Check if you want to create a Private Repository") Boolean isPrivate) {

		LOGGER.info("creating a new repository...\n");

		GithubCommandService githubCommandService = getGithubCommandExecutor(sessionMap);
		LOGGER.info("Auth object created...\n");
		githubCommandService.createRepository(name, description, isPrivate);
		
		LOGGER.info("New repository has been successfully created...\n");
	}

	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

}
