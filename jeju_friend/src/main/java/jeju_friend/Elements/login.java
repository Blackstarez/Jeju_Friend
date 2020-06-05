package jeju_friend.Elements;

import java.io.*;

public class Login implements Serializable, Cloneable{
    // 직렬화 - 역직렬화시 필요한 값 (고정시켜 놓음)
    private static final long serialVersionUID = 1L;
    private String ID;
    private String PW;

    public Login(){}

    public Login(String id, String pw)
    {
        this.ID = id;
        this.PW = pw;
    }

    //getter, setter
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPW() {
		return PW;
	}

	public void setPW(String pW) {
		PW = pW;
    }

    //직렬화 및 역 직렬화
    public byte[] toBytes()
    {
        Login obj = this;
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

    public static Login toLogin(byte[] serializedMember)
    {
        Login member = null;

        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                member = (Login)ois.readObject();
            } catch (Exception e) {}
        }catch(Exception e){}

        return member;
    }

    // 객체 출력
    public void printInfo()
    {
        System.out.printf("ID : %s\nPW : %s\n",this.ID,this.PW);
    }
}