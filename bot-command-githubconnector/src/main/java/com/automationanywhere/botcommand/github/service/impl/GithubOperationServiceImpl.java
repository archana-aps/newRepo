package com.automationanywhere.botcommand.github.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.automationanywhere.botcommand.data.model.Schema;
import com.automationanywhere.botcommand.data.model.table.Row;
import com.automationanywhere.botcommand.data.model.table.Table;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.botcommand.github.service.GithubOperationService;

public class GithubOperationServiceImpl implements GithubOperationService {
	
	public GithubOperationServiceImpl() {
		super();
	}

	@Override
	public String getDataFromDatatable(Table dataTable, String fieldsList) {
		
		StringBuilder result = new StringBuilder();
		
		List<Schema> headers = dataTable.getSchema();
		List<Row> rows = dataTable.getRows();
		
		if ((headers.isEmpty()) || (rows.isEmpty()))
			return "";
		
		List<Integer> headerIndexList = getHeaderIndex(headers, fieldsList);
		
		StringBuilder row;
		
		for (int i = 0; i < rows.size(); i++) {
			row = new StringBuilder();
			for(Integer index: headerIndexList) {
				row.append(rows.get(i).getValues().get(index).toString());
				row.append(", ");
			}
			row = new StringBuilder(row.substring(0, row.lastIndexOf(",")));
			row.append(System.getProperty("line.separator"));
			result.append(row);
		}
		
		return result.toString();
	}
	
	public List<Integer> getHeaderIndex (List<Schema> headers, String fieldsList) {
				
		List<Integer> csvHeaderIndexList = new ArrayList<Integer>();
		
		List<String> tableHeadersList = headers.stream().map(head -> head.getName().toLowerCase()).collect(Collectors.toList());
		String[] fields = fieldsList.split(",");
		
		for(String field: fields) {
			int index = tableHeadersList.indexOf(field.trim().toLowerCase());
			
			if (index == -1)
				throw new BotCommandException("Invalid headers in input fields");
			csvHeaderIndexList.add(index);
		}
		return csvHeaderIndexList;
		
	}

}
