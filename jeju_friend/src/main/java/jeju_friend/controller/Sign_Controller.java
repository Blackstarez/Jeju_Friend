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
	private ToggleButton maleToggleBtn = new ToggleButton();

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
	private void applyBtn_Actioned() throws IOException
	{
		createAccount();
	}

	@FXML
	private void maleToggleBtn_Actioned() 
	{
		if(maleToggleBtn.isSelected())
		{
			femaleToggleBtn.setSelected(false);
		}
	}

	@FXML
	private void femaleToggleBtn_Actioned()
	{
		if(femaleToggleBtn.isSelected())
		{
			maleToggleBtn.setSelected(false);
		}
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
			return;
		}
		else if(inputID.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("아이디를 입력해 주세요!");
			alert.showAndWait();
			idField.requestFocus();
			return;
		}
		else if(inputPW.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호를 입력해 주세요!");
			alert.showAndWait();
			pwField.requestFocus();
			return;
		}
		else if(inputPW==inputConfirmPW)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호와 비밀번호 확인이 다릅니다!");
			alert.showAndWait();
			pwField.requestFocus();
			return;
		}
		else if(inputName.isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("이름을 입력해주세요!");
			alert.showAndWait();
			nameField.requestFocus();
			return;
		}
		// 나이 선택 확인하는 코드 필요함. 
		// 선호지역 선택확인하는 코드 필요함. 
		// 근데 아직 구현을 안해놔서 일단 주석

		/* 이제 통신해서 DB에 새 계정 넣는 과정

		주오가 할 일 ! 

		*/
		
		moveToLogin(); 
			// 이건 지금은 여기에 있는데 나중에 DB에 새 계정 넣는 과정 생기면 
			// 아이디 생성 성공하면 로그인창으로 이동하도록 수정해야 함

		
		
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


