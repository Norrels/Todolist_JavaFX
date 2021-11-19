package br.suetham.com.todolist.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.suetham.com.todolist.model.Tarefa;

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

	public static void insert(Tarefa tarefa) throws FileNotFoundException, IOException {
	  File arqTarefas = new File(FILE_TAREFA);
	  File arqIds = new File(FILE_IDS);
	
	  //ler o ultimo id no FILE_IDS
	  Scanner leitor = new Scanner(arqIds);
	  tarefa.setId(leitor.nextLong());
	  leitor.close();
	  //Grava a tarefa no arquivo
	  FileWriter writer = new FileWriter(arqTarefas, true);
	  writer.append(tarefa.formatToSave());
	  writer.close();
	  //Gravar o proximo ID no arquivo de IDS
	  long proxId = tarefa.getId() + 1;
	  writer = new FileWriter(arqIds);
	  writer.write(proxId+"");
	  writer.close();
	  
	}
	
	public static List<Tarefa> readTarefas() throws FileNotFoundException{
		File arqTarefas = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<>();
		FileReader reader = new FileReader(arqTarefas);
		BufferedReader buff = new BufferedReader(reader);
		 return null;
	}
}