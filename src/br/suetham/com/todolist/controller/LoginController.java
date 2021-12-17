package br.suetham.com.todolist.controller;

import java.io.IOException;
import br.suetham.com.todolist.io.UsuarioIO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField tfUser;

	@FXML
	private PasswordField tfSenha;

	@FXML
	private Button btCadastre;

	@FXML
	private Label lbWrongSenha;

	@FXML
	private Label lbWrongUser;

	@FXML
	void cliqCadastre() {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader
			.load(getClass().getResource("/br/suetham/com/todolist/view/Cadastro.fxml"));
			Scene scene = new Scene(root, 403, 377);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Index");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void cliqLogin() throws IOException {
		if (UsuarioIO.logar(tfUser.getText(), tfSenha.getText())) {
			lbWrongUser.setVisible(false);
			lbWrongSenha.setVisible(false);

			Stage stage = (Stage) btCadastre.getScene().getWindow();
			stage.close();

			try {
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/br/suetham/com/todolist/view/Index.fxml"));
				Scene scene = new Scene(root, 1071, 589);
				stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Index");
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!tfUser.getText().equals("admin")) {
			lbWrongUser.setVisible(true);
			tfUser.requestFocus();
		} else {
			lbWrongUser.setVisible(false);
		}

		if (!tfSenha.getText().equals("admin")) {
			lbWrongSenha.setVisible(true);
			tfSenha.requestFocus();
		} else {
			lbWrongSenha.setVisible(false);
		}
	}
}
