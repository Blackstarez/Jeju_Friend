package jeju_friend.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Login_Controller 
{
	@FXML
	private TextField idField;
	
	@FXML
	private PasswordField pwField;
	
	@FXML
	private Button loginBtn;
	
	@FXML
	private Button signBtn;

	@FXML 
	private Label pwLabel;

	@FXML
	private Label idLabel;


	@FXML
	private void idField_Typed(KeyEvent event)
	{
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) 
		{
			pwField.requestFocus();
			pwLabel.setVisible(false);
		}
		idLabel.setVisible(false);
	}
	
	@FXML
	private void pwField_Typed(KeyEvent event) throws Exception
	{
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) 
		{
			login();
		}
		pwLabel.setVisible(false);
	}

	@FXML
	private void idField_Clicked(MouseEvent event)
	{
		idLabel.setVisible(false);
	}

	@FXML
	private void pwField_Cliked(MouseEvent event)
	{
		pwLabel.setVisible(false);
	}
	
	@FXML
	private void loginBtn_Actioned() throws Exception
	{
		login();
	}

	@FXML
	private void signBtn_Actioned() throws Exception
	{
		moveToSign();
	}

	// 로직


	private void login() throws Exception
	{
		String inputID = idField.getText();
		String inputPW = pwField.getText();
		
		//id,pw 잘 입력했나 확인
		
		if (inputID.isEmpty()) 
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인 오류");
			alert.setHeaderText(null);
			alert.setContentText("아이디를 입력해 주세요!");
			alert.showAndWait();
			idField.requestFocus();
            return;
        } 
		else if (inputPW.isEmpty()) 
		{
        	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인 오류");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호를 입력해 주세요!");
			alert.showAndWait();
        	pwField.requestFocus();
            return;
        }
		
		// 주오가 네트워킹해서 아이디 입력 해서 로그인되는지 확인해야해 ^-^
		
		//일단 아무거나 입력하면 무조건 되는걸로 하고 메인페이지로 ㄱㄱ
		moveToMain();
		
	}
	
	public void moveToMain() throws IOException
	{
		Stage primaryStage = (Stage) loginBtn.getScene().getWindow(); 
        Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Main.fxml"));
        Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
        primaryStage.show();   
	}
	public void moveToSign() throws IOException
	{
		Stage primaryStage = (Stage) signBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Sign.fxml"));
        Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show();   
	}
}
