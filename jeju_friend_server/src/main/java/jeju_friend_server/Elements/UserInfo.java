package jeju_friend_server.Elements;

import java.io.*;

public class UserInfo {
    private static final long serialVersionUID = 1L;
    private String id;
    private String pw;
    private String nickName;        // 닉네임
    private boolean gender;         // true : 남성   false : 여성
    private int age;                // 연령

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

    public String getNickname() {
        return nickName;
    }

    public void setNickname(String nickName) {
        this.nickName = nickName;
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

    public static UserInfo toUser(byte[] serializedMember)
    {
        UserInfo user = null;

        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember);
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