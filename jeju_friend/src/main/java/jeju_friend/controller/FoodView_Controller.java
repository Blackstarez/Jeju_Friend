package jeju_friend.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import jeju_friend.Elements.Protocol;
import jeju_friend.Elements.TouristSpot;
import jeju_friend.Elements.UserInfo;
import jeju_friend.application.SocketHandler;

public class FoodView_Controller {
    @FXML
    Button backBtn;
    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox vBox;

    UserInfo user = new UserInfo();
    TouristSpot[] foodList;
    Image defaultImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/defaultImage.png"));

    public void enter(String userID) throws InterruptedException, ExecutionException {
        user.setId(userID);
        foodList = getFoodSpotList();
        setScrollPane();    
    }
    // 이벤트 핸들러


    private void setScrollPane() 
    {   
        Thread thread = new Thread() {
            @Override
            public void run() {  
            for(int i = 0; i<foodList.length; i++)
            {
                Label name = new Label(foodList[i].getTouristSpot());
                Label place = new Label(foodList[i].getLocation());
                try {
                    String imageSource = foodList[i].getImageUrl();
                    Image image = new Image(imageSource);
                    ImageView foodImage = new ImageView(image);
                    Platform.runLater(()->{
                        foodImage.setFitWidth(200);
                        foodImage.setFitHeight(150);
                        vBox.getChildren().addAll(foodImage,name,place);
                        });
                    } catch (Exception e) {
                        ImageView foodImage = new ImageView(defaultImage);
                        Platform.runLater(()->{
                            foodImage.setFitWidth(200);
                            foodImage.setFitHeight(150);
                            vBox.getChildren().addAll(foodImage,name,place);
                        });
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        
    }

    public void backBtn_Actioned() throws IOException, InterruptedException, ExecutionException {
        moveToMain(user.getId());
    }

    // 로직

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

    public void moveToMain(String id) throws IOException, InterruptedException, ExecutionException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/Main.fxml"));
        Parent root = loader.load();
        Main_Controller controller = loader.getController();
        backBtn.getScene().setRoot(root);
        controller.enter(id);
    }
    
}