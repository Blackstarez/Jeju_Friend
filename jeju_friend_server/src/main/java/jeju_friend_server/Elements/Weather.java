package jeju_friend_server.Elements;

import java.util.Date;
import java.io.*;

enum RainfallForm{없음, 비, 비와눈, 눈, 소나기}     // 강수형태는 0,1,2,3,4 만 사용
enum SkyState {콩, 맑음, 없음 ,구름많음, 흐림}      // 대기상태는 1,3,4만 사용
enum WindDirection {북, 북북동, 북동, 동북동, 동, 동남동, 남동, 남남동, 남, 남남서, 남서, 서남서, 서, 서북서, 북서, 북북서, 북북}


public class Weather {
    private int areaCode;                   // 지역코드
    private Date day;                       // 날짜
    private int time;                       // 시간 : 18:00 의경우 18, 21:00의 경우 21으로 저장
    private int rainfallProbability;        // 강수확률
    private RainfallForm rainfallForm;      // 강수형태
    private int humidity;                   // 습도
    private SkyState sky;                   // 대기상태 - 하늘 상태
    private float temperature;              // 기온
    private float lowestTemperature;        // 최저기온
    private float highestTemperature;       // 최고기온
    private WindDirection windDirection;    // 풍향
    private float windSpeed;                // 풍속


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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRainfallProbability() {
        return rainfallProbability;
    }

    public void setRainfallProbability(int rainfallProbability) {
        this.rainfallProbability = rainfallProbability;
    }

    public RainfallForm getRainfallForm() {
        return rainfallForm;
    }

    public void setRainfallForm(int rainfallForm) {
        this.rainfallForm = RainfallForm.values()[rainfallForm];
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public SkyState getSky() {
        return sky;
    }

    public void setSky(int sky) {
        this.sky = SkyState.values()[sky];
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getLowestTemperature() {
        return lowestTemperature;
    }

    public void setLowestTemperature(float lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    public float getHighestTemperature() {
        return highestTemperature;
    }

    public void setHighestTemperature(float highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = WindDirection.values()[makeWindDirection(windDirection)];
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    // 함수
    public int makeWindDirection(int windDirectionVal)
    {
        return (int)(((double)windDirectionVal+22.5*0.5)/22.5);
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

    public static Weather toWeather(byte[] serializedWeather)
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




    public static Weather[] toWeatherLists(byte[] data){
		Weather[] weatherList = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(data);
			try {
				ObjectInputStream ois = new ObjectInputStream(bais);
				weatherList = (Weather[]) ois.readObject();
			} catch (Exception e) { }
		} catch (Exception e) { }
		return weatherList;
	}

	public static byte[] getBytes(Weather[] WeatherList) {

		byte[] result = null;
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try
            {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(WeatherList);
                result = baos.toByteArray();
            }catch(Exception e) { }
        }catch(Exception e) { }
		return result;
	}

    // 객체 출력
    public void printInfo()
    {
        System.out.printf("지역코드 : %d\n날짜 : %s\n시간 : %d시\n강수확률 : %d %%\n강수형태 : %s\n습도 : %s %%\n대기상태 : %s\n기온 : %f\n최저기온 : %f\n최고기온 : %f\n풍향 : %s\n풍속 : %f\n"
        ,this.areaCode, this.day, this.time, this.rainfallProbability, this.rainfallForm.name(), this.humidity, this.sky.name(), this.temperature, this.lowestTemperature, this.highestTemperature, this.windDirection.name(), this.windSpeed);
    }

   


}