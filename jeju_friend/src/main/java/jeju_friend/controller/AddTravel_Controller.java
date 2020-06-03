package jeju_friend.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private ToggleButton parentBtn;
    @FXML
    private ToggleButton familyBtn;
    @FXML
    private ToggleButton friendBtn;
    @FXML
    private ToggleButton loverBtn;
    @FXML
    private ToggleButton aloneBtn;
    @FXML
    private ToggleButton familyTravelBtn;
    @FXML
    private ToggleButton parentTravelBtn;
    @FXML
    private ToggleButton loverTravelBtn;
    @FXML
    private ToggleButton friendTravelBtn;
    @FXML
    private ToggleButton tourismTravelBtn;
    @FXML
    private ToggleButton foodTravelBtn;
    @FXML
    private ToggleButton healingTravelBtn;
    @FXML
    private ToggleButton schoolTripBtn;

    ToggleButton [] tbArr = new ToggleButton[14];

    // 화면 처음 시작할 때 동작
    @FXML
    public void view()
    {
        tbArr[1] = parentBtn;
        tbArr[2] = familyBtn;
        tbArr[3] = friendBtn;
        tbArr[4] = loverBtn;
        tbArr[5] = aloneBtn;
        tbArr[6] = familyTravelBtn;
        tbArr[7] =  parentTravelBtn;
        tbArr[8] = loverTravelBtn;
        tbArr[9] = friendTravelBtn;
        tbArr[10] = tourismTravelBtn;
        tbArr[11] = foodTravelBtn;
        tbArr[12] = healingTravelBtn;
        tbArr[13] = schoolTripBtn;
    }

    // 이벤트 핸들러
    @FXML
    public void cancelBtn_Actioned() throws IOException {
        moveToMain();
    }
    @FXML
    public void addTravelBtn_Actioned() throws IOException {
        addTravel();
    }
    
    @FXML
    public void parentBtn_Actioned() 
    {
        partnerBtn_Actioned(parentBtn);
    }
    @FXML
    public void familyBtn_Actioned()
    {
        partnerBtn_Actioned(familyBtn);
    }
    @FXML
    public void friendBtn_Actioned()
    {
        partnerBtn_Actioned(friendBtn);
    }
    @FXML
    public void loverBtn_Actioned()
    {
        partnerBtn_Actioned(loverBtn);
    }
    @FXML
    public void aloneBtn_Actioned()
    {
        partnerBtn_Actioned(aloneBtn);
    }

    @FXML
    public void familyTravelBtn_Actioned()
    {
        travelChooseBtn_Actioned(familyTravelBtn);
    }
    @FXML
    public void parentTravelBtn_Actioned()
    {
        travelChooseBtn_Actioned(parentTravelBtn);
    }
    @FXML
    public void loverTravelBtn_Actioned()
    {
        travelChooseBtn_Actioned(loverTravelBtn);
    }
    @FXML
    public void friendTravelBtn_Actioned()
    {
        travelChooseBtn_Actioned(friendTravelBtn);
    }
    @FXML
    public void tourismTravelBtn_Actioned()
    {
        travelChooseBtn_Actioned(familyTravelBtn);
    }
    @FXML
    public void foodTravelBtn_Actioned()
    {
        travelChooseBtn_Actioned(foodTravelBtn);
    }
    @FXML
    public void healingTravelBtn_Actioned()
    {
        travelChooseBtn_Actioned(healingTravelBtn);
    }
    @FXML
    public void schoolTripBtn_Actioned()
    {
        travelChooseBtn_Actioned(schoolTripBtn);
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
        int partner = getPartner();
        int travel = getTravel();
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
        else if(partner == 0)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("여행 생성 오류");
			alert.setHeaderText(null);
			alert.setContentText("누구와 가는지 선택 해주세요");
			alert.showAndWait();
            return;
        }
        else if (travel == 0)
        {
            Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("여행 생성 오류");
			alert.setHeaderText(null);
			alert.setContentText("어떤 여행인지 선택 해주세요");
			alert.showAndWait();
			nameField.requestFocus();
            return;
        }   
        else
        {
            System.out.println(inputName+ partner+ travel);
            moveToMain();
        }            
    }

    private void partnerBtn_Actioned(ToggleButton tButton)
    {
        if (tButton.isSelected())
        {
            for(int i = 1; i <6; i++)
            {
                tbArr[i].setSelected(false);
                tbArr[i].setStyle("-fx-background-color:  rgb(127, 198, 135)");
            }
            tButton.setSelected(true);
            tButton.setStyle("-fx-background-color: #369E77");
        }
        else
        {
            tButton.setStyle("-fx-background-color:  rgb(127, 198, 135)");
        } 
    }
    private void travelChooseBtn_Actioned(ToggleButton tButton)
    {
        if (tButton.isSelected())
        {
            for(int i = 6; i<14;i++)
            {
                tbArr[i].setSelected(false);
                tbArr[i].setStyle("-fx-background-color:  rgb(52, 152, 219)");
            }
            tButton.setSelected(true);
            tButton.setStyle("-fx-background-color: #4A729E");
        }
        else
        {
            tButton.setStyle("-fx-background-color:  rgb(52, 152, 219)");
        }
    }
    private int getPartner()
    {
        for(int i = 1; i <6; i++)
        {
            if (tbArr[i].isSelected())
            return i;
        }
        return 0;
    }
    private int getTravel()
    {
        for(int i = 6; i <14; i++)
        {
            if (tbArr[i].isSelected())
            return i;
        }
        return 0;
    }
}