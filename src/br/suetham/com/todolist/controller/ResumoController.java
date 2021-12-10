package br.suetham.com.todolist.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import br.suetham.com.todolist.io.TarefaIO;
import br.suetham.com.todolist.model.StatusTarefa;
import br.suetham.com.todolist.model.Tarefa;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ResumoController implements Initializable {
	@FXML
	private TextArea infoAbertas;

	@FXML
	private TextArea infoConcluidas;

	@FXML
	private TextArea infoAtrasadas;

	@FXML
	private TextArea infoAdiadas;

	private List<Tarefa> tarefas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int contopen = 1;
		int contadiada = 1;
		int contconcluida = 1;
		int contatrasada = 1;
		try {
			tarefas = TarefaIO.readTarefas();
			for (Tarefa tarefa : tarefas) {

				if (tarefa.getStatus() == StatusTarefa.ABERTA) {
					contopen++;
				} else if (tarefa.getStatus() == StatusTarefa.ADIADA) {
					contadiada++;
				} else if (tarefa.getStatus() == StatusTarefa.CONCLUIDA){
					contconcluida++;
				} else if (tarefa.getDataLimite().isAfter(LocalDate.now())) {
					contatrasada++;
				}
				
			}
			infoAbertas.setText(contopen + "");
			infoAdiadas.setText(contadiada + "");
			infoAtrasadas.setText(contatrasada + "");
			infoConcluidas.setText(contconcluida + "");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
