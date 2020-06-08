package jeju_friend.controller;

import static java.util.Calendar.getInstance;

import java.io.IOException;
import java.util.Calendar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Weather_Controller {
    @FXML
    private Label dateLabel1;

    @FXML
    private Label dateLabel2;

    @FXML
    private Label dateLabel3;

    @FXML
    private Button backBtn;


    // 이벤트 처리
    @FXML
    public void backBtn_Actioned() throws IOException {
        moveToMain();
    }

    // 로직

    public void moveToMain() throws IOException
    {
        final Stage primaryStage = (Stage) backBtn.getScene().getWindow();
        final Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Main.fxml"));
        final Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show(); 
    }
}