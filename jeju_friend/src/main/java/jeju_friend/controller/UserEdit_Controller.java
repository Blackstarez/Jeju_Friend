package jeju_friend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserEdit_Controller {

    @FXML
    Button travelEditBtn;
    @FXML
    Button travelPlusBtn;
    @FXML
    Button traveldeleteBtn;
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
    
    public void enter()
    {
        // 유저 기본 정보 받아와야 함.
    }

    //이벤트 핸들러

    public void travelPlusBtn_Actioned()
    {
        
    }
    public void travelDeleteBtn_Actioned()
    {
        
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
	private void regionBtn2_Actioned(){
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
    
    //로직

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
		return 0;
	}

}