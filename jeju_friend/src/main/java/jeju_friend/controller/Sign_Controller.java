package jeju_friend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Sign_Controller 
{
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
	
}


