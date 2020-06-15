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

public class TourSpotView_Controller {
    @FXML
    Button backBtn;
    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox vBox;

    UserInfo user = new UserInfo();
    TouristSpot[] tourList;
    Image defaultImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/defaultImage.png"));

    public void enter(String userID) throws InterruptedException, ExecutionException {
        user.setId(userID);
        tourList = getTourSpotList();
        setVBox();    
    }
    // 이벤트 핸들러


    private void setVBox() 
    {   
        Thread thread = new Thread() {
            @Override
            public void run() {  
            for(int i = 0; i<tourList.length; i++)
            {
                Label name = new Label(tourList[i].getTouristSpot());
                Label place = new Label(tourList[i].getLocation());
                try {
                    String imageSource = tourList[i].getImageUrl();
                    Image image = new Image(imageSource);
                    ImageView tourImage = new ImageView(image);
                    Platform.runLater(()->{
                        tourImage.setFitWidth(200);
                        tourImage.setFitHeight(150);
                        vBox.getChildren().addAll(tourImage,name,place);
                        });
                    } catch (Exception e) {
                        ImageView tourImage = new ImageView(defaultImage);
                        Platform.runLater(()->{
                            tourImage.setFitWidth(200);
                            tourImage.setFitHeight(150);
                            vBox.getChildren().addAll(tourImage,name,place);
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

    public TouristSpot[] getTourSpotList() throws InterruptedException, ExecutionException 
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

    public void moveToMain(String id) throws IOException, InterruptedException, ExecutionException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/Main.fxml"));
        Parent root = loader.load();
        Main_Controller controller = loader.getController();
        backBtn.getScene().setRoot(root);
        controller.enter(id);
    }
    
}