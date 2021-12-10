package br.suetham.com.todolist.controller;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AboutController {
	
	  @FXML
	    private Button btEntendido;

	    @FXML
	    void clikEntendido() {
	    	Stage stage = (Stage) btEntendido.getScene().getWindow();
	    	stage.close();
	    }

}
