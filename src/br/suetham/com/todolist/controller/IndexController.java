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
    	limparCampos();
    	dpDataRealização.setDisable(false);
    	lbStatus.setVisible(false);
    	btAdiar.setDisable(true);
		btExcluir.setDisable(true);
		btConcluir.setDisable(true);
    }
    
    private List<Tarefa> tarefas;

    @FXML
    void cllqSalvar(ActionEvent event) {
// validação de campos
    	if(dpDataRealização.getValue() == null) {
    		JOptionPane.showMessageDialog(null, "Informe a data de realização","Informe",JOptionPane.ERROR_MESSAGE);
    		dpDataRealização.requestFocus();
    	} else if(tfTitulo.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe um título para tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		tfTitulo.requestFocus();
    	} else if(tfsobre.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe sobre a tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		tfsobre.requestFocus();
    	} else if(tfTitulo.getText().length() >=20) {
    		JOptionPane.showMessageDialog(null, "O titulo da tarefá deve ser menor que vinte caracteres", "Informe", JOptionPane.ERROR_MESSAGE);
    		tfsobre.requestFocus();
    	} else {
    		if(dpDataRealização.getValue().isBefore(LocalDate.now())) {
    			JOptionPane.showMessageDialog(null, "Ops essa data já passou informe uma data valida", "Informe", JOptionPane.ERROR_MESSAGE);
        		dpDataRealização.requestFocus();
        		
    		} else 
    			//Iniciando a tarefa
    			tarefa = new Tarefa();
    			//populando a tarefa
    			tarefa.setDataCriacao(LocalDate.now());
    			tarefa.setDataLimite(dpDataRealização.getValue());
    			tarefa.setTarefaNome(tfTitulo.getText());
    			tarefa.setComentario(tfsobre.getText());
    			tarefa.setStatus(StatusTarefa.ABERTA);
    			
    		    	
    			// se voce colocar TODO em comentario ele marca como algo para fazer
    			
    			//TODO 	salvar no banco de dados
    			try {
					TarefaIO.insert(tarefa);
					//Limpar os campos do formulário
	    			limparCampos();
	    			carregarTarefas();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Arquivo não encontrado"+e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Erro de I/O"+e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
    	}
    }
    
    private void limparCampos () {
    	tarefa = null;
    	tfTitulo.setText(" ");
    	tfsobre.setText(" ");
    	dpDataRealização.setValue(null);
    	imagvStatus.setImage(null);
    	TvTarefa.getSelectionModel().clearSelection();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	lbStatus.setVisible(false);
		//definir os parâmetros para as colunas do tableview
		TcData.setCellValueFactory(new PropertyValueFactory<>("dataLimite"));
		TcTarefa.setCellValueFactory(new PropertyValueFactory<>("tarefaNome"));
		TcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		//formatando a data na coluna
		TcData.setCellFactory(call -> {
			return new TableCell<Tarefa, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					// TODO Auto-generated method stub
					super.updateItem(item, empty);
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd / MMM / yyyy");
					if(!empty) {
						setText(item.format(fmt));
					}else {
							setText("");
						}
				}
			};
		});
				
//Substituido por um expressão lambida
//			@Override
//			public TableCell<Tarefa, LocalDate> call(TableColumn<Tarefa, LocalDate> param) {
//				// TODO Auto-generated method stub
//				return new TableCell<Tarefa, LocalDate>() {
//				@Override
//				protected void updateItem(LocalDate item, boolean empty) {
//					// TODO Auto-generated method stub
//					super.updateItem(item, empty);
//				}
				
		//evento de seleção de um item na TableView
		
		TvTarefa.getSelectionModel().selectedItemProperty().addListener(this);
		
		carregarTarefas();
	}
	
	private void carregarTarefas() {
		try {
			tarefas = TarefaIO.readTarefas();
			TvTarefa.setItems(FXCollections.observableArrayList(tarefas));
			//Atualizar a tarefa 
			TvTarefa.refresh();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Erro ao buscar as tarefas", "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	
			
	}
	//Interface ChangerLister adicionar esse metodo
	@Override
	public void changed(ObservableValue<? extends Tarefa> observable, Tarefa oldValue, Tarefa newValue) {
		//passar a referencia da tarefa local para a tarefa global
		tarefa = newValue;
		//se houver uma tarefa selecionada
		if(tarefa != null) {
			tfTitulo.setText(tarefa.getTarefaNome());
			tfsobre.setText(tarefa.getComentario());
			dpDataRealização.setValue(tarefa.getDataLimite());
			if(tarefa.getStatus() == StatusTarefa.ABERTA) {
				lbStatus.setText("Aberta");
				lbStatus.setVisible(true);
				Image Open = new Image("file:///C:/Users/TecDevTarde/eclipse-workspace/todolist/src/br/suetham/com/todolist/imagens/caneta.png");
    			imagvStatus.setImage(Open);
			} else if (tarefa.getStatus() == StatusTarefa.ADIADA) {
				lbStatus.setText("Adiada");
				lbStatus.setVisible(true);
    			Image adiada = new Image("file:///C:/Users/TecDevTarde/eclipse-workspace/todolist/src/br/suetham/com/todolist/imagens/projeto.png");
    	    	imagvStatus.setImage(adiada);
    		} else if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
    			lbStatus.setText("Concluida");
    			lbStatus.setVisible(true);
    			Image finished = new Image("file:///C:/Users/TecDevTarde/eclipse-workspace/todolist/src/br/suetham/com/todolist/imagens/carraca.png");
    	    	imagvStatus.setImage(finished);
			}
			dpDataRealização.setDisable(true);
			btAdiar.setDisable(false);
			btExcluir.setDisable(false);
			btConcluir.setDisable(false);
		}
	}
}


	

