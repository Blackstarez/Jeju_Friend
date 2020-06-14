package jeju_friend.Elements;

import java.io.*;

public class TouristSpot implements Serializable{
    private static final long serialVersionUID = 1L;
    private String touristSpot;             //관광지 명
    private int areaCode;                   //지역코드
    private String contactInformation;      //연락처
    private String homepage;                //홈페이지
    private String weekdayViewingTime;      //평일관람시간
    private String holidayViewingTime;      //휴일관람시간
    private String closedInformation;       //휴관정보
    private String childAdmissionFee;       //어린이관람료
    private String teenagerAdmissionFee;    //청소년관람료
    private String adultAdmissionFee;       //성인관람료
    private String etc;                     //기타사항
    private String information;             //정보
    private String location;                //위치
    private int recommendedNumber;          //추천수
    private int sortation;                  //구분
    private String imageUrl;               //이미지 주소
    
    public TouristSpot(){}
    
    // getter, setter
    public String getTouristSpot() {
        return touristSpot;
    }

    public void setTouristSpot(String touristSpot) {
        this.touristSpot = touristSpot;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getWeekdayViewingTime() {
        return weekdayViewingTime;
    }

    public void setWeekdayViewingTime(String weekdayViewingTime) {
        this.weekdayViewingTime = weekdayViewingTime;
    }

    public String getHolidayViewingTime() {
        return holidayViewingTime;
    }

    public void setHolidayViewingTime(String holidayViewingTime) {
        this.holidayViewingTime = holidayViewingTime;
    }

    public String getClosedInformation() {
        return closedInformation;
    }

    public void setClosedInformation(String closedInformation) {
        this.closedInformation = closedInformation;
    }

    public String getChildAdmissionFee() {
        return childAdmissionFee;
    }

    public void setChildAdmissionFee(String childAdmissionFee) {
        this.childAdmissionFee = childAdmissionFee;
    }

    public String getTeenagerAdmissionFee() {
        return teenagerAdmissionFee;
    }

    public void setTeenagerAdmissionFee(String teenagerAdmissionFee) {
        this.teenagerAdmissionFee = teenagerAdmissionFee;
    }

    public String getAdultAdmissionFee() {
        return adultAdmissionFee;
    }

    public void setAdultAdmissionFee(String adultAdmissionFee) {
        this.adultAdmissionFee = adultAdmissionFee;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRecommendedNumber() {
        return recommendedNumber;
    }

    public void setRecommendedNumber(int recommendedNumber) {
        this.recommendedNumber = recommendedNumber;
    }

    public int getSortation() {
        return sortation;
    }

    public void setSortation(int sortation) {
        this.sortation = sortation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    //직렬화 및 역 직렬화
    public byte[] toBytes()
    {
        TouristSpot obj = this;
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

    public static TouristSpot toLogin(byte[] serializedTouristSpot)
    {
        TouristSpot spot = null;

        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(serializedTouristSpot);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                spot = (TouristSpot)ois.readObject();
            } catch (Exception e) {}
        }catch(Exception e){}

        return spot;
    }

    public static TouristSpot[] toTouristSpotList(byte[] data){
		TouristSpot[] touristSpotList = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			try {
				ObjectInputStream ois = new ObjectInputStream(bais);
				touristSpotList = (TouristSpot[]) ois.readObject();
			} catch (Exception e) { }
		} catch (Exception e) { }
		return touristSpotList;
	}

	public static byte[] getBytes(TouristSpot[] touristSpotList) {

		byte[] result = null;
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try
            {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(touristSpotList);
                result = baos.toByteArray();
            }catch(Exception e) { }
        }catch(Exception e) { }
		return result;
	}


    // 객체 출력
    public void printInfo()
    {
        String code = this.sortation == 1?"관광지":"음식점";
        System.out.println("------------------------");
        System.out.printf("관광지명 : %s\n지역코드 : %d\n",this.touristSpot,this.areaCode);
        System.out.printf("연락처 : %s\n홈페이지 : %s\n",this.contactInformation,this.homepage);
        System.out.printf("평일관람시간 : %s\n휴일관람시간 : %s\n휴관정보 : %s\n",this.weekdayViewingTime,this.holidayViewingTime,this.closedInformation);
        System.out.printf("어린이관람료 : %s\n청소년관람료 : %s\n성인관람료 : %s\n",this.childAdmissionFee,this.teenagerAdmissionFee,this.adultAdmissionFee);
        System.out.printf("특이사항 : %s\n정보 : %s\n위치 : %s\n",this.etc,this.information,this.location);
        System.out.printf("추천수 : %d\n구분 : %d\n",this.recommendedNumber,code);
    }

}