package jeju_friend_server;

import java.sql.*;
import jeju_friend_server.Elements.*;

public class DBmanager {
    private static String DB_ADDRESS = "jdbc:mysql://localhost/jeju?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String DB_ID = "root";
    private static String DB_PW = "root";
    Connection conn = null;
    Statement stmt = null;

    // DB와 연결을 실행하는 함수 - 제일 처음 실행
    public void startDB() 
    {
        try {
            conn = DriverManager.getConnection(DB_ADDRESS, DB_ID, DB_PW);
            System.out.println("\n- MySQL Connction");
            stmt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 로그인
    public boolean getLoginResult(String id, String pw) throws SQLException 
    {
        String sql = "select * from 로그인 where ID ='" + id + "' && PW ='" + pw + "';";
        ResultSet result = stmt.executeQuery(sql);
        if (result != null)
            return true;
        return false;

    }

    // 아이디 조회 - 회원가입시 아이디 중복을 방지하기 위함.
    public boolean isLoginIdExist(String id) throws SQLException 
    {
        String sql = "select * from 로그인 where ID ='" + id + "';";
        ResultSet result;

        result = stmt.executeQuery(sql);
        if(result != null)
            return true;
        return false;
    }

    // 사용자 관리 - 등록, 수정, 삭제, 조회

    // 사용자 등록
    public boolean userApply(String id,String pw, String name, int age, boolean gender)
    {
        char sex = gender==true?'M':'F';
        try{
            String sql = "Insert into user_info (ID,name,age,gender,권한) Values('"+id+"', '"+name+"', '"+age+"', '"+sex+"',0);";
            stmt.executeQuery(sql);
            sql = "Insert into login (ID,PW) Values('"+id+"', '"+pw+");";
            stmt.executeQuery(sql);
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 사용자 조회(개인별)
    public UserInfo userInfoLookup(String id) throws SQLException
    {
        String sql = "select * from user_info where ID = '"+id+"';";
        ResultSet result = stmt.executeQuery(sql);
        UserInfo user = new UserInfo();
        user.setId(result.getString("ID"));
        user.setName(result.getString("name"));
        user.setAge(result.getInt("age"));
        user.setGender(result.getString("gender")=="M"?true:false);
        return user;        
    }
}