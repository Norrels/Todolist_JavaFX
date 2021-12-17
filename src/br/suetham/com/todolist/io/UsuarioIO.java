package br.suetham.com.todolist.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import br.suetham.com.todolist.model.Usuario;

public class UsuarioIO {

	
	private static final String FOLDER =  System.getProperty("user.home")+"/todolist";
	private static final String FILE_USER = FOLDER + "/user.csv";
	

	
	public static void cadastra(Usuario usuario) throws IOException {
		  File arqUser = new File(FILE_USER);
		  FileWriter writer = new FileWriter(arqUser, true);
		 writer.append(usuario.getLogin()+";");
		 writer.append(usuario.getSenha()+"\n");
		  writer.close();
		  
		}
	
	public static void createFiles() {
		try {
	
			File arqUser = new File(FILE_USER);
			
			if (!arqUser.exists() ) {
				arqUser.createNewFile();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean logar(String login, String senha) throws IOException {
		File arqUser = new File(FILE_USER);
		FileReader reader = new FileReader(arqUser);
		BufferedReader buff = new BufferedReader(reader);
		String linha;
		while ((linha = buff.readLine())!= null) {
			String[] vetor = linha.split(";");
			Usuario u = new Usuario();
			u.setLogin(vetor[0]);
			u.setSenha(vetor[1]);
			if(u.getSenha().equals(senha) && u.getLogin().equals(login)) {
				buff.close();
				reader.close();
				return true;
			}
		}
		buff.close();
		reader.close();
		return false;
	}
 
}
