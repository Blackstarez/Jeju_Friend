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
import javafx.stage.Stage;

import jeju_friend.Elements.Protocol;
import jeju_friend.Elements.UserInfo;
import jeju_friend.application.SocketHandler;

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

	@FXML
	private ToggleButton regionBtn1;

	@FXML
	private ToggleButton regionBtn2;

	@FXML
	private ToggleButton regionBtn3;

	@FXML
	private ToggleButton regionBtn4;

	@FXML
	private ToggleButton regionBtn5;

	@FXML
	private ImageView view1;
	
	@FXML
	private ImageView view2;

	@FXML
	private ImageView view3;

	@FXML
	private ImageView view4;

	@FXML
	private ImageView view5;

	@FXML
	private ToggleGroup regionGroup;

	private int inputAge = 12;
	Image maleSelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/male_icon_selected.png"));
	Image maleUnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/male_icon.png"));
	Image femaleSelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/female_icon_selected.png"));
	Image femaleUnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/female_icon.png"));
	
	Image map1SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_1_selected.png"));
	Image map1UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_1.png"));
	Image map2SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_2_selected.png"));
	Image map2UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_2.png"));
	Image map3SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_3_selected.png"));
	Image map3UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_3.png"));
	Image map4SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_4_selected.png"));
	Image map4UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_4.png"));
	Image map5SelectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_5_selected.png"));
	Image map5UnselectedImage = new Image(getClass().getResourceAsStream("/jeju_friend/image/map_5.png"));

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
		checkValid();
	}

	
	@FXML
	private void cancelBtn_Actioned() throws IOException {
		moveToLogin();
	}

	@FXML
	private void maleToggleBtn_Actioned() throws IOException{
		if(maleToggleBtn.isSelected())
		{
			femaleToggleBtn.setSelected(false);
			maleToggleBtn.setGraphic(new ImageView(maleSelectedImage));
			femaleToggleBtn.setGraphic(new ImageView(femaleUnselectedImage));
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
			femaleToggleBtn.setGraphic(new ImageView(femaleSelectedImage));
			maleToggleBtn.setSelected(false);
			maleToggleBtn.setGraphic(new ImageView(maleUnselectedImage));
		}
		else
		{
			femaleToggleBtn.setGraphic(new ImageView(femaleUnselectedImage));
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

	@FXML
	private void regionBtn1_Actioned(){

		regionBtn1.setToggleGroup(regionGroup);
		if(regionBtn1.isSelected())
		{
			view1.setImage(map1SelectedImage);
			view2.setImage(map2UnselectedImage);
			view3.setImage(map3UnselectedImage);
			view4.setImage(map4UnselectedImage);
			view5.setImage(map5UnselectedImage);
		}	
		else
			view1.setImage(map1UnselectedImage);
	}
	@FXML
	private void regionBtn2_Actioned()
	{
		regionBtn2.setToggleGroup(regionGroup);
		if(regionBtn2.isSelected())
		{
			view1.setImage(map1UnselectedImage);
			view2.setImage(map2SelectedImage);
			view3.setImage(map3UnselectedImage);
			view4.setImage(map4UnselectedImage);
			view5.setImage(map5UnselectedImage);
		}		
		else
			view2.setImage(map2UnselectedImage);
	}
	@FXML
	private void regionBtn3_Actioned(){
		regionBtn3.setToggleGroup(regionGroup);
		if(regionBtn3.isSelected())
		{
			view1.setImage(map1UnselectedImage);
			view2.setImage(map2UnselectedImage);
			view3.setImage(map3SelectedImage);
			view4.setImage(map4UnselectedImage);
			view5.setImage(map5UnselectedImage);
		}			
		else
			view3.setImage(map3UnselectedImage);
	}
	@FXML
	private void regionBtn4_Actioned(){
		regionBtn4.setToggleGroup(regionGroup);
		if(regionBtn4.isSelected())
		{
			view1.setImage(map1UnselectedImage);
			view2.setImage(map2UnselectedImage);
			view3.setImage(map3UnselectedImage);
			view4.setImage(map4SelectedImage);
			view5.setImage(map5UnselectedImage);
		}
		else
			view4.setImage(map4UnselectedImage);
	}
	@FXML
	private void regionBtn5_Actioned(){
		regionBtn5.setToggleGroup(regionGroup);
		if(regionBtn5.isSelected())
		{
			view1.setImage(map1UnselectedImage);
			view2.setImage(map2UnselectedImage);
			view3.setImage(map3UnselectedImage);
			view4.setImage(map4UnselectedImage);
			view5.setImage(map5SelectedImage);
		}
		else
			view5.setImage(map5UnselectedImage);
	}
	// 로직


	//아이디 만들기
	public void checkValid() throws IOException {
		final boolean isMale = maleToggleBtn.isSelected();		// 남자눌렀냐? 성별이 남자면 T 여자면 F므로 이게 곧 성별임. 
		final boolean isFemale = femaleToggleBtn.isSelected();	// 여자눌렀냐?
		final String inputID = idField.getText();				// 아이디
		final String inputPW = pwField.getText();				// 비밀번호
		final String inputConfirmPW = pwConfirmField.getText(); // 비번확인
		final String inputName = nameField.getText(); 			// 이름
		final int inputAge = this.inputAge; 					// 나이
		final int interestArea = getSelectedRegion();
		// 필수정보들 입력 했는지 아닌지 확인 과정
		if ((isMale!=true) &&  (isFemale!=true)) {
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
		} else if (inputPW.compareTo(inputConfirmPW)!=0) {
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
		else
		{
			UserInfo userInfo = new UserInfo();
			userInfo.setAge(inputAge);
			userInfo.setGender(isMale);
			userInfo.setId(inputPW);
			userInfo.setPw(inputPW);
			userInfo.setNickName(inputName);
			userInfo.setInterestArea(interestArea);
			tryToSign(userInfo);
		}
	}

	public void tryToSign(UserInfo userInfo) throws IOException {
		Protocol pro = new Protocol();
		Protocol resultProtocol = new Protocol();
		System.out.println(userInfo.toBytes());
		pro.setPacket(Protocol.PT_REQUEST, Protocol.PT_USERINFO, Protocol.PT_APPLY, Protocol.PT_UNKNOWN, userInfo.toBytes());
		
		SocketHandler socketHandler = new SocketHandler();
		try {
			resultProtocol = socketHandler.request(pro);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//회원가입 성공 여부 체크
		if(resultProtocol.getProtocolCodeExpansion() == Protocol.PT_SUCCESS)
		{
			moveToLogin();
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("로그인 오류");
			alert.setHeaderText(null);
			alert.setContentText("아이디 비밀번호를 다시 입력해 주세요!");
			alert.showAndWait();
			idField.requestFocus();
			return;
		}

	}
	public void moveToLogin() throws IOException {
		final Stage primaryStage = (Stage) applyBtn.getScene().getWindow();
		final Parent root = FXMLLoader.load(getClass().getResource("/jeju_friend/Login.fxml"));
		final Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.show();   
	}

	public int getSelectedRegion() {
		
		if(regionBtn1.isSelected())
			return 1;
		else if(regionBtn2.isSelected())
			return 2;
		else if(regionBtn3.isSelected())
			return 3;
		else if(regionBtn4.isSelected())
			return 4;
		else if(regionBtn5.isSelected())
			return 5;
		else
		return 0;
	}
}


