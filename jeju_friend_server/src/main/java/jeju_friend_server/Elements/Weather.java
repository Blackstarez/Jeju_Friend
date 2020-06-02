package jeju_friend_server.Elements;

import java.util.Date;
import java.io.*;

public class Weather {
    private int areaCode;   // 지역코드
    private Date day;       // 날짜
    private int rainfallProbability;    // 강수확률
    private String rainfallForm;        // 강수형태
    private int humidity;               // 습도
    private String sky;                 // 대기상태 - 하늘 상태
    private int temperature;            // 기온
    private int lowestTemperature;      // 최저기온
    private int highestTemperature;     // 최고기온
    private String windDirection;       // 풍향
    private int windSpeed;              // 풍속


    //getter, setter
    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getRainfallProbability() {
        return rainfallProbability;
    }

    public void setRainfallProbability(int rainfallProbability) {
        this.rainfallProbability = rainfallProbability;
    }

    public String getRainfallForm() {
        return rainfallForm;
    }

    public void setRainfallForm(String rainfallForm) {
        this.rainfallForm = rainfallForm;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getLowestTemperature() {
        return lowestTemperature;
    }

    public void setLowestTemperature(int lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    public int getHighestTemperature() {
        return highestTemperature;
    }

    public void setHighestTemperature(int highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }


    //직렬화 및 역 직렬화
    public byte[] toBytes()
    {
        Weather obj = this;
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

    public static Weather toLogin(byte[] serializedWeather)
    {
        Weather weather = null;

        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(serializedWeather);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                weather = (Weather)ois.readObject();
            } catch (Exception e) {}
        }catch(Exception e){}

        return weather;
    }

    // 객체 출력
    public void printInfo()
    {
        System.out.printf("지역코드 : %d\n날짜 : %s\n강수확률 : %d\n습도 : %s\n대기상태 : %s\n기온 : %d\n최저기온 : %d\n최고기온 : %d\n풍향 : %s\n풍속 : %d\n"
        ,this.areaCode, this.day, this.rainfallProbability, this.rainfallForm, this.humidity, this.sky, this.temperature, this.lowestTemperature, this.highestTemperature, this.windDirection, this.windSpeed, this.day);

    }
    
}