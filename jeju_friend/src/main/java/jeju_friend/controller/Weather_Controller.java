package jeju_friend.controller;
import static java.util.Calendar.getInstance;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Weather_Controller {
    @FXML 
    private Label todayLabel1;


    public void setTime() 
    {
        todayLabel1.setText("d");
    }
}