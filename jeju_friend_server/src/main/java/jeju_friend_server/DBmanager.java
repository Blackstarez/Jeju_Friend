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
    public boolean userApply(String id,String pw, String nickName, int age, boolean gender, int interestArea)
    {
        char sex = gender==true?'M':'F';
        try{
            String sql = "Insert into user_info (ID,nickName,age,gender,권한,관심지역) Values(?,?,?,?,?,?);";
            PreparedStatement prestmt = conn.prepareStatement(sql);
            prestmt.setString(1,id);
            prestmt.setString(2,nickName);
            prestmt.setInt(3, age);
            prestmt.setString(4, String.valueOf(sex));
            prestmt.setInt(5,0);
            prestmt.setInt(6,interestArea);
            prestmt.executeUpdate();

            sql = "Insert into login (ID,PW) Values(?,?);";
            prestmt = conn.prepareStatement(sql);
            prestmt.setString(1,id);
            prestmt.setString(2,pw);
            prestmt.executeUpdate();
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
        user.setNickName(result.getString("nickName"));
        user.setAge(result.getInt("age"));
        user.setGender(result.getString("gender")=="M"?true:false);
        user.setInterestArea(result.getInt("관심지역"));
        return user;        
    }


    // 관광지 조회
    public TouristSpot[] touristSpotLookup() throws SQLException
    {
        String sql = "select * from 관광지 where 구분 = 1";
        ResultSet results = stmt.executeQuery(sql);
        results.last();
        int rowCount = results.getRow();
        results.beforeFirst();
        TouristSpot[] spotList = new TouristSpot[rowCount];
        int index = 0;
        while(results.next())
        {
            spotList[index] = new TouristSpot();
            spotList[index].setTouristSpot(results.getString("관광지명"));
            spotList[index].setAreaCode(results.getInt("지역코드"));
            spotList[index].setContactInformation(results.getString("연락처"));
            spotList[index].setLocation(results.getString("위치"));
            spotList[index].setInformation(results.getString("정보"));
            spotList[index].setChildAdmissionFee(results.getString("어린이관람료"));
            spotList[index].setTeenagerAdmissionFee(results.getString("청소년관람료"));
            spotList[index].setAdultAdmissionFee(results.getString("성인관람료"));
            spotList[index].setHomepage(results.getString("홈페이지"));
            spotList[index].setWeekdayViewingTime(results.getString("평일관람시간"));
            spotList[index].setHolidayViewingTime(results.getString("휴일관람시간"));
            spotList[index].setSortation(results.getInt("구분"));
            spotList[index].setClosedInformation(results.getString("휴관정보"));
            spotList[index].setEtc(results.getString("특이사항"));
            spotList[index].setRecommendedNumber(results.getInt("추천수"));
            index++;
        }
        return spotList;
    }

    // 음식점 조회
    public TouristSpot[] foodSpotLookup() throws SQLException
    {
        String sql = "select * from 관광지 where 구분 = 2";
        ResultSet results = stmt.executeQuery(sql);
        results.last();
        int rowCount = results.getRow();
        results.beforeFirst();
        TouristSpot[] spotList = new TouristSpot[rowCount];
        int index = 0;
        while(results.next())
        {
            spotList[index] = new TouristSpot();
            spotList[index].setTouristSpot(results.getString("관광지명"));          // 음식점명으로 사용된다.
            spotList[index].setAreaCode(results.getInt("지역코드"));
            spotList[index].setContactInformation(results.getString("연락처"));
            spotList[index].setLocation(results.getString("위치"));
            spotList[index].setInformation(results.getString("정보"));
            spotList[index].setSortation(results.getInt("구분"));
            spotList[index].setEtc(results.getString("특이사항"));
            spotList[index].setRecommendedNumber(results.getInt("추천수"));
            index++;
        }
        return spotList;
    }

    // 여행계획 조회
    public TourPlan[] tourPlanLookup(String id) throws SQLException
    {
        String sql = "select * from '여행일정' where ID = '"+id+"';";
        ResultSet results = stmt.executeQuery(sql);
        results.last();
        int rowCount = results.getRow();
        results.beforeFirst();
        TourPlan[] tourPlanList = new TourPlan[rowCount];
        int index = 0;
        while(results.next())
        {
            tourPlanList[index] = new TourPlan();
            tourPlanList[index].setUserId(results.getString("ID"));
            tourPlanList[index].setTourPlanName(results.getString("여행명"));
            tourPlanList[index].setTourWith(results.getInt("동행코드"));
            tourPlanList[index].setTourForm(results.getString("여행목적"));
            tourPlanList[index].setAreaInterest(results.getInt("관심지역"));
            index++;
        }
        return tourPlanList;
    }



    // 지역코드 - 격자 x, 격자 y 조회
    public int[] getXY(int areaCode) throws SQLException
    {
        String sql = "select X, Y from 지역코드 where 지역코드.지역코드 = '"+areaCode+"';";
        ResultSet result = stmt.executeQuery(sql);
        int[] xy = new int[2];
        xy[0] = result.getInt("X");
        xy[1] = result.getInt("Y");
        return xy;
    }
}