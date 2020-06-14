package jeju_friend.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import jeju_friend.Elements.Protocol;
import jeju_friend.Elements.UserInfo;
import jeju_friend.Elements.Weather;
import jeju_friend.application.SocketHandler;

public class Weather_Controller {
    @FXML
    private Label dateLabel1;

    @FXML
    private Label dateLabel2;

    @FXML
    private Label dateLabel3;

    @FXML
    private Button backBtn;

    @FXML
    private Button lookUpBtn;

    @FXML
    private ChoiceBox cityBox;

    @FXML
    private ChoiceBox townBox;

    @FXML
    private GridPane grid1;

    @FXML
    private GridPane grid2;

    @FXML
    private GridPane grid3;

    UserInfo user = new UserInfo();

    private final String[] sityArr = { "제주시", "서귀포시" };

    private final String[] townArr1 = { "전체", "한림음", "애월읍", "구좌읍", "조천읍", "한경명", "추자면", "우도면", "일도1동", "일도2동", "이도1동",
            "이도2동", "삼도1동", "삼도2동", "용담1동", "용담2동", "건입동", "화북동", "삼양동", "봉개동", "아라동", "오라동", "연동", "노형동", "외도동", "이호동",
            "도두동" };

    private final String[] townArr2 = { "전체", "대정읍,마라도", "남원읍", "성산읍", "안덕면", "표선면", "송산동", "정방동", "중앙동", "천지동", "효돈동",
            "영천동", "동홍동", "서홍동", "대륜동", "대천동", "중문동", "예래동" };

    private final int[] areaCode = { 50110000, 50110250, 50110253, 50110256, 50110259, 50110310, 50110320, 50110330,
            50110510, 50110520, 50110530, 50110540, 50110550, 50110560, 50110570, 50110580, 50110590, 50110600,
            50110610, 50110620, 50110630, 50110640, 50110650, 50110660, 50110670, 50110680, 50110690, 50130250,
            50130253, 50130259, 50130310, 50130320, 50130510, 50130520, 50130530, 50130540, 50130550, 50130560,
            50130570, 50130580, 50130590, 50130600, 50130610, 50130620 };

    public void enter(String id) {
        cityBox.setItems(FXCollections.observableArrayList(sityArr));
        //grid1.setVisible(false);
        //grid1.setVisible(false);
        //grid1.setVisible(false);
        user.setId(id);
    }

    // 이벤트 처리
    @FXML
    public void backBtn_Actioned() throws IOException, InterruptedException, ExecutionException {
        moveToMain();
    }

    @FXML
    public void townBox_Cliked() {
        if (cityBox.getSelectionModel().isSelected(0)) {
            townBox.setItems(FXCollections.observableArrayList(townArr1));
        } else if (cityBox.getSelectionModel().isSelected(1)) {
            townBox.setItems(FXCollections.observableArrayList(townArr2));
        }
    }

    // 로직

    public void lookUpBtn_Actioned() {
        Weather[] list = getWeatherlists();
        Weather[] today = getTodayWeatherlist(list);
        Weather[] tomorrow = getTomorrowWeatherlist(list);
        Weather[] last = getLastWeatherlist(list);
        setGrid(grid1, today);
        setGrid(grid2, tomorrow);
        setGrid(grid3, last);
    }

    public int getAreaCode() {
        int code = 0;
        if (cityBox.getSelectionModel().isSelected(0)) {
            code = areaCode[townBox.getSelectionModel().getSelectedIndex()];
        } else if (cityBox.getSelectionModel().isSelected(1)) {
            code = areaCode[townBox.getSelectionModel().getSelectedIndex() + 26];
        }
        return code;
    }

    public Weather[] getWeatherlists() {
        int areaCode = getAreaCode(); // 지역코드 휙득
        Weather weather = new Weather(areaCode); // Weather 생성

        Protocol protocol = new Protocol();
        Protocol resultProtocol = new Protocol();
        protocol.setPacket(Protocol.PT_REQUEST, Protocol.PT_WEATHER, Protocol.PT_LOOKUP, Protocol.PT_USER,
                weather.toBytes());
        SocketHandler socketHandler = new SocketHandler();

        try {
            resultProtocol = socketHandler.request(protocol);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Weather[] list = Weather.toWeatherlists(resultProtocol.getBody());
        return list;

    }

    public void moveToMain() throws IOException, InterruptedException, ExecutionException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeju_friend/Main.fxml"));
        Parent root = loader.load();
        Main_Controller controller = loader.getController();
        backBtn.getScene().setRoot(root); 
        controller.enter(user.getId());
    }

    public Weather[] getTodayWeatherlist(Weather[] list) 
    {
        Date today = new Date();
        Weather[] todayList;
        int index = 0;
        while(index < list.length)
        {
            index++;
            if(list[index].getDay().compareTo(today) != 0)
            {
                break;
            }
        }

        todayList = new Weather[index];

        for(int i =0;i<index;i++)
        {
            todayList[i]=list[i];
        }
        return todayList;
    }


    public Weather[] getTomorrowWeatherlist(Weather[] list) 
    {
        Date tomorrow = new Date();
        Weather[] tomorrowList;
        int i = 0 , count = 0, pos = 0;
        boolean isCheck = false;
        while(i<list.length)
        {
            
            if(tomorrow.compareTo(list[i].getDay()) != 0)
            {
                isCheck = !isCheck;
            }

            if(isCheck)
            {
                if(pos == 0)
                    pos = i;
                count++;
            }
            i++;
        }
        pos--;
        tomorrowList = new Weather[count];
        for(i=0;i<count;pos++,i++)
        {
            tomorrowList[i] = list[pos];
        }

        return tomorrowList;
    }


    public Weather[] getLastWeatherlist(Weather[] list) 
    {
        Date day = new Date();
        Weather[] lastList = new Weather[10];
        int count = 0,i;
        for(i = 0;i<list.length;i++){
            if(count < 2)
            {
                if(day.compareTo(list[i].getDay()) != 0)
                {
                    count++;
                    day = list[i].getDay();
                }
            }
            else
            {
                break;
            }
        }
        lastList = new Weather[list.length-i];
        for(int j =0;i<list.length;i++,j++)
        {
            lastList[j] = list[i];
        }
        return lastList;
	}

    public void setGrid(GridPane grid, Weather[] list)
    {
        for (int index = 1; index <=list.length; index++) {
            RowConstraints con = new RowConstraints();
            con.setPrefHeight(30);
            grid.getRowConstraints().add(con);
            

            System.out.println("---------------------------------------------");
            list[index-1].printInfo();
            System.out.println("---------------------------------------------");

            grid.add(new Label(Integer.toString(list[index-1].getTime())+"시"), 0, index);
            grid.add(new Label(Integer.toString(list[index-1].getRainfallProbability())), 1 ,index);
            grid.add(new Label(list[index-1].getRainfallForm()), 2, index);
            grid.add(new Label(String.valueOf(list[index-1].getTemperature())), 3, index);
            grid.add(new Label(String.valueOf(list[index-1].getWindSpeed())), 4, index);
            grid.add(new Label(list[index-1].getWindDirection()), 5, index);
            grid.add(new Label(String.valueOf(list[index-1].getHumidity())), 6, index);
        }
        grid.setVisible(true);
    }
	
}