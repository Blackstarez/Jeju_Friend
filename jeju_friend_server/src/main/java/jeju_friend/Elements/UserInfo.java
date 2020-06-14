package jeju_friend.Elements;

import java.io.*;

public class UserInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String pw;
    private String nickName;        // 닉네임
    private boolean gender;         // true : 남성   false : 여성
    private int age;                // 연령
    private int interestArea;       // 관심지역 지도상으로 12시방향부터 시계방향으로 1~5

    public UserInfo(){}
    

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getInterestArea() {
        return interestArea;
    }

    public void setInterestArea(int interestArea) {
        this.interestArea = interestArea;
    }

    //직렬화 및 역직렬화
    public byte[] toBytes()
    {
        UserInfo obj = this;
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

    public static UserInfo toUser(byte[] serializedUserInfo)
    {
        UserInfo user = null;

        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(serializedUserInfo);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                user = (UserInfo)ois.readObject();
            } catch (Exception e) {}
        }catch(Exception e){}

        return user;
    }

    // 객체 출력
    public void printInfo()
    {
        System.out.printf("ID : %s\n닉네임 : %s\n나이 : %d\n성별 : %s\n",this.id, this.nickName, this.age, this.gender==true?"남성":"여성");
    }


}