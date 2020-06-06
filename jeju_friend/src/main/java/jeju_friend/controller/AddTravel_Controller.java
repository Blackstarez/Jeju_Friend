package jeju_friend.controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddTravel_Controller {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button addTravelBtn;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker datePicker;

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
        final Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Main.fxml"));
        final Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show(); 
    }

    private void addTravel() throws IOException {
        String inputName = nameField.getText();
        LocalDate date = datePicker.getValue();
        // 선호 지역 골라야 함.
        if(inputName.isEmpty())
        {
            Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("여행 생성 오류");
			alert.setHeaderText(null);
			alert.setContentText("여행 이름을 입력해 주세요!");
			alert.showAndWait();
			nameField.requestFocus();
            return;
        }
        else if (date == null)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("여행 생성 오류");
			alert.setHeaderText(null);
			alert.setContentText("여행 시작일을 선택해 주세요!");
			alert.showAndWait();
			nameField.requestFocus();
            return;
        }
        else
        {
            moveToMain();
        }            
    }

}