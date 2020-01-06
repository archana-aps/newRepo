package com.automationanywhere.botcommand.github.util;

import java.util.List;

import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.SearchRepository;

import com.automationanywhere.botcommand.data.model.Schema;

public class OperationUtils {
	
	public static void getRepositoryHeaders (List<Schema> headers) {
		headers.add(new Schema("id"));
		headers.add(new Schema("name"));
		headers.add(new Schema("description"));
		headers.add(new Schema("forks"));
		headers.add(new Schema("language"));
		headers.add(new Schema("createdAt"));
		headers.add(new Schema("homePage"));
		headers.add(new Schema("openIssues"));
		headers.add(new Schema("owner"));
		headers.add(new Schema("pushedAt"));
		headers.add(new Schema("size"));
		headers.add(new Schema("url"));
		headers.add(new Schema("watchers"));
	}
	
	public static void getRepositoryValues (List<String> row, SearchRepository repository) {
		row.add(repository.getId());
		row.add(repository.getName());
		row.add(repository.getDescription());
		row.add(((Integer) repository.getForks()).toString());
		row.add(repository.getLanguage());
		row.add(repository.getCreatedAt().toString());
		row.add(repository.getHomepage());
		row.add(((Integer) repository.getOpenIssues()).toString());
		row.add(repository.getOwner());
		row.add(repository.getPushedAt().toString());
		row.add(((Integer) repository.getSize()).toString());
		row.add(repository.getUrl());
		row.add(((Integer) repository.getWatchers()).toString());
	}
	
	public static void getIssueValues(List<String> row, RepositoryIssue issue) {
		row.add(Long.toString(issue.getId()));
		row.add(issue.getTitle());
		
		String body = issue.getBody();
		if(body != null)
			row.add(body);
		else
			row.add("");
		
		row.add(((Integer) issue.getNumber()).toString());
		
		String labelsName = "";
		List<Label> labels = issue.getLabels();
		for(Label label: labels) {
			labelsName += label.getName() + ",";	
		}
		labelsName.substring(0, labelsName.lastIndexOf(","));
		row.add(labelsName);
		
		try {
			row.add(((Integer)issue.getMilestone().getNumber()).toString());
		} catch(NullPointerException e) {
			row.add("");
		}
		
		row.add(issue.getRepository().getName());
		
		try {
			row.add(issue.getAssignee().getLogin());
		} catch(NullPointerException e) {
			row.add("");
		}
		
		row.add(issue.getState());
		row.add(((Integer)issue.getComments()).toString());
		row.add(issue.getCreatedAt().toString());
		
		try {
			row.add(issue.getClosedAt().toString());
		} catch(NullPointerException e) {
			row.add("");
		}
		
		try {
			row.add(issue.getUpdatedAt().toString());
		} catch(NullPointerException e) {
			row.add("");
		}
		
		row.add(issue.getHtmlUrl());
	}

}
