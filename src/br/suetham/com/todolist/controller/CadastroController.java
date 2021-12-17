package br.suetham.com.todolist.controller;



import java.io.IOException;

import javax.swing.JOptionPane;


import br.suetham.com.todolist.io.UsuarioIO;
import br.suetham.com.todolist.model.Usuario;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class CadastroController {
	
	
	@FXML
	    private TextField newUser;

	    @FXML
	    private PasswordField newSenha;
	    
	    @FXML
	    private Button btCadastra;
	      
	    @FXML
	    void cliqCadastra() {
	    Usuario novoUser = new Usuario();
	    novoUser.setLogin(newUser.getText());
	    novoUser.setSenha(newSenha.getText());
	    	
	 try {
		UsuarioIO.createFiles();
	    UsuarioIO.cadastra(novoUser);
	    JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso");
	    Stage stage = (Stage) btCadastra.getScene().getWindow();
	    stage.close();

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    	
		}
    
		
	}


	

