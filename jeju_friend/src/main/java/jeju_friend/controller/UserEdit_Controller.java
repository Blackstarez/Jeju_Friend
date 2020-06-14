package jeju_friend.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import jeju_friend.Elements.Protocol;
import jeju_friend.Elements.TourPlan;
import jeju_friend.Elements.UserInfo;
import jeju_friend.application.SocketHandler;

public class UserEdit_Controller {

    @FXML
    Button travelEditBtn;
    @FXML
    Button addTravelBtn;
    @FXML
	Button traveldeleteBtn;
	@FXML
	Button cancelBtn;
	@FXML
	Button saveBtn;

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

	@FXML
	private TextArea nameArea;

	@FXML
	private GridPane travelGridPane;

	private String userName;

	private int interestArea;

	private ToggleButton[] group = new ToggleButton[10];
	
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
	
	public void enter() throws InterruptedException, ExecutionException {
		UserInfo user = getUserInfo();
		TourPlan[] tourList = getTourList();
		userName = user.getNickName();
		interestArea = user.getInterestArea();
		nameArea.setText(userName);
		setToggle(interestArea);
		setTourBox(travelGridPane, tourList);
	}

	// 이벤트 핸들러

	
	private TourPlan[] getTourList() throws InterruptedException, ExecutionException {

		Task<TourPlan[]> task = new Task<TourPlan[]>() {

			@Override
			protected TourPlan[] call() throws Exception {

				Protocol protocol = new Protocol();
				Protocol resultProtocol = new Protocol();

				protocol.setPacket(Protocol.PT_REQUEST, Protocol.PT_TOURPLAN, Protocol.PT_LOOKUP, Protocol.PT_USER);
				SocketHandler socketHandler = new SocketHandler();
				try {
					resultProtocol = socketHandler.request(protocol);
				} catch (Exception e) {
					e.printStackTrace();
				}
				TourPlan[] list = TourPlan.toTourPlanList(resultProtocol.getBody());
				return list;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
		return task.get();
	}

	private UserInfo getUserInfo() throws InterruptedException, ExecutionException {
		Task<UserInfo> task = new Task<UserInfo>() {

			@Override
			protected UserInfo call() throws Exception {

				Protocol protocol = new Protocol();
				Protocol resultProtocol = new Protocol();

				protocol.setPacket(Protocol.PT_REQUEST, Protocol.PT_USERINFO, Protocol.PT_LOOKUP, Protocol.PT_USER);
				SocketHandler socketHandler = new SocketHandler();
				try {
					resultProtocol = socketHandler.request(protocol);
				} catch (Exception e) {
					e.printStackTrace();
				}
				UserInfo user = UserInfo.toUser(resultProtocol.getBody());
				return user;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
		return task.get();
	}

   
    public void travelDeleteBtn_Actioned()
    {
        
	}

	public void addTravelBtn_Actioned() throws IOException
    {
        moveToAddTravel();
    }
	public void cancelBtn_Actioned() throws IOException, InterruptedException, ExecutionException
	{
		moveToMain();
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
	private void regionBtn2_Actioned(){
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
    
	//로직
	
	
	private void setToggle(int interestArea) {

		switch(interestArea)
		{
			case 1:
			{
				regionBtn1.setSelected(true);
				view1.setImage(map1SelectedImage);
			}
			case 2:
			{
				regionBtn2.setSelected(true);
				view2.setImage(map2SelectedImage);
			}
			case 3:
			{
				regionBtn3.setSelected(true);
				view3.setImage(map3SelectedImage);
			}
			case 4:
			{
				regionBtn4.setSelected(true);
				view4.setImage(map4SelectedImage);
			}
			case 5:
			{
				regionBtn5.setSelected(true);
				view5.setImage(map5SelectedImage);
			}	
		}
	}

	public void setTourBox(GridPane travelGridPane, TourPlan[] tourList)
	{
		for(int index = 0; index<tourList.length; index++)
		{
			RowConstraints con = new RowConstraints();
			con.setPrefHeight(50);
            travelGridPane.getRowConstraints().add(con);
			VBox input = new VBox();
			TextArea tourPlanNameArea = new TextArea(tourList[index].getTourPlanName());
			//DatePicker tourDay = new DatePicker(tourList[index].getTourDay());
			input.getChildren().addAll(tourPlanNameArea);
			//input.getChildren().addAll(tourPlanNameArea,tourDay);
			travelGridPane.getChildren().add(input);
		}
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
		return 0;
	}
	public void moveToAddTravel() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/AddTravel.fxml"));
        Parent root = loader.load();
        AddTravel_Controller controller = loader.getController();
        addTravelBtn.getScene().setRoot(root);
	}
	
	public void moveToMain() throws IOException, InterruptedException, ExecutionException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/Main.fxml"));
        Parent root = loader.load();
        Main_Controller controller = loader.getController();
        cancelBtn.getScene().setRoot(root); 
        controller.lookUp();
    }

}