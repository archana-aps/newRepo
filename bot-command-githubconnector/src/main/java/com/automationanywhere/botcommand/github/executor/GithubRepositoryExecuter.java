package com.automationanywhere.botcommand.github.executor;

import java.io.IOException;
import java.net.HttpRetryException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.automationanywhere.botcommand.command.dto.RepositoryDto;
import com.automationanywhere.botcommand.exception.BotCommandException;

public class GithubRepositoryExecuter extends GithubCommandExecuter {
	
	RepositoryService service;

	public GithubRepositoryExecuter(GitHubClient client) {
		super(client);
		this.service = new RepositoryService(client);
	}

	@Override
	public void createRepository(String name, String description, boolean isPrivate) {

		if ((name == null) || (name.isBlank()))
			throw new BotCommandException("Name cannot be empty.");

		RepositoryService service = new RepositoryService(getClient());
		RepositoryDto repositoryDto = new RepositoryDto(name, description, isPrivate);
		Repository repository = repositoryDto.getRepository();

		try {
			service.createRepository(repository);
		} catch (RequestException e) {
			throw new BotCommandException("A repository with the given name already exists on this account.");
		} catch (HttpRetryException e) {
			throw new BotCommandException("Check your Authentication credentials.");
		} catch (IOException e) {
			throw new BotCommandException("Cannot create repository. Unknown Exception occured.");
		}
	}
}
