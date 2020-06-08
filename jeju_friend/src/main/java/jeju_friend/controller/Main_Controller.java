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
    @FXML
    private Button setUpBtn;
    // 이벤트 핸들러

    @FXML
    public void searchField_Typed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
            lookUp();
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
        moveToWeather();
    }
    @FXML
    public void userEditBtn_Actioned() throws IOException {
        moveToUserEdit();
    }
    // 로직

    public void lookUp()
    {
        
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/AddTravel.fxml"));
        Parent root = loader.load();
        AddTravel_Controller controller = loader.getController();
        addTravelBtn.getScene().setRoot(root);
    }
    
    public void moveToWeather() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/weatherView.fxml"));
        Parent root = loader.load();
        Weather_Controller weatherController = loader.getController();
        weatherBtn.getScene().setRoot(root);
    }

    public void moveToUserEdit() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/UserEdit.fxml"));
        Parent root = loader.load();
        UserEdit_Controller controller = loader.getController();
        setUpBtn.getScene().setRoot(root); 
        controller.enter();
    }
}