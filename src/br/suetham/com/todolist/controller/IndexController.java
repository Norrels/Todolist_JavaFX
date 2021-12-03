package br.suetham.com.todolist.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import br.suetham.com.todolist.io.TarefaIO;
import br.suetham.com.todolist.model.StatusTarefa;
import br.suetham.com.todolist.model.Tarefa;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.scene.image.Image;

public class IndexController implements Initializable, ChangeListener<Tarefa> {

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
	private DatePicker dpDataRealização;

	@FXML
	private ImageView imagvStatus;

	@FXML
	private TableView<Tarefa> TvTarefa;

	@FXML
	private TableColumn<Tarefa, LocalDate> TcData;

	@FXML
	private TableColumn<Tarefa, String> TcTarefa;

	@FXML
	private TableColumn<Tarefa, String> TcStatus;

	@FXML
	private Label lbStatus;

	@FXML
	private Label lbData;

	@FXML
	private TextField TfId;

	@FXML
	private Label lbcodigo;

	@FXML
	void cliqAdiar(ActionEvent event) {
		if (tarefa != null) {
			int dias = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantos dias você deseja adiar?",
					"Informe quantos dias", JOptionPane.QUESTION_MESSAGE));

			if (dias >= 1) {
				LocalDate novaData = tarefa.getDataLimite().plusDays(dias);
				tarefa.setDataLimite(novaData);
				tarefa.setStatus(StatusTarefa.ADIADA);
				try {
					TarefaIO.AtualizaTarefas(tarefas);
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					JOptionPane.showMessageDialog(null,
							"Tarefa adiada para " + tarefa.getDataLimite().format(fmt) + " com sucesso");
					carregarTarefas();
					limparCampos();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar a tarefa", "Erro",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Informe um numero maior que 0");
			}
		}
	}

	@FXML
	void cliqConcluir(ActionEvent event) {
		try {
			tarefa.setStatus(StatusTarefa.CONCLUIDA);
			tarefa.setDataFinalizada(LocalDate.now());
			TarefaIO.AtualizaTarefas(tarefas);
			JOptionPane.showMessageDialog(null, "Tarefa conluida com sucesso, Parabéns");
			carregarTarefas();
			limparCampos();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao concluir a tarefa", "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@FXML
	void cliqExcluir(ActionEvent event) {
		if (tarefa != null) {
			int resposta = JOptionPane.showConfirmDialog(null,
					"Deseja realmente excluir a tarefa " + tarefa.getId() + "?", "Confirmar exclusão",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) {
				tarefas.remove(tarefa);
				try {
					TarefaIO.AtualizaTarefas(tarefas);
					limparCampos();
					carregarTarefas();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao exluir a tarefa", "Erro",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	void cliqLimpar(ActionEvent event) {
		limparCampos();
		dpDataRealização.setDisable(false);
		lbStatus.setVisible(false);
		TfId.setVisible(false);
		lbcodigo.setVisible(false);
	}

	private List<Tarefa> tarefas;

	@FXML
	void cllqSalvar(ActionEvent event) {
		// validação de campos
		if (dpDataRealização.getValue() == null) {
			JOptionPane.showMessageDialog(null, "Informe a data de realização", "Informe", JOptionPane.ERROR_MESSAGE);
			dpDataRealização.requestFocus();
		} else if (tfTitulo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe um título para tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
			tfTitulo.requestFocus();
		} else if (tfsobre.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe sobre a tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
			tfsobre.requestFocus();
		} else if (dpDataRealização.getValue().isBefore(LocalDate.now())) {
			JOptionPane.showMessageDialog(null, "Ops essa data já passou informe uma data valida", "Informe",
					JOptionPane.ERROR_MESSAGE);
			dpDataRealização.requestFocus();
		} else if (tfTitulo.getText().length() >= 40) {
			JOptionPane.showMessageDialog(null, "O titulo da tarefá deve ser menor que quarenta caracteres", "Informe",
					JOptionPane.ERROR_MESSAGE);
			tfTitulo.requestFocus();
		} else if (tfsobre.getText().length() >= 200) {
			JOptionPane.showMessageDialog(null, "A tarefa atingiu o limite de caracteres", "Informe",
					JOptionPane.ERROR_MESSAGE);
			tfsobre.requestFocus();

		} else
		// Iniciando a tarefa
		if (tarefa == null) {
			tarefa = new Tarefa();
			tarefa.setDataCriacao(LocalDate.now());
			tarefa.setStatus(StatusTarefa.ABERTA);
		}
		// populando a tarefa
		tarefa.setDataLimite(dpDataRealização.getValue());
		tarefa.setTarefaNome(tfTitulo.getText());
		tarefa.setComentario(tfsobre.getText());

		// se voce colocar TODO em comentario ele marca como algo para fazer

		// salvar no banco de dados
		try {
			if (tarefa.getId() == 0) {
				TarefaIO.insert(tarefa);
			} else {
				TarefaIO.AtualizaTarefas(tarefas);
			}
			// Limpar os campos do formulário
			limparCampos();
			carregarTarefas();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Arquivo não encontrado" + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro de I/O" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void limparCampos() {
		tarefa = null;
		tfTitulo.setText(null);
		tfsobre.setText(null);
		dpDataRealização.setValue(null);
		imagvStatus.setImage(null);
		TvTarefa.getSelectionModel().clearSelection();
		TfId.setText(null);
		lbData.setText("Data para realização");
		tfTitulo.setEditable(true);
		tfsobre.setEditable(true);
		btSalvar.setDisable(false);
		dpDataRealização.setDisable(false);
		lbStatus.setVisible(false);
		TfId.setVisible(false);
		lbcodigo.setVisible(false);
		btAdiar.setDisable(true);
		btExcluir.setDisable(true);
		btConcluir.setDisable(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lbStatus.setVisible(false);
		// definir os parâmetros para as colunas do tableview
		TcData.setCellValueFactory(new PropertyValueFactory<>("dataLimite"));
		TcTarefa.setCellValueFactory(new PropertyValueFactory<>("tarefaNome"));
		TcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		// formatando a data na coluna
		TcData.setCellFactory(call -> {
			return new TableCell<Tarefa, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					// TODO Auto-generated method stub
					super.updateItem(item, empty);
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd / MMM / yyyy");
					if (!empty) {
						setText(item.format(fmt));
					} else {
						setText("");
					}
				}
			};
		});

//		Substituido pela expressão lambida acima
//			@Override
//			public TableCell<Tarefa, LocalDate> call(TableColumn<Tarefa, LocalDate> param) {
//				// TODO Auto-generated method stub
//				return new TableCell<Tarefa, LocalDate>() {
//				@Override
//				protected void updateItem(LocalDate item, boolean empty) {
//					// TODO Auto-generated method stub
//					super.updateItem(item, empty);
//				}

		// evento de seleção de um item na TableView
		
		TvTarefa.setRowFactory(call -> new TableRow<Tarefa>() {
			protected void updateItem(Tarefa item, boolean empty) {
				super.updateItem(item,empty);
				if(item == null) {
					setStyle("");
				}else if(item.getStatus() == StatusTarefa.CONCLUIDA) {
					setStyle("-fx-background-color: #e3e0f8");
				}else if(item.getDataLimite().isBefore(LocalDate.now())) {
					setStyle("-fx-background-color:#e7c4cf");
				}else if(item.getStatus() == StatusTarefa.ADIADA) {
					setStyle("-fx-background-color: #f5e5df");
				}else
					setStyle("");
			};
		});
		TvTarefa.getSelectionModel().selectedItemProperty().addListener(this);
		carregarTarefas();
	}

	private void carregarTarefas() {
		try {
			tarefas = TarefaIO.readTarefas();
			TvTarefa.setItems(FXCollections.observableArrayList(tarefas));
			// Atualizar a tarefa
			TvTarefa.refresh();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar as tarefas", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// Interface ChangerLister adicionar esse metodo
	@Override
	public void changed(ObservableValue<? extends Tarefa> observable, Tarefa oldValue, Tarefa newValue) {

		// passar a referencia da tarefa local para a tarefa global
		tarefa = newValue;

		// se houver uma tarefa selecionada
		if (tarefa != null) {
			TfId.setVisible(true);
			lbcodigo.setVisible(true);
			tfTitulo.setText(tarefa.getTarefaNome());
			tfsobre.setText(tarefa.getComentario());
			dpDataRealização.setValue(tarefa.getDataLimite());
			TfId.setText(tarefa.getId() + "");
			dpDataRealização.setOpacity(1);

			if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
				lbData.setText("Data de conclusão");
				lbStatus.setText("Concluída");
				lbStatus.setVisible(true);
				tfTitulo.setEditable(false);
				tfsobre.setEditable(false);
				btAdiar.setDisable(true);
				btSalvar.setDisable(true);
				btConcluir.setDisable(true);
				btExcluir.setDisable(false);
				dpDataRealização.setValue(tarefa.getDataFinalizada());
				Image finished = new Image(getClass().getResourceAsStream("../imagens/carraca.png"));
				imagvStatus.setImage(finished);

			} else {
				lbStatus.setVisible(true);
				btAdiar.setDisable(false);
				btExcluir.setDisable(false);
				btConcluir.setDisable(false);
				btSalvar.setDisable(false);
				lbData.setText("Data para realização");
				tfTitulo.setEditable(true);
				tfsobre.setEditable(true);

			}
			if (tarefa.getStatus() == StatusTarefa.ABERTA) {
				lbStatus.setText("Aberta");
				Image Open = new Image(getClass().getResourceAsStream("../imagens/caneta.png"));
				imagvStatus.setImage(Open);

			}
			if (tarefa.getStatus() == StatusTarefa.ADIADA) {
				lbStatus.setText("Adiada");
				Image adiada = new Image(getClass().getResourceAsStream("../imagens/projeto.png"));
				imagvStatus.setImage(adiada);
			}
			dpDataRealização.setDisable(true);
		}
	}
}
