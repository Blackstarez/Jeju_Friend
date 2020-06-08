package jeju_friend.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
import jeju_friend.Elements.Login;
import jeju_friend.Elements.Protocol;
import jeju_friend.application.SocketHandler;

import java.net.Socket;

public class Login_Controller {
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
	private void idField_Typed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
			pwField.requestFocus();
			pwLabel.setVisible(false);
		}
		idLabel.setVisible(false);
	}

	@FXML
	private void pwField_Typed(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
			login();
		}
		pwLabel.setVisible(false);
	}

	@FXML
	private void idField_Clicked(MouseEvent event) {
		idLabel.setVisible(false);
	}

	@FXML
	private void pwField_Cliked(MouseEvent event) {
		pwLabel.setVisible(false);
	}

	@FXML
	private void loginBtn_Actioned() throws Exception {
		login();
	}

	@FXML
	private void signBtn_Actioned() throws Exception {
		moveToSign();
	}

	// 로직

	private void login() throws Exception {
		String inputID = idField.getText();
		String inputPW = pwField.getText();

		// id,pw 잘 입력했나 확인

		if (inputID.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인 오류");
			alert.setHeaderText(null);
			alert.setContentText("아이디를 입력해 주세요!");
			alert.showAndWait();
			idField.requestFocus();
			return;
		} else if (inputPW.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인 오류");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호를 입력해 주세요!");
			alert.showAndWait();
			pwField.requestFocus();
			return;
		}
		else
			tryLogin(inputID, inputPW);		
	}

	public void moveToMain() throws IOException {
		Stage primaryStage = (Stage) loginBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Main.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public void moveToSign() throws IOException {
		Stage primaryStage = (Stage) signBtn.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Sign.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public void tryLogin(String inputID, String inputPW) throws IOException {
		Protocol protocol = new Protocol();
		Protocol resultProtocol = new Protocol();
		Login loginInfo = new Login(inputID, inputPW);
		
		//로그인 요청
		protocol.setPacket(Protocol.PT_REQUEST,Protocol.PT_SIGNIN, Protocol.PT_APPLY, Protocol.PT_USER,
				loginInfo.toBytes());
		SocketHandler socketHandler = new SocketHandler();
		try {
			resultProtocol = socketHandler.request(protocol);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//로그인 성공 여부 체크
		if(resultProtocol.getProtocolCodeExpansion() == Protocol.PT_SUCCESS)
		{
			moveToMain();
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인 오류");
			alert.setHeaderText(null);
			alert.setContentText("아이디 비밀번호를 다시 입력해 주세요!");
			alert.showAndWait();
			idField.requestFocus();
		}
	}
}
