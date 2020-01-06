package com.automationanywhere.botcommand.command.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.client.GitHubClient;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.model.Schema;
import com.automationanywhere.botcommand.data.model.table.Row;
import com.automationanywhere.botcommand.data.model.table.Table;
import com.automationanywhere.botcommand.github.executor.GithubCommandExecuter;


public class CreateRepositoryTest {

	private static Map<String, Object> sessionMap = new HashMap<String, Object>();

	public static void main(String[] args) {

		System.out.println("----------hi-----------");

//		CreateRepository repository = new CreateRepository();
//		repository.setSessionMap(sessionMap);
//		repository.execute("cmdPublicRepo", "A private repo from cmd pkg", true);
		
		String token = "715e6fdab709909ab10ff8b882a8b116c7c5f779";
		GitHubClient client = new GitHubClient();
    	client.setOAuth2Token(token);
    	
    	GithubCommandExecuter exe = new GithubCommandExecuter(client);
//    	Table table = exe.getBranches("archana-aps", "--");
//    	Table table = exe.searchRepositories("archana-aps/", null);
//    	
//    	List<Schema> schema = table.getSchema();
//
//		for (Schema str : schema) {
//			System.out.print(str.getName() + ",  ");
//		}
//		System.out.println();
//
//		List<Row> rows = table.getRows();
//
//		for (Row row : rows) {
//			List<Value> rowValues = row.getValues();
//
//			for (Value value : rowValues)
//				System.out.print(value.get().toString() + ",  ");
//
//			System.out.println();
//
//		}
//    	exe.createIssue("archana-aps", "newRepo", "sgfg", " ", null, "bug,  kl   ", " ");
    	
    	exe.listIssues("assigned","","","","");

		System.out.println("----------bye-----------");
	}

}
