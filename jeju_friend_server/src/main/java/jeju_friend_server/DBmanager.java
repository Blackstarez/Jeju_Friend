package Jeju_Friend_server;

import java.sql.*;

public class DBmanager {
    private static String DB_ADDRESS = "jdbc:mysql://localhost/DB이름?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static String DB_ID = "admin";
    private static String DB_PW = "admin";
    Connection conn = null;
    Statement stmt = null;

    //DB와 연결을 실행하는 함수 - 제일 처음 실행
    public void startDB()
    {
        try{
            conn = DriverManager.getConnection(DB_ADDRESS, DB_ID, DB_PW);
            System.out.println("\n- MySQL Connction");
            stmt = conn.createStatement();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // 로그인
    public void getLoginInfo(String ID, String PW) throws SQLException
    {
        String sql = "select * from 로그인 where ID ='"+ID+"' && PW ='"+PW+"';";
        ResultSet result = stmt.executeQuery(sql);

    }

    //
}