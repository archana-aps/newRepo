package com.automationanywhere.botcommand.github.service;

import com.automationanywhere.botcommand.data.model.table.Table;

public interface GithubOperationService {
	
	String getDataFromDatatable (Table dataTable, String fieldsList);

}
