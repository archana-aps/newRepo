package com.automationanywhere.botcommand.command.dto;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.User;

public class IssueDto {

	private String title;
	private String body;
	private Integer milestone;
	private String[] labels;
	private String assignee;

	public IssueDto(String title, String body, Integer milestone, String[] labels, String assignee) {
		super();
		this.title = title;
		this.body = body;
		this.milestone = milestone;
		this.labels = Arrays.stream(labels).map(String::trim).toArray(String[]::new);
		this.assignee = assignee;
	}

	public Issue getIssue() {
		Issue issue = new Issue();
		issue.setTitle(this.title);

		if ((this.body != null) && (!this.body.isBlank()))
			issue.setBody(this.body);

		if (this.milestone != null) {
			Milestone milestoneObject = new Milestone();
			milestoneObject.setNumber(this.milestone);
			issue.setMilestone(milestoneObject);
		}

		if (this.labels != null) {
			ArrayList<Label> labelsList = new ArrayList<Label>();
			Label label;
			for (String labelName : this.labels) {
				if ((labelName != null) && (!labelName.isBlank())) {
					label = new Label();
					label.setName(labelName.trim());
					labelsList.add(label);
				}
			}
			issue.setLabels(labelsList);
		}

		if ((this.assignee != null) && (!this.assignee.isBlank())) {
			User assigneeObject = new User();
			assigneeObject.setLogin(this.assignee);
			issue.setAssignee(assigneeObject);
		}

		return issue;
	}

}
