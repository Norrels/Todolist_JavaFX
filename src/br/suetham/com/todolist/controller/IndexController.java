package br.suetham.com.todolist.controller;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class IndexController {

    @FXML
    private TextField tfNome;

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
    private DatePicker dpDataRealização;

    @FXML
    private ImageView imagvStatus;

    @FXML
    void cliqAdiar(ActionEvent event) {

    }

    @FXML
    void cliqConcluir(ActionEvent event) {

    }

    @FXML
    void cliqExcluir(ActionEvent event) {

    }

    @FXML
    void cliqLimpar(ActionEvent event) {

    }

    @FXML
    void cllqSalvar(ActionEvent event) {
// validação de campos
    	if(dpDataRealização.getValue() == null) {
    		JOptionPane.showMessageDialog(null, "Informe a data de realização","Informe",JOptionPane.ERROR_MESSAGE);
    		dpDataRealização.requestFocus();
    	} else if(tfsobre.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe sobre a tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		tfNome.requestFocus();
    	} else if(tfsobre.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe sobre a tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		tfsobre.requestFocus();
    	} else {
    		
    	}
    }

}
