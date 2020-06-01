package jeju_friend_server.Elements;

import java.io.*;

public class TourPlan {
    private static final long serialVersionUID = 1L;
    private String userId;          //사용자 ID
    private String tourPlanName;    //여행계획명
    private int tourWith;           //함께하는 사람
    private String tourForm;        //여행형태  구분자 ','
    private int areaInterest; //관심지역

    public TourPlan(){}
    // getter, setter
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTourPlanName() {
        return tourPlanName;
    }

    public void setTourPlanName(String tourPlanName) {
        this.tourPlanName = tourPlanName;
    }

    public int getTourWith() {
        return tourWith;
    }

    public void setTourWith(int tourWith) {
        this.tourWith = tourWith;
    }

    public String getTourForm() {
        return tourForm;
    }

    public void setTourForm(String tourForm) {
        this.tourForm = tourForm;
    }

    public int getAreaInterest() {
        return areaInterest;
    }

    public void setAreaInterest(int areaInterest) {
        this.areaInterest = areaInterest;
    }
    

    //직렬화 및 역 직렬화
    public byte[] toBytes()
    {
        TourPlan obj = this;
        byte[] serializedMember = null;

        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try{
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                serializedMember = baos.toByteArray();
            }catch(Exception e) { }
        }catch(Exception e) { }

        return serializedMember;
    }

    public static TourPlan toLogin(byte[] serializedTourPlan)
    {
        TourPlan plan = null;

        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(serializedTourPlan);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                plan = (TourPlan)ois.readObject();
            } catch (Exception e) {}
        }catch(Exception e){}

        return plan;
    }

    // 객체 출력
    public void printInfo()
    {   
        String with="";
        switch(this.tourWith)
        {
            case 1:
                with="가족";
                break;
            case 2:
                with="친구";
                break;
            case 3:
                with="애인";
                break;
            case 4:
                with="단체";
                break;
            case 5:
                with="혼자서";
                break;
        }
        System.out.printf("사용자ID : %s\n여행명 : %s\n함께하는 사람 : %s\n여행형태 : %s\n관심지역 : %s\n",this.userId,this.tourPlanName,with,this.tourForm,this.areaInterest);
    }


}