package jeju_friend.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import jeju_friend.Elements.Protocol;
import jeju_friend.Elements.TourPlan;
import jeju_friend.Elements.UserInfo;
import jeju_friend.application.SocketHandler;

public class AddTravel_Controller {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button addTravelBtn;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ToggleButton groupBtn;
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

    @FXML
	private ToggleButton regionBtn1;

	@FXML
	private ToggleButton regionBtn2;

	@FXML
	private ToggleButton regionBtn3;

	@FXML
	private ToggleButton regionBtn4;

	@FXML
	private ToggleButton regionBtn5;

	@FXML
	private ImageView view1;
	
	@FXML
	private ImageView view2;

	@FXML
	private ImageView view3;

	@FXML
	private ImageView view4;

	@FXML
	private ImageView view5;

	@FXML
	private ToggleGroup regionGroup;

    Image map1SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_1_selected.png"));
	Image map1UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_1.png"));
	Image map2SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_2_selected.png"));
	Image map2UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_2.png"));
	Image map3SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_3_selected.png"));
	Image map3UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_3.png"));
	Image map4SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_4_selected.png"));
	Image map4UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_4.png"));
	Image map5SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_5_selected.png"));
    Image map5UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_5.png"));
    
    ToggleButton [] tbArr = new ToggleButton[14];

    UserInfo user = new UserInfo();

    public void enter(String id)
    {
        final DatePicker datePicker = new DatePicker();
        user.setId(id);
        setArr();
    }


    
    // 이벤트 핸들러
    @FXML
    public void cancelBtn_Actioned() throws IOException, InterruptedException, ExecutionException {
        moveToMain();
    }

    @FXML
    public void addTravelBtn_Actioned() throws IOException, InterruptedException, ExecutionException {
        checkValid();
    }
    @FXML
    public void groupBtn_Actioned() 
    {
        partnerBtn_Actioned(groupBtn);
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
    
	@FXML
	private void regionBtn1_Actioned(){

		regionBtn1.setToggleGroup(regionGroup);
		if(regionBtn1.isSelected())
		{
			view1.setImage(map1SelectedImage);
			view2.setImage(map2UnselectedImage);
			view3.setImage(map3UnselectedImage);
			view4.setImage(map4UnselectedImage);
			view5.setImage(map5UnselectedImage);
		}	
		else
			view1.setImage(map1UnselectedImage);
	}
	@FXML
	private void regionBtn2_Actioned()
	{
		regionBtn2.setToggleGroup(regionGroup);
		if(regionBtn2.isSelected())
		{
			view1.setImage(map1UnselectedImage);
			view2.setImage(map2SelectedImage);
			view3.setImage(map3UnselectedImage);
			view4.setImage(map4UnselectedImage);
			view5.setImage(map5UnselectedImage);
		}		
		else
			view2.setImage(map2UnselectedImage);
	}
	@FXML
	private void regionBtn3_Actioned(){
		regionBtn3.setToggleGroup(regionGroup);
		if(regionBtn3.isSelected())
		{
			view1.setImage(map1UnselectedImage);
			view2.setImage(map2UnselectedImage);
			view3.setImage(map3SelectedImage);
			view4.setImage(map4UnselectedImage);
			view5.setImage(map5UnselectedImage);
		}			
		else
			view3.setImage(map3UnselectedImage);
	}
	@FXML
	private void regionBtn4_Actioned(){
		regionBtn4.setToggleGroup(regionGroup);
		if(regionBtn4.isSelected())
		{
			view1.setImage(map1UnselectedImage);
			view2.setImage(map2UnselectedImage);
			view3.setImage(map3UnselectedImage);
			view4.setImage(map4SelectedImage);
			view5.setImage(map5UnselectedImage);
		}
		else
			view4.setImage(map4UnselectedImage);
	}
	@FXML
	private void regionBtn5_Actioned(){
		regionBtn5.setToggleGroup(regionGroup);
		if(regionBtn5.isSelected())
		{
			view1.setImage(map1UnselectedImage);
			view2.setImage(map2UnselectedImage);
			view3.setImage(map3UnselectedImage);
			view4.setImage(map4UnselectedImage);
			view5.setImage(map5SelectedImage);
		}
		else
			view5.setImage(map5UnselectedImage);
    }
    
    // 로직

  

    public void checkValid() throws IOException, InterruptedException, ExecutionException {
        String inputName = nameField.getText();
        LocalDate tourDay = datePicker.getValue();
        int region = getSelectedRegion();
        int partner = getPartner();
        int travel = getTravel();

        if (inputName.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("여행 생성 오류");
            alert.setHeaderText(null);
            alert.setContentText("여행 이름을 입력해 주세요!");
            alert.showAndWait();
            nameField.requestFocus();
            return;
        } else if (datePicker.getValue() == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("여행 생성 오류");
            alert.setHeaderText(null);
            alert.setContentText("여행 시작일을 선택해 주세요!");
            alert.showAndWait();
            nameField.requestFocus();
            return;
        }else if(partner == 0)
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
         else {
            TourPlan tourPlan = new TourPlan();
            tourPlan.setTourPlanName(inputName);
            tourPlan.setTourDay(tourDay);
            tourPlan.setUserId(user.getId());
            tourPlan.setTourForm(getTravelName(travel));
            tourPlan.setTourWith(partner);
            tourPlan.setAreaInterest(region);
            addTravel(tourPlan);
        }
    }

    public void addTravel(TourPlan tourPlan) throws IOException, InterruptedException, ExecutionException {
        Protocol protocol = new Protocol();
        Protocol resultProtocol = new Protocol();
        protocol.setPacket(Protocol.PT_REQUEST, Protocol.PT_TOURPLAN, Protocol.PT_APPLY, Protocol.PT_USER,
                tourPlan.toBytes());
        SocketHandler socketHandler = new SocketHandler();
        try {
            resultProtocol = socketHandler.request(protocol);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 여행 추가 성공 여부 체크
        if (resultProtocol.getProtocolCodeExpansion() == Protocol.PT_SUCCESS) {
            moveToMain();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("여행추가 오류");
            alert.setHeaderText(null);
            alert.setContentText("다시 시도해 주세요!");
            alert.showAndWait();
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

    private String getTravelName(int index)
    {
        String name = "";
        switch(index)
        {
            case 1: name = "가족여행";
            break;
            case 2: name = "커플여행";
            break;
            case 3: name = "우정여행";
            break;
            case 4: name = "관광여행";
            break;
            case 5: name = "식도락여행";
            break;
            case 6: name = "힐링여행";
            break;
            case 7: name = "수학여행";
        }
        return name;
    }

    public int getSelectedRegion() {
		
		if(regionBtn1.isSelected())
			return 1;
		else if(regionBtn2.isSelected())
			return 2;
		else if(regionBtn3.isSelected())
			return 3;
		else if(regionBtn4.isSelected())
			return 4;
		else if(regionBtn5.isSelected())
			return 5;
		else
		return 0;
    }
    private void setArr() {
        tbArr[1] = familyBtn;
        tbArr[2] = friendBtn;
        tbArr[3] = loverBtn;
        tbArr[4] = groupBtn;
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

    public void moveToMain() throws IOException, InterruptedException, ExecutionException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/Main.fxml"));
        Parent root = loader.load();
        Main_Controller controller = loader.getController();
        cancelBtn.getScene().setRoot(root); 
        controller.enter(user.getId());
    }
}