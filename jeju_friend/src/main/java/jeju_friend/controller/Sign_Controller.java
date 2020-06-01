package jeju_friend.controller;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
	private Button cancelBtn;

	@FXML
	private Slider slider;

	@FXML
	private Label ageLabel;

	private int inputAge = 12;
	Image maleSelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/male_icon_selected.png"));
	Image maleUnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/male_icon.png"));

	@FXML
	private void idField_Typed(final KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
			pwField.requestFocus();
		}
	}

	@FXML
	private void pwField_Typed(final KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
			pwConfirmField.requestFocus();
		}
	}

	@FXML
	private void pwConfirmField_Typed(final KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCharacter().equals("\r")) {
			nameField.requestFocus();
		}
	}

	@FXML
	private void applyBtn_Actioned() throws IOException {
		createAccount();
	}

	
	@FXML
	private void cancelBtn_Actioned() throws IOException {
		moveToLogin();
	}

	@FXML
	private void maleToggleBtn_Actioned() throws IOException{
		if(maleToggleBtn.isSelected())
		{
			maleToggleBtn.setSelected(true);
			femaleToggleBtn.setSelected(false);
			maleToggleBtn.setGraphic(new ImageView(maleSelectedImage));
		}	
		else
		{
			maleToggleBtn.setGraphic(new ImageView(maleUnselectedImage));
		}
	}

	@FXML
	private void femaleToggleBtn_Actioned() throws IOException{		
		if(femaleToggleBtn.isSelected())
		{
			femaleToggleBtn.setSelected(true);
			maleToggleBtn.setSelected(false);
			maleToggleBtn.setGraphic(new ImageView(maleUnselectedImage));
		}
		
	}

	@FXML
	private void sliderMouseDragged() {
		slider.setShowTickLabels(false);
		slider.valueProperty().addListener(
			(observable, oldvalue, newvalue) ->
			{
				inputAge = newvalue.intValue();
				ageLabel.setText(Integer.toString(inputAge));
			} );
	}


	// 로직


	//아이디 만들기
	public void createAccount() throws IOException {
		final boolean isMale = maleToggleBtn.isSelected();		// 남자눌렀냐? 성별이 남자면 T 여자면 F므로 이게 곧 성별임. 
		final boolean isFemale = femaleToggleBtn.isSelected();	// 여자눌렀냐?
		final String inputID = idField.getText();				// 아이디
		final String inputPW = pwField.getText();				// 비밀번호
		final String inputConfirmPW = pwConfirmField.getText(); // 비번확인
		final String inputName = nameField.getText(); 			// 이름
		final int inputAge = this.inputAge; 					// 나이

		// 필수정보들 입력 했는지 아닌지 확인 과정
		if (isMale || isFemale) {
			final Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("성별을 선택해 주세요!");
			alert.showAndWait();
			return;
		} else if (inputID.isEmpty()) {
			final Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("아이디를 입력해 주세요!");
			alert.showAndWait();
			idField.requestFocus();
			return;
		} else if (inputPW.isEmpty()) {
			final Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호를 입력해 주세요!");
			alert.showAndWait();
			pwField.requestFocus();
			return;
		} else if (inputPW == inputConfirmPW) {
			final Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("비밀번호와 비밀번호 확인이 다릅니다!");
			alert.showAndWait();
			pwField.requestFocus();
			return;
		} else if (inputName.isEmpty()) {
			final Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("회원가입 오류");
			alert.setHeaderText(null);
			alert.setContentText("닉네임을 입력해주세요!");
			alert.showAndWait();
			nameField.requestFocus();
			return;
		} 

		// 선호지역 선택확인하는 코드 필요함.
		// 근데 아직 구현을 안해놔서 일단 주석

		/*
		 * 이제 통신해서 DB에 새 계정 넣는 과정
		 * 
		 * 주오가 할 일 !
		 * 
		 */

		moveToLogin();
		// 이건 지금은 여기에 있는데 나중에 DB에 새 계정 넣는 과정 생기면
		// 아이디 생성 성공하면 로그인창으로 이동하도록 수정해야 함

	}

	public void moveToLogin() throws IOException {
		final Stage primaryStage = (Stage) applyBtn.getScene().getWindow();
		final Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Login.fxml"));
		final Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show();   
	}
}


