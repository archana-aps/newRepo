package com.automationanywhere.botcommand.github.service;

import com.automationanywhere.botcommand.data.model.table.Table;

public interface GithubCommandService {
	
	void createRepository (String name, String description, boolean isPrivate);
	
	Table getBranches (String ownerName, String repositoryName);
	
	Table searchRepositories (String query, String language);

	void createIssue(String ownerName, String repositoryName, String issueTitle, String issueBody,
			Integer issueMilestone, String issueLabels, String issueAssigne);

	void listIssues(String filter, String state, String labels, String sort, String direction);

}
