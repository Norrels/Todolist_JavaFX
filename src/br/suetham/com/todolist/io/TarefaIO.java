package br.suetham.com.todolist.io;

import java.io.File;
import java.io.FileWriter;

public class TarefaIO {
	private static final String FOLDER =  System.getProperty("user.home")+"/todolist";
	private static final String FILE_IDS = FOLDER + "/id.csv";
	private static final String FILE_TAREFA = FOLDER + "/tarefas.csv";
	
	public static void createFiles() {
		try {
			File pasta = new File(FOLDER);
			File arqIds = new File(FILE_IDS);
			File arqTarefas = new File(FILE_TAREFA);
			if (!pasta.exists() ) {
				pasta.mkdir();
				arqIds.createNewFile();
				arqTarefas.createNewFile();
				FileWriter writer = new FileWriter(arqIds);
				writer.write("1");
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
