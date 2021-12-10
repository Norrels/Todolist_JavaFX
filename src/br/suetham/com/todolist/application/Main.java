package br.suetham.com.todolist.application;
	
import br.suetham.com.todolist.io.TarefaIO;
import br.suetham.com.todolist.model.Tarefa;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TarefaIO.createFiles();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/br/suetham/com/todolist/view/Index.fxml"));
			Scene scene = new Scene(root,1071,589);
			scene.getStylesheets().add(getClass().getResource("/br/suetham/com/todolist/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Suetham");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/br/suetham/com/todolist/imagens/caderno.png")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
