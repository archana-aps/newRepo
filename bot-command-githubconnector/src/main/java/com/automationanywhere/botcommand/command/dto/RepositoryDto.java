package com.automationanywhere.botcommand.command.dto;

import org.eclipse.egit.github.core.Repository;

public class RepositoryDto {
	
	private String name;
	private String description;
	private boolean isPrivate;
	
	public RepositoryDto (String name, String description, boolean isPrivate) {
		super();
		this.name = name;
		this.description = description;
		this.isPrivate = isPrivate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public Repository getRepository () {
		Repository repository = new Repository();
		repository.setName(this.name);
		
		if((this.description != null) && (!this.description.isBlank()))
			repository.setDescription(this.description);
		
		repository.setPrivate(this.isPrivate);
		
		return repository;
	}

}
