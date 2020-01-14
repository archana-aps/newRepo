package com.automationanywhere.botcommand.github.executor;

import static com.automationanywhere.botcommand.github.util.Constant.MESSAGES;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpRetryException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.TypedResource;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.RequestException;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.automationanywhere.botcommand.command.dto.IssueDto;
import com.automationanywhere.botcommand.command.dto.RepositoryDto;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.data.model.Schema;
import com.automationanywhere.botcommand.data.model.table.Row;
import com.automationanywhere.botcommand.data.model.table.Table;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.botcommand.github.service.GithubCommandService;
import com.google.gson.JsonObject;

import com.automationanywhere.botcommand.github.util.OperationUtils;

public class GithubCommandExecuter implements GithubCommandService {

	protected GitHubClient client;

	public GithubCommandExecuter(GitHubClient client) {
		this.client = client;
	}

	public GitHubClient getClient() {
		return client;
	}

	@Override
	public void createRepository(String name, String description, boolean isPrivate) {

		if ((name == null) || (name.isBlank()))
			throw new BotCommandException("Name cannot be empty.");

		RepositoryService service = new RepositoryService(this.client);
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

	@Override
	public Table getBranches(String ownerName, String repositoryName) {

		if ((repositoryName == null) || (repositoryName.isBlank()))
			throw new BotCommandException("Name cannot be empty.");
		if ((ownerName == null) || (ownerName.isBlank()))
			throw new BotCommandException("Owner cannot be empty.");

		RepositoryService service = new RepositoryService(this.client);

		IRepositoryIdProvider repositoryId = new RepositoryId(ownerName, repositoryName);

		Table table = new Table();
		List<Schema> headers = table.getSchema();
		List<Row> rows = table.getRows();
		headers.add(new Schema("name"));
		headers.add(new Schema("commit-sha"));
		headers.add(new Schema("commit-url"));
		List<String> row;
		List<Value> rowValue;
		Row actualRow;

		try {
			List<RepositoryBranch> branches = service.getBranches(repositoryId);
			for (RepositoryBranch branch : branches) {
				row = new ArrayList<String>();
				rowValue = new ArrayList<Value>();
				TypedResource commit = branch.getCommit();

				row.add(branch.getName());
				row.add(commit.getSha());
				row.add(commit.getUrl());

				rowValue = row.stream().map(StringValue::new).collect(Collectors.toList());
				actualRow = new Row(rowValue);
				rows.add(actualRow);
			}

			return table;
		} catch (IOException e) {
			if (e.getMessage().contains("401"))
				throw new BotCommandException("Check your Authentication credentials.");
			else if (e.getMessage().contains("404"))
				throw new BotCommandException("Repository not found.");
			else
				throw new BotCommandException("Unknown exception occured.");
		}

	}

	@Override
	public Table searchRepositories(String query, String language) {

		if ((query == null) || (query.isBlank()))
			throw new BotCommandException("Query cannot be empty.");

		RepositoryService service = new RepositoryService(this.client);

		Table table = new Table();
		List<Schema> headers = table.getSchema();
		List<Row> rows = table.getRows();

		OperationUtils.getRepositoryHeaders(headers);
		
		List<String> row;
		List<Value> rowValue;
		Row actualRow;

		try {
			List<SearchRepository> repositories = service.searchRepositories(query, language);
			for (SearchRepository repository : repositories) {
				row = new ArrayList<String>();
				rowValue = new ArrayList<Value>();
				
				OperationUtils.getRepositoryValues(row, repository);

				rowValue = row.stream().map(StringValue::new).collect(Collectors.toList());
				actualRow = new Row(rowValue);
				rows.add(actualRow);

			}
			return table;
		} catch (IOException e) {
			if (e.getMessage().contains("401"))
				throw new BotCommandException("Check your Authentication credentials.");
			else
				throw new BotCommandException("Unknown exception occured.");
		}

	}

	@Override
	public void createIssue(String ownerName, String repositoryName, String issueTitle, String issueBody,
			Integer issueMilestone, String issueLabels, String issueAssigne) {
		IssueService issueService = new IssueService(client);

		IRepositoryIdProvider repository = new RepositoryId(ownerName, repositoryName);
		
		String[] labels = null;
		if((issueLabels != null) && (!issueLabels.isBlank())) {
			labels = issueLabels.split(",");
		}
		IssueDto issueDto = new IssueDto(issueTitle, issueBody, issueMilestone, labels, issueAssigne);
		Issue issue = issueDto.getIssue();

		try {
			issueService.createIssue(repository, issue);
		} catch (RequestException e) {
			throw new BotCommandException("Check your Inputs..");
		} catch (HttpRetryException e) {
			throw new BotCommandException("Check your Authentication credentials.");
		} catch (IOException e) {
			throw new BotCommandException("Unknown Exception occured.");
		}

	}

	@Override
	public void listIssues(String filter, String state, String labels, String sort, String direction) {
		IssueService issueService = new IssueService(client);
		
		Table table = new Table();
		List<Schema> headers = table.getSchema();
		List<Row> rows = table.getRows();

//		OperationUtils.getRepositoryHeaders(headers);
		
		List<String> row;
		List<Value> rowValue;
		Row actualRow;
		
		Map<String, String> filterData = new HashMap<String, String>();
		filterData.put("filter", filter);
		filterData.put("state", state);
		filterData.put("labels", labels);
		filterData.put("sort", sort);
		filterData.put("direction", direction);
		
		try {
			List<RepositoryIssue> issues = issueService.getIssues(filterData);
			
			for(RepositoryIssue issue: issues) {
//				row = new ArrayList<String>();
//				rowValue = new ArrayList<Value>();
//				
////				OperationUtils.getRepositoryValues(row, repository);
//
//				rowValue = row.stream().map(StringValue::new).collect(Collectors.toList());
//				actualRow = new Row(rowValue);
//				rows.add(actualRow);
				System.out.println("----------issue--------");
				System.out.println(issue.getTitle());
//				System.out.println(issue.getBody());
//				System.out.println(issue.getNumber());
//				System.out.println(issue.getId());
//				System.out.println(issue.getRepository().getId());
//				System.out.println(issue.getRepository().getName());
//				System.out.println(issue.getMilestone());
//				System.out.println(issue.getLabels());
				System.out.println(issue.getHtmlUrl());
//				System.out.println(issue.getUrl());
//				System.out.println(issue.getComments());
//				System.out.println(issue.getClosedAt().toString());
//				System.out.println(issue.getUpdatedAt());
				
				
//				System.out.println(issue.getAssignee().getLogin());
//				System.out.println(issue.getMilestone().getNumber());
//				System.out.println(issue.getAssignee().getName());
//				System.out.println(issue.getAssignee().getEmail());
//				System.out.println(issue.getUser().getLogin());
				
			}
		} catch (RequestException e) {
			throw new BotCommandException("Check your Inputs..");
		} catch (HttpRetryException e) {
			throw new BotCommandException("Check your Authentication credentials.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new BotCommandException("Unknown Exception occured.");
		}
		
	}

}
