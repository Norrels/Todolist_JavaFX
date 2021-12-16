package br.suetham.com.todolist.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.suetham.com.todolist.io.TarefaIO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	    	System.out.println(newUser.getText()+"");
	    	
//		try {
////			TarefaIO.cadastra(newUser.getText(), newSenha.getText());
//			JOptionPane.showMessageDialog(null, "Usuário Cadastrado");
//			Stage stage = (Stage) btCadastra.getScene().getWindow();
//	    	stage.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}

	

