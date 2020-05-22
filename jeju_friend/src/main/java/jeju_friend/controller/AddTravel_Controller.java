package jeju_friend.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class AddTravel_Controller {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button addTravelBtn;
    @FXML
    private TextField nameField;
    @FXML
    private ToggleButton parentBtn;
    @FXML
    private ToggleButton familyBtn;
    



    // 이벤트 핸들러
    @FXML
    public void cancelBtn_Actioned() throws IOException {
        moveToMain();
    }
    @FXML
    public void addTravelBtn_Actioned() throws IOException {
        addTravel();
    }

    

    // 로직
    private void moveToMain() throws IOException {
        final Stage primaryStage = (Stage) cancelBtn.getScene().getWindow();
        final Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/MainView.fxml"));
        final Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show(); 
    }

    private void addTravel() throws IOException {
        String inputName = nameField.getText();

    }
}