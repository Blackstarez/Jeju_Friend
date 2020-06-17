package jeju_friend.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jeju_friend.Elements.LocationRecommend;
import jeju_friend.Elements.Protocol;
import jeju_friend.Elements.TourPlan;
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
    private Pane searchBar;
    @FXML
    private Label interestAreaLabel;
    @FXML
    private VBox travelView;
    @FXML
    private Button tourSpotViewBtn;
    @FXML
    private Button foodViewBtn;
    @FXML
    private Button recommendBtn;

    TouristSpot[] tourList;
    TouristSpot[] foodList;
    TourPlan[] planList;
    HashMap<String,Integer> searchTourTable;
    HashMap<String,Integer> searchFoodTable;
    HashMap<String,Object> searchWordFoodTable;
    HashMap<String,Object> searchWordTourTable;
    
    UserInfo user = new UserInfo();

    Image defaultImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/defaultImage.png"));

    LocationRecommend recommend = new LocationRecommend();

    public void enter(String id) throws InterruptedException, ExecutionException {
        user.setId(id);
        user = getUserInfo();
        tourList = getTouristSpotList();
        foodList = getFoodSpotList();
        planList = getTourPlanList();
        TouristSpot tourSpot = getTouristSpot(tourList);
        TouristSpot foodSpot = getTouristSpot(foodList);
        String interestArea =  ToAreaName(user.getInterestArea());
        interestAreaLabel.setText("현재관심지역: " + interestArea);
        recommend.setAge(user.getAge());
        recommend.setMale(user.getGender());
        addVBox(tourSpot);
        addVBox(foodSpot);
        setTravelView(planList);

        searchTourTable = new HashMap<String,Integer>();
        searchFoodTable = new HashMap<String,Integer>();
        for(int i=0;i<tourList.length;i++)
        {
            searchTourTable.put(tourList[i].getTouristSpot(),i);
        }
        for(int i = 0;i<foodList.length;i++)
        {
            searchFoodTable.put(foodList[i].getTouristSpot(),i);
        }

        makeTourSpotSearchTable();
        makeFoodSearchTable();
    }


    // 이벤트 핸들러

    

    @FXML
    public void searchField_Typed(KeyEvent event) throws InterruptedException, ExecutionException {
        if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
            ArrayList<TouristSpot> touristSpot = searchTouristSpot(searchField.getText());
            if(touristSpot.size() != 0)
            {
                vBox.getChildren().clear();
                for(TouristSpot t : touristSpot)
                    addVBox(t);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("검색 완료");
                alert.setHeaderText(null);
                alert.setContentText("검색을 완료했습니다.");
                alert.showAndWait();
                searchField.requestFocus();
            }
            else
            {
                Alert alert = new Alert(AlertType.INFORMATION);
			    alert.setTitle("검색 오류");
			    alert.setHeaderText(null);
    			alert.setContentText("검색 결과가 없습니다\n정확한 명칭을 입력해주세요");
	    		alert.showAndWait();
		    	searchField.requestFocus();
            }
        }
        searchLabel.setVisible(false);
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
        moveToAddTravel();
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

   @FXML
   public void foodViewBtn_Actioned() throws IOException, InterruptedException, ExecutionException
   {
       moveToFoodView();
   }

  

   @FXML
   public void tourSpotViewBtn_Actioned() throws IOException, InterruptedException, ExecutionException
   {
       moveToTourSpotView();
   }
    
    @FXML
    public void recommendBtn_Actioned() throws InterruptedException, ExecutionException {
        LocationRecommend recommend = getLocationRecommend();
        Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("지역 추천");
			alert.setHeaderText(null);
			alert.setContentText(user.getNickName()+"님에게 추천되는 장소는 「"+recommend.getLocationName()+"」 입니다");
			alert.showAndWait();
    }
    // 로직

    private void makeFoodSearchTable() throws InterruptedException, ExecutionException {
        Task<HashMap<String,Object>> task = new Task<>() {

			@Override
			protected HashMap<String,Object> call() throws Exception {
                HashMap<String,Object> foodWordSearchTable = new HashMap<>();
                // 단어 분할
                String word;
                HashSet<String> wordSet = new HashSet<>();
                List<Integer> indexList;
                for(int i=0;i<foodList.length;i++)
                {
                    for(int j=0;j*2+1<foodList[i].getTouristSpot().length();j++){
                        word = foodList[i].getTouristSpot().substring(j * 2, j * 2 + 2);
                        if(!wordSet.contains(word))
                        {
                            wordSet.add(word);
                            indexList = new ArrayList<Integer>();
                            indexList.add(i);
                            wordSet.add(word);
                            foodWordSearchTable.put(word,indexList);
                        }
                        else
                        {
                            indexList = (ArrayList<Integer>)foodWordSearchTable.get(word);
                            indexList.add(i);
                            foodWordSearchTable.replace(word, indexList);    
                        }  
                    }
                }
				return foodWordSearchTable;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
        thread.start();
        searchWordFoodTable = task.get();
		return ;
    }

    private void makeTourSpotSearchTable() throws InterruptedException, ExecutionException {
        Task<HashMap<String,Object>> task = new Task<>() {

			@Override
			protected HashMap<String,Object> call() throws Exception {
                HashMap<String,Object> tourWordSearchTable = new HashMap<>();
                // 단어 분할
                String word;
                HashSet<String> wordSet = new HashSet<>();
                List<Integer> indexList;
                for(int i=0;i<tourList.length;i++)
                {
                    for(int j=0;j*2+1<tourList[i].getTouristSpot().length();j++){
                        word = tourList[i].getTouristSpot().substring(j * 2, j * 2 + 2);
                        if(!wordSet.contains(word))
                        {
                            wordSet.add(word);
                            indexList = new ArrayList<Integer>();
                            indexList.add(i);
                            wordSet.add(word);
                            tourWordSearchTable.put(word,indexList);
                        }
                        else
                        {
                            indexList = (ArrayList<Integer>)tourWordSearchTable.get(word);
                            indexList.add(i);
                            tourWordSearchTable.replace(word, indexList);    
                        }  
                    }
                }
				return tourWordSearchTable;
			}
		};
		Thread thread = new Thread(task);
		thread.setDaemon(true);
        thread.start();
        searchWordTourTable = task.get();
		return ;
    }

    
   private ArrayList<TouristSpot> searchTouristSpot(String text) {
        int tourIndex = searchTourTable.getOrDefault(text,-1);
        int foodIndex = searchFoodTable.getOrDefault(text,-1);
        if(tourIndex == -1 && foodIndex == -1)
        {
            ArrayList<TouristSpot> results = new ArrayList<TouristSpot>();
            List<Integer> indexList;
            // 2글자로 쪼개 검색
            for(int i=0;2*i+1<text.length();i++)
            {
                indexList = (ArrayList<Integer>)searchWordFoodTable.getOrDefault(text.substring(i*2, i*2+2),null);
                if(indexList != null)
                {
                    for(int k : indexList){
                        results.add(foodList[k]);
                    }
                }
                indexList = (ArrayList<Integer>)searchWordTourTable.getOrDefault(text.substring(i*2, i*2+2),null);
                if(indexList != null)
                {
                    for(int k : indexList){
                        System.out.println("관광지 index : "+k);
                        results.add(tourList[k]);
                    }
                }
            }
            return results;
        }
        else if(foodIndex == -1)
        {
            ArrayList<TouristSpot> result = new ArrayList<TouristSpot>();
            result.add(tourList[tourIndex]);
            return result;
        }
        else if(tourIndex == -1)
        {
            ArrayList<TouristSpot> result = new ArrayList<TouristSpot>();
            result.add(foodList[foodIndex]);
            return result;
        }
        return null;
    }

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
				protocol.setPacket(Protocol.PT_REQUEST, Protocol.PT_USERINFO, Protocol.PT_LOOKUP, Protocol.PT_USER, user.toBytes());
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
    private TourPlan[] getTourPlanList() throws InterruptedException, ExecutionException {
        Task<TourPlan[]> task = new Task<TourPlan[]>() {

			@Override
			protected TourPlan[] call() throws Exception {

				Protocol protocol = new Protocol();
                Protocol resultProtocol = new Protocol();
                TourPlan tourPlan = new TourPlan();
                tourPlan.setUserId(user.getId());
                protocol.setPacket(Protocol.PT_REQUEST,Protocol.PT_TOURPLAN, Protocol.PT_LOOKUP, Protocol.PT_USER,tourPlan.toBytes());
                SocketHandler socketHandler = new SocketHandler();
                try {
                    resultProtocol = socketHandler.request(protocol);
                    System.out.println(resultProtocol.getBody());
                    TourPlan[] list = TourPlan.toTourPlanList(resultProtocol.getBody()); 
                    return list;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("tourPlan lookup error 발생");
                }
                return null; 
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
                TourPlan tourPlan = new TourPlan();
                tourPlan.setUserId(user.getId());
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
    private LocationRecommend getLocationRecommend() throws InterruptedException, ExecutionException {
        Task<LocationRecommend> task = new Task<LocationRecommend>() {

			@Override
			protected LocationRecommend call() throws Exception {

				Protocol protocol = new Protocol();
                Protocol resultProtocol = new Protocol();
                
                protocol.setPacket(Protocol.PT_REQUEST,Protocol.PT_RECOMMEND, Protocol.PT_EMPTY, Protocol.PT_USER,recommend.toBytes());
                SocketHandler socketHandler = new SocketHandler();
                try {
                    resultProtocol = socketHandler.request(protocol);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recommend = LocationRecommend.toRecommend(resultProtocol.getBody());  
                return recommend; 
            }   
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return task.get();
    }
    private void setTravelView(TourPlan[] tourPlans)
    {
        LocalDate today = LocalDate.now();
        for(int i = 0; i<tourPlans.length; i++)
        {
            travelView.getChildren().add(new Label(tourPlans[i].getTourPlanName()));
            Long dday = ChronoUnit.DAYS.between(today, tourPlans[i].getTourDay());
            if(dday == 0L)
            {
                travelView.getChildren().add(new Label("D-Day"));
            }
            else
            {
                travelView.getChildren().add(new Label("D-"+dday));
            }
        }
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
                try {
                    String imageSource = tour.getImageUrl();
                    Image image = new Image(imageSource);
                    ImageView tourImage = new ImageView(image);
                    Platform.runLater(()->{
                        tourImage.setFitWidth(200);
                        tourImage.setFitHeight(150);
                        newV.getChildren().addAll(tourImage,tourName);
                    });
                } catch (Exception e) {
                    ImageView tourImage = new ImageView(defaultImage);
                    Platform.runLater(()->{
                        tourImage.setFitWidth(200);
                        tourImage.setFitHeight(150);
                        newV.getChildren().addAll(tourImage,tourName);
                    });
                }
               
                Platform.runLater(() -> {
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
    public void moveToAddTravel() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/AddTravel.fxml"));
        Parent root = loader.load();
        AddTravel_Controller controller = loader.getController();
        addTravelBtn.getScene().setRoot(root);
        controller.enter(user.getId());
    }
    
    public void moveToWeather() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/WeatherView.fxml"));
        Parent root = loader.load();
        Weather_Controller weatherController = loader.getController();
        weatherBtn.getScene().setRoot(root);
        weatherController.enter(user.getId());
    }

    public void moveToUserEdit() throws IOException, InterruptedException, ExecutionException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/UserEdit.fxml"));
        Parent root = loader.load();
        UserEdit_Controller controller = loader.getController();
        userEditBtn.getScene().setRoot(root); 
        controller.enter(user.getId());
    }
    private void moveToTourSpotView() throws IOException, InterruptedException, ExecutionException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/TourSpotView.fxml"));
        Parent root = loader.load();
        TourSpotView_Controller controller = loader.getController();
        userEditBtn.getScene().setRoot(root); 
        controller.enter(user.getId());
    }
    private void moveToFoodView() throws IOException, InterruptedException, ExecutionException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/FoodView.fxml"));
        Parent root = loader.load();
        FoodView_Controller controller = loader.getController();
        userEditBtn.getScene().setRoot(root); 
        controller.enter(user.getId());
    }
        
    private void refresh() throws IOException, InterruptedException, ExecutionException {
        vBox.getChildren().clear();
        TouristSpot tourSpot = getTouristSpot(tourList);
        TouristSpot foodSpot = getTouristSpot(foodList);
        addVBox(tourSpot);
        addVBox(foodSpot);
    }

  
}