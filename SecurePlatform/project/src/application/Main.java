package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try { 
            Parent login = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene loginScene = new Scene(login);
            loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(loginScene);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
