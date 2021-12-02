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
    private DatePicker dpDataRealiza��o;

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
    if(tarefa != null) {
    	int dias = Integer.parseInt(
    	JOptionPane.showInputDialog(null, "Quantos dias voc� deseja adiar?","Informe quantos dias",JOptionPane.QUESTION_MESSAGE));
    	
    	if(dias > 1) {
	    	LocalDate novaData = tarefa.getDataLimite().plusDays(dias);
	    	tarefa.setDataLimite(novaData);
	    	tarefa.setStatus(StatusTarefa.ADIADA);
	    	try {
				TarefaIO.AtualizaTarefas(tarefas);
				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				JOptionPane.showMessageDialog(null, "Tarefa adiada para " + tarefa.getDataLimite().format(fmt) + " com sucesso");
				carregarTarefas();
				limparCampos();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Ocorreu um erro ao atualizar a tarefa", "Erro",JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
		}
    	} else {
    		JOptionPane.showMessageDialog(null, "Informe um numero maior que 1 ou verifique se a tarefa j� est� atrasada");
    	}
    }
    }

    @FXML
    void cliqConcluir(ActionEvent event) {
    	tarefa.setStatus(StatusTarefa.CONCLUIDA);
    	tarefa.setDataFinalizada(LocalDate.now());
    	try {
			TarefaIO.AtualizaTarefas(tarefas);
			JOptionPane.showMessageDialog(null, "Tarefa conluida com sucesso, Parab�ns");
			carregarTarefas();
			limparCampos();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Ocorreu um erro ao concluir a tarefa", "Erro",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
    }

    @FXML
    void cliqExcluir(ActionEvent event) {
    }

    @FXML
    void cliqLimpar(ActionEvent event) {
    	limparCampos();
    	dpDataRealiza��o.setDisable(false);
    	lbStatus.setVisible(false);
    	btAdiar.setDisable(true);
		btExcluir.setDisable(true);
		btConcluir.setDisable(true);
		TfId.setVisible(false);
		lbcodigo.setVisible(false);
    }
    
    private List<Tarefa> tarefas;

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
    	} else if(tfTitulo.getText().length() >=30) {
    		JOptionPane.showMessageDialog(null, "O titulo da taref� deve ser menor que trinta caracteres", "Informe", JOptionPane.ERROR_MESSAGE);
    		tfsobre.requestFocus();
    	} else {
    		if(dpDataRealiza��o.getValue().isBefore(LocalDate.now())) {
    			JOptionPane.showMessageDialog(null, "Ops essa data j� passou informe uma data valida", "Informe", JOptionPane.ERROR_MESSAGE);
        		dpDataRealiza��o.requestFocus();
        		
    		} else 
    			//Iniciando a tarefa
    			tarefa = new Tarefa();
    			//populando a tarefa
    			tarefa.setDataCriacao(LocalDate.now());
    			tarefa.setDataLimite(dpDataRealiza��o.getValue());
    			tarefa.setTarefaNome(tfTitulo.getText());
    			tarefa.setComentario(tfsobre.getText());
    			tarefa.setStatus(StatusTarefa.ABERTA);
    			
    		    	
    			// se voce colocar TODO em comentario ele marca como algo para fazer
    			
    			//TODO 	salvar no banco de dados
    			try {
					TarefaIO.insert(tarefa);
					//Limpar os campos do formul�rio
	    			limparCampos();
	    			carregarTarefas();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Arquivo n�o encontrado"+e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
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
    	dpDataRealiza��o.setValue(null);
    	imagvStatus.setImage(null);
    	TvTarefa.getSelectionModel().clearSelection();
    	TfId.setText(null);
    	lbData.setText("Data para realiza��o");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	lbStatus.setVisible(false);
		//definir os par�metros para as colunas do tableview
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
				
//Substituido por um express�o lambida
//			@Override
//			public TableCell<Tarefa, LocalDate> call(TableColumn<Tarefa, LocalDate> param) {
//				// TODO Auto-generated method stub
//				return new TableCell<Tarefa, LocalDate>() {
//				@Override
//				protected void updateItem(LocalDate item, boolean empty) {
//					// TODO Auto-generated method stub
//					super.updateItem(item, empty);
//				}
				
		//evento de sele��o de um item na TableView
		
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
			TfId.setVisible(true);
			lbcodigo.setVisible(true);
			tfTitulo.setText(tarefa.getTarefaNome());
			tfsobre.setText(tarefa.getComentario());
			dpDataRealiza��o.setValue(tarefa.getDataLimite());
			TfId.setText(tarefa.getId()+"");
			dpDataRealiza��o.setOpacity(1);
			
			if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
    			lbData.setText("Data de conclus�o");
    			lbStatus.setText("Conclu�da");
    			lbStatus.setVisible(true);
    			tfTitulo.setEditable(false);
    			tfsobre.setEditable(false);
    			btAdiar.setDisable(true);
    			btSalvar.setDisable(true);
    			btConcluir.setDisable(true);
    			dpDataRealiza��o.setValue(tarefa.getDataFinalizada());
    			Image finished = new Image(getClass().getResourceAsStream("../imagens/carraca.png"));
    	    	imagvStatus.setImage(finished);

			} else {
				lbStatus.setVisible(true);
				btAdiar.setDisable(false);
				btExcluir.setDisable(false);
				btConcluir.setDisable(false);
				btSalvar.setDisable(false);
				lbData.setText("Data para realiza��o");
			
			}if(tarefa.getStatus() == StatusTarefa.ABERTA) {
				lbStatus.setText("Aberta");
				Image Open = new Image(getClass().getResourceAsStream("../imagens/caneta.png"));
    			imagvStatus.setImage(Open);
    			
			}if (tarefa.getStatus() == StatusTarefa.ADIADA) {
				lbStatus.setText("Adiada");
    			Image adiada = new Image(getClass().getResourceAsStream("../imagens/projeto.png"));
    	    	imagvStatus.setImage(adiada);
    		} 
			dpDataRealiza��o.setDisable(true);
			
		}
	
	}
}


	

