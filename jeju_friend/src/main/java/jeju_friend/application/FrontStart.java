package jeju_friend.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrontStart extends Application{

	public void start(Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Login.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("제주 친구");
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.show();
		
	}
	public static void main(String[] args) 
	{
		launch(args);
	}

}
