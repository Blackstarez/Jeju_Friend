package jeju_friend.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import jeju_friend.Elements.Protocol;
import jeju_friend.Elements.TourPlan;
import jeju_friend.Elements.UserInfo;
import jeju_friend.application.SocketHandler;

public class EditTravel_Controller {
    @FXML
    private Button cancelBtn;
    @FXML
    private Button EditTravelBtn;
    @FXML
    private Label travelNameLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button deleteBtn;

    UserInfo user = new UserInfo();
    TourPlan tour = new TourPlan();

    public void enter(String id, TourPlan tour)
    {
        user.setId(id);
        this.tour = tour;
        travelNameLabel.setText(tour.getTourPlanName());
    }
    // 이벤트 핸들러

    @FXML
    public void deleteBtn_Actioned()
    {
        tryToDelete();
    }
    


    @FXML
    public void cancelBtn_Actioned() throws IOException, InterruptedException, ExecutionException {
        moveToMain();
    }

    @FXML
    public void EditTravelBtn_Actioned() throws IOException, InterruptedException, ExecutionException {
        checkValid();
    }

    // 로직

    public void checkValid() throws IOException, InterruptedException, ExecutionException {
        LocalDate tourDay = datePicker.getValue();
        // 선호 지역 골라야 함.
        if (tourDay == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("여행 생성 오류");
            alert.setHeaderText(null);
            alert.setContentText("여행 시작일을 선택해 주세요!");
            alert.showAndWait();
            return;
        } else {
            TourPlan tourPlan = new TourPlan();
            tourPlan.setTourDay(tourDay);
            tourPlan.setUserId(user.getId());
            editTravel(tourPlan);
        }
    }

    public void editTravel(TourPlan tourPlan) throws IOException, InterruptedException, ExecutionException {
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

    public void moveToMain() throws IOException, InterruptedException, ExecutionException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/Main.fxml"));
        Parent root = loader.load();
        Main_Controller controller = loader.getController();
        cancelBtn.getScene().setRoot(root); 
        controller.enter(user.getId());
    }


    private void tryToDelete() 
    {
        Protocol protocol = new Protocol();
		Protocol resultProtocol = new Protocol();

		protocol.setPacket(Protocol.PT_REQUEST, Protocol.PT_TOURPLAN, Protocol.PT_DELETE, Protocol.PT_USER,tour.toBytes());
		SocketHandler socketHandler = new SocketHandler();
			try {
				resultProtocol = socketHandler.request(protocol);
			} catch (Exception e) {
				e.printStackTrace();
            }
            
    }

}