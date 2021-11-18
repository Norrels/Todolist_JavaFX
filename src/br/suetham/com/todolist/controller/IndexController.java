package br.suetham.com.todolist.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.suetham.com.todolist.io.TarefaIO;
import br.suetham.com.todolist.model.StatusTarefa;
import br.suetham.com.todolist.model.Tarefa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class IndexController implements Initializable {
	
	private Tarefa tarefa;

    @FXML
    private TextField tfTitulo;

    @FXML
    private TextArea tfsobre;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btAdiar;

    @FXML
    private Button btConcluir;

    @FXML
    private Button btLimpar;

    @FXML
    private DatePicker dpDataRealiza��o;

    @FXML
    private ImageView imagvStatus;

    
    @FXML
    void cliqAdiar(ActionEvent event) {
    	Image adiada = new Image("file:///C:/Users/TecDevTarde/eclipse-workspace/todolist/src/br/suetham/com/todolist/imagens/projeto.png");
    	imagvStatus.setImage(adiada);
    }

    @FXML
    void cliqConcluir(ActionEvent event) {
    	Image finished = new Image("file:///C:/Users/TecDevTarde/eclipse-workspace/todolist/src/br/suetham/com/todolist/imagens/carraca.png");
    	imagvStatus.setImage(finished);
    }

    @FXML
    void cliqExcluir(ActionEvent event) {

    }

    @FXML
    void cliqLimpar(ActionEvent event) {
    	limparCampos();
    	
    	
    }

    @FXML
    void cllqSalvar(ActionEvent event) {
// valida��o de campos
    
    	if(dpDataRealiza��o.getValue() == null) {
    		JOptionPane.showMessageDialog(null, "Informe a data de realiza��o","Informe",JOptionPane.ERROR_MESSAGE);
    		dpDataRealiza��o.requestFocus();
    	} else if(tfTitulo.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe um t�tulo para tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		tfTitulo.requestFocus();
    	} else if(tfsobre.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe sobre a tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		tfsobre.requestFocus();
    	} else {
    		if(dpDataRealiza��o.getValue().isBefore(LocalDate.now())) {
    			JOptionPane.showMessageDialog(null, "Ops essa data j� passou informe uma data valida", "Informe", JOptionPane.ERROR_MESSAGE);
        		dpDataRealiza��o.requestFocus();
        		
    		} else {
    			//Iniciando a tarefa
    			tarefa = new Tarefa();
    			//populando a tarefa
    			tarefa.setDataCriacao(LocalDate.now());
    			tarefa.setDataLimite(dpDataRealiza��o.getValue());
    			tarefa.setTarefaNome(tfTitulo.getText());
    			tarefa.setComentario(tfsobre.getText());
    			tarefa.setStatus(StatusTarefa.ABERTA);
//    			Image Open = new Image("file:///C:/Users/TecDevTarde/eclipse-workspace/todolist/src/br/suetham/com/todolist/imagens/caneta.png");
//    		    imagvStatus.setImage(Open);
    		    	
    			}
    			//TODO 	salvar no banco de dados
    			//Limpar os campos do formul�rio
    			
    			limparCampos();
   
    		}
    	}
    
    private void limparCampos () {
    	tarefa = null;
    	tfTitulo.setText(" ");
    	tfsobre.setText(" ");
    	dpDataRealiza��o.setValue(null);
    	imagvStatus.setImage(null);
    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
 
}
