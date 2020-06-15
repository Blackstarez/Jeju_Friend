package jeju_friend.Elements;

import java.io.*;

public class LocationRecommend implements Serializable, Cloneable{
    // 직렬화 - 역직렬화시 필요한 값 (고정시켜 놓음)
    private static final long serialVersionUID = 1L;
    private int age;                 // 나이
    private boolean isMale;             // 성별
    private String locationName;        // 추천지역

    public LocationRecommend(){}



    //getter, setter
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    //직렬화 및 역 직렬화
    public byte[] toBytes()
    {
        LocationRecommend obj = this;
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

    public static LocationRecommend toRecommend(byte[] serializedRecommend)
    {
        LocationRecommend recommend = null;

        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(serializedRecommend);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                recommend = (LocationRecommend)ois.readObject();
            } catch (Exception e) {}
        }catch(Exception e){}

        return recommend;
    }

    // 객체 출력
    public void printInfo()
    {
        System.out.printf("나이 : %s\n성별 : %s\n추천지역 : %s\n",this.age,this.isMale==true?"남성":"여성",this.locationName);
    }


}