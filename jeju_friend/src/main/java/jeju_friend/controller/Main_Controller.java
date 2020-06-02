package jeju_friend.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main_Controller {

    @FXML
    private Label searchLabel;
    @FXML
    private TextField searchField;
    @FXML
    private Button addTravelBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button weatherBtn;
    // 이벤트 핸들러

    @FXML
    public void searchField_Typed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
            // 검색창으로
        }
        searchLabel.setVisible(false);
    }

    @FXML
    public void searchField_Clicked(MouseEvent event) {
        searchLabel.setVisible(false);
    }

    @FXML
    public void addTravelBtn_Actioned() throws IOException {
        moveToAddTrevel();
    }

    @FXML
    public void logoutBtn_Actioned() throws IOException {
        moveToLogin();
    }

    @FXML
    public void weatherBtn_Actioned() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/weatherView.fxml"));
        Parent root = loader.load();
        Weather_Controller weatherController = loader.getController();
        weatherBtn.getScene().setRoot(root);
    }
    // 로직

    public void moveToSearch() {

    }

    public void moveToLogin() throws IOException
    {
        Stage primaryStage = (Stage) logoutBtn.getScene().getWindow(); 
        Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Login.fxml"));
        Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show();   
    }
    public void moveToAddTrevel() throws IOException
    {
        Stage primaryStage = (Stage) addTravelBtn.getScene().getWindow(); 
        Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/AddTravel.fxml"));
        Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show(); 
    }

}