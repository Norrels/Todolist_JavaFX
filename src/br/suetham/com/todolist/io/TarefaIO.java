package br.suetham.com.todolist.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import br.suetham.com.todolist.model.StatusTarefa;
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
	
	public static List<Tarefa> readTarefas() throws IOException{
		File arqTarefas = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<>();
		FileReader reader = new FileReader(arqTarefas);
		BufferedReader buff = new BufferedReader(reader);
		String linha;
		
		while ((linha = buff.readLine())!= null) {
			// transformando a linha em vetor
			String[] vetor = linha.split(";");
			//cria uma tarefa
			Tarefa t = new Tarefa();
			t.setId(Long.parseLong(vetor[0]));
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			t.setDataCriacao(LocalDate.parse(vetor[1],fmt));
			t.setDataLimite(LocalDate.parse(vetor[2], fmt));
			if(!vetor [3].isEmpty()) {
				t.setDataFinalizada(LocalDate.parse(vetor[3],fmt));
			}
			t.setTarefaNome(vetor[4]);
			t.setComentario(vetor[5]);
			int indStatus = Integer.parseInt(vetor[6]);
			t.setStatus(StatusTarefa.values()[indStatus]);
			tarefas.add(t);
		}
			buff.close();
			reader.close();
			Collections.sort(tarefas);
			return tarefas;
	}
	
	public static void AtualizaTarefas(List<Tarefa>tarefas) throws IOException {
		File arqTarefas = new File(FILE_TAREFA);
		FileWriter writer = new FileWriter(arqTarefas);
		for(Tarefa t : tarefas) {
			writer.append(t.formatToSave());
		}
		writer.close();
	}
	
	public static void exportHtml(List<Tarefa> lista, File arquivo) throws IOException {
		FileWriter writer = new FileWriter(arquivo);
		writer.append("<!DOCTYPE html>\n");
		writer.append("<html>\n");
		writer.append("<body>\n");
		writer.append("<h1>Lista de Tarefas</h1>\n");
		writer.append("<ul>\n");
		for(Tarefa tarefa : lista) {
			writer.append("<li>\n");
			writer.append(tarefa.getTarefaNome() + "-" + tarefa.getDataLimite());
			writer.append("</li>\n");
		}
		writer.append("<ul>	\n");
		writer.append("</body>\n");
		writer.append("</html>\n");
		writer.close();
		}
	}
	
	
