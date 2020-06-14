package jeju_friend.controller;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jeju_friend.Elements.Protocol;
import jeju_friend.Elements.TouristSpot;
import jeju_friend.Elements.UserInfo;
import jeju_friend.application.SocketHandler;

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
    private Button userEditBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private VBox vBox;
    @FXML
    private Pane mainPane;
    @FXML
    private Pane searchBar;
    @FXML
    private Label interestAreaLabel;
    TouristSpot[] tourList;
    TouristSpot[] foodList;
    // 이벤트 핸들러

    @FXML
    public void searchField_Typed(KeyEvent event) throws InterruptedException, ExecutionException {
        if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
            TouristSpot touristSpot = searchTouristSpot(searchField.getText());
            vBox.getChildren().clear();
            addVBox(touristSpot);
        }
        searchLabel.setVisible(false);
    }
    
    private TouristSpot searchTouristSpot(String text) {
        int index = 0;
        TouristSpot spot;
        while(index<=tourList.length)
        {
            if(tourList[index].getTouristSpot() == text)
            {
                spot = tourList[index];
                return spot;
            }
        }
        index = 0;
        while(index<=foodList.length)
        {
            if(foodList[index].getTouristSpot() == text)
            {
                spot = foodList[index];
                return spot;
            }
        }
        return null;
    }

    @FXML
    public void refreshBtn_Actioned() throws IOException, InterruptedException, ExecutionException {
        refresh();
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
    public void userEditBtn_Actioned() throws IOException, InterruptedException, ExecutionException {
        moveToUserEdit();
    }

    public void lookUp() throws InterruptedException, ExecutionException {
        UserInfo user = getUserInfo();
        tourList = getTouristSpotList();
        foodList = getFoodSpotList();
        TouristSpot tourSpot = getTouristSpot(tourList);
        TouristSpot foodSpot = getTouristSpot(foodList);
        String interestArea =  ToAreaName(user.getInterestArea());
        interestAreaLabel.setText("현재관심지역: " + interestArea);
        addVBox(tourSpot);
        addVBox(foodSpot);
    }


    // 로직


    private String ToAreaName(int interestArea) {
        String name = "";
        switch(interestArea)
        {
            case 1: name = "신나는 제주";
                    break;
            case 2: name = "익스트림 제주";
                    break;
            case 3: name = "열대 제주";
                    break;
            case 4: name = "힐링 제주";
                    break;
            case 5: name = "숲속 제주";
                    break;
        }
        return name;
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

    public TouristSpot[] getTouristSpotList() throws InterruptedException, ExecutionException 
    {
        Task<TouristSpot[]> task = new Task<TouristSpot[]>() {

			@Override
			protected TouristSpot[] call() throws Exception {

				Protocol protocol = new Protocol();
                Protocol resultProtocol = new Protocol();
                
                protocol.setPacket(Protocol.PT_REQUEST,Protocol.PT_TOURIST_SPOT, Protocol.PT_LOOKUP, Protocol.PT_USER);
                SocketHandler socketHandler = new SocketHandler();
                try {
                    resultProtocol = socketHandler.request(protocol);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TouristSpot[] list = TouristSpot.toTouristSpotList(resultProtocol.getBody());  
                return list; 
            }   
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return task.get();
    }

    public TouristSpot[] getFoodSpotList() throws InterruptedException, ExecutionException 
    {
        Task<TouristSpot[]> task = new Task<TouristSpot[]>() {

			@Override
			protected TouristSpot[] call() throws Exception {

				Protocol protocol = new Protocol();
                Protocol resultProtocol = new Protocol();
                
                protocol.setPacket(Protocol.PT_REQUEST,Protocol.PT_RESTAURANT, Protocol.PT_LOOKUP, Protocol.PT_USER);
                SocketHandler socketHandler = new SocketHandler();
                try {
                    resultProtocol = socketHandler.request(protocol);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                TouristSpot[] list = TouristSpot.toTouristSpotList(resultProtocol.getBody());  
                return list; 
            }   
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return task.get();
    }

    public TouristSpot getTouristSpot(TouristSpot[] list)
    {
        int max = list.length;
        int index;
        TouristSpot result;
        Random random = new Random(System.currentTimeMillis());
        index = random.nextInt(max);
        result = list[index];
        return result;
    }

    public void addVBox(TouristSpot tour)
    {
        Thread thread = new Thread() {
            @Override
            public void run() {                
                VBox newV = new VBox();
                Label tourName = new Label(tour.getTouristSpot());
                String imageSource = tour.getImageUrl();
                Image image = new Image(imageSource);
                ImageView tourImage = new ImageView(image);
                Platform.runLater(() -> {
                    mainPane.setLayoutY(searchBar.getLayoutY()+80);
                    //tourImage.setFitWidth(200);
                    //tourImage.setFitHeight(150);
                    newV.getChildren().addAll(tourName); //  newV.getChildren().addAll(tourName, tourImage);
                    if(tour.getContactInformation() != null)
                    {
                        Label text = new Label("연락처 : " + tour.getContactInformation());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getHomepage() != null)
                    {
                        Label text = new Label("홈페이지 : " + tour.getHomepage());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getWeekdayViewingTime() != null)
                    {
                        Label text = new Label("평일영업시간 : " + tour.getWeekdayViewingTime());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getHolidayViewingTime() != null)
                    {
                        Label text = new Label("휴일영업시간 : " + tour.getHolidayViewingTime());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getClosedInformation() != null)
                    {
                        Label text = new Label("휴일정보 : " + tour.getClosedInformation());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getChildAdmissionFee() != null)
                    {
                        Label text = new Label("어린이요금 : " + tour.getChildAdmissionFee());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getTeenagerAdmissionFee() != null)
                    {
                        Label text = new Label("청소년요금 : " + tour.getTeenagerAdmissionFee());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getAdultAdmissionFee()!= null)
                    {
                        Label text = new Label("성인요금 : " + tour.getAdultAdmissionFee());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getEtc() != null)
                    {
                        Label text = new Label("기타사항 : " + tour.getEtc());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getInformation() != null)
                    {
                        Label text = new Label("정보 : " + tour.getInformation());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    if(tour.getLocation() != null)
                    {
                        Label text = new Label("위치 : " + tour.getLocation());
                        text.setWrapText(true);
                        newV.getChildren().add(text);
                    }
                    vBox.getChildren().add(newV);
                });
            }
        };
        thread.setDaemon(true);
        thread.start();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/WeatherView.fxml"));
        Parent root = loader.load();
        Weather_Controller weatherController = loader.getController();
        weatherBtn.getScene().setRoot(root);
        weatherController.enter();
    }

    public void moveToUserEdit() throws IOException, InterruptedException, ExecutionException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/UserEdit.fxml"));
        Parent root = loader.load();
        UserEdit_Controller controller = loader.getController();
        userEditBtn.getScene().setRoot(root); 
        controller.enter();
    }

        
    private void refresh() throws IOException, InterruptedException, ExecutionException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/Main.fxml"));
        Parent root = loader.load();
        Main_Controller controller = loader.getController();
        userEditBtn.getScene().setRoot(root); 
        controller.lookUp();
    }

}