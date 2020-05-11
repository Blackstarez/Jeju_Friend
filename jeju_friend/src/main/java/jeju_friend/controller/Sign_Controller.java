package jeju_friend.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Sign_Controller 
{

	// 이벤트 처리
	@FXML
	private ToggleButton maleToggleBtn;

	@FXML 
	private ToggleButton femaleToggleBtn;

	@FXML
	private TextField idField;

	@FXML
	private PasswordField pwField;

	@FXML
	private PasswordField pwConfirmField;

	@FXML
	private TextField nameField;

	@FXML
	private Button applyBtn;
	@FXML
	private void idField_Typed(KeyEvent event)
	{
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) 
		{
			pwField.requestFocus();   
		}
	}

	@FXML
	private void pwField_Typed(KeyEvent event)
	{
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) 
		{
			pwConfirmField.requestFocus();   
		}
	}
	
	@FXML
	private void pwConfirmField_Typed(KeyEvent event)
	{
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) 
		{
			nameField.requestFocus();   
		}
	}

	@FXML
	private void applyBtn_actioned() throws IOException
	{
		createAccount();
	}


	// 로직
	public void createAccount() throws IOException
	{
		boolean isMale = maleToggleBtn.isSelected();
		boolean isFemale = femaleToggleBtn.isSelected();
		String inputID = idField.getText();
		String inputPW = pwField.getText();
		String inputConfirmPW = pwConfirmField.getText();
		String inputName = nameField.getText();
		
		// 필수정보들 입력 했는지 아닌지 확인 과정
		if(isMale==false && isFemale==false)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("성별을 선택해 주세요!");
			alert.showAndWait();
		}
		else if(inputID.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("아이디를 입력해 주세요!");
			alert.showAndWait();
			idField.requestFocus();
		}
		else if(inputPW.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호를 입력해 주세요!");
			alert.showAndWait();
			pwField.requestFocus();
		}
		else if(inputPW==inputConfirmPW)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호와 비밀번호 확인이 다릅니다!");
			alert.showAndWait();
			pwField.requestFocus();
		}
		else if(inputName.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("이름을 입력해주세요!");
			alert.showAndWait();
			nameField.requestFocus();
		}
		//나이 선택 확인하는 코드 필요함. 근데 아직 초이스박스 구현을 안해놔서 일단 주석
		else
			moveToLogin();
		// 이제 통신해서 DB에 새 계정 넣는 과정
		
	}
	public void moveToLogin() throws IOException
	{
		Stage primaryStage = (Stage) applyBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Login.fxml"));
        Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show();   
	}
}


