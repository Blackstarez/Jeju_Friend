package jeju_friend;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException;

import jeju_friend.Elements.Weather;


public class APIManager {
    private final String weatherkey= "q3XgjsnsW%2BPm%2FflDB3%2FwM%2B9kF1zrmWAiwe7zjU99saeCWbsx6fX5oIBe%2FvyVvoYElCIegVEvwgDNntaoKGL8dA%3D%3D";
    public APIManager() {   }
    public String getDay()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return format.format(date);
    }
    public String getHour()
    {
        SimpleDateFormat format = new SimpleDateFormat("HH");
        Date date = new Date();
        System.out.println("현재 시각 : "+format.format(date));
        int result = Integer.parseInt(format.format(date));
        if((result+1)/3 == result/3)
            result = (result/3)*3-1;
        System.out.println("보정 시각 : "+Integer.toString(result));
        return Integer.toString(result);
        
    }

    public ArrayList<Weather> getWeather(String jsonData) throws java.text.ParseException
    {
        ArrayList<Weather> weatherInfoList = new ArrayList<Weather>();
        try { JSONParser jsonParse = new JSONParser(); 
            JSONObject jsonObj = (JSONObject) jsonParse.parse(jsonData);  
            JSONObject jsonObj2 = (JSONObject)jsonParse.parse(jsonObj.get("response").toString());
            JSONObject jsonObj3 = (JSONObject)jsonParse.parse(jsonObj2.get("body").toString());
            JSONObject jsonObj4 = (JSONObject)jsonParse.parse(jsonObj3.get("items").toString());
            System.out.println("json item : "+jsonObj3.get("item"));
            

            JSONArray weatherArray =  (JSONArray)jsonObj4.get("item");
            
            boolean isEqual = true;
            String fcstTime = new String();
            Weather tmp = null;
            DateFormat df = new SimpleDateFormat("yyyymmdd");
            for(int i=0; i < weatherArray.size(); i++) 
            { 
                JSONObject weatherObject = (JSONObject) weatherArray.get(i);
                
                
                /*System.out.println("======== weather : " + i + " ========"); 
                
                System.out.println("날짜 : "+weatherObject.get("fcstDate")); 
                System.out.println("시각 : "+weatherObject.get("fcstTime"));
                System.out.println("분류 : "+weatherObject.get("category"));
                System.out.println("값 : "+weatherObject.get("fcstValue"));*/

                 
                if(i==0)
                {
                    tmp = new Weather();
                    fcstTime = String.valueOf(weatherObject.get("fcstTime"));
                }

                if(fcstTime.compareTo(String.valueOf(weatherObject.get("fcstTime"))) == 0)
                    isEqual = true;
                else
                {
                    isEqual = false;
                    fcstTime = String.valueOf(weatherObject.get("fcstTime"));
                }

                
                
                if(!isEqual)
                {
                    System.out.println("시간이 같지 않음");
                    weatherInfoList.add(tmp);
                    tmp = new Weather();
                    //weatherInfoList.get(weatherInfoList.size()-1).printInfo();
                }
                
                
                tmp.setDay(df.parse(String.valueOf(weatherObject.get("fcstDate"))));
                tmp.setTime(Integer.parseInt(String.valueOf(weatherObject.get("fcstTime")))/100);
                String category = String.valueOf(weatherObject.get("category"));
                switch(category)
                {
                    case "POP": // 강수확률
                        tmp.setRainfallProbability(Integer.parseInt(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    case "PTY": // 강수형태
                        tmp.setRainfallForm(Integer.parseInt(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    case "REH": // 습도
                        tmp.setHumidity(Integer.parseInt(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    case "SKY": // 하늘상태
                        tmp.setSky(Integer.parseInt(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    case "T3H": // 3시간 기온
                        tmp.setTemperature(Float.parseFloat(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    case "TMN": // 아침 최저기온
                        tmp.setLowestTemperature(Float.parseFloat(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    case "TMX": // 낮 최고기온
                        tmp.setHighestTemperature(Float.parseFloat(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    case "VEC": // 풍향
                        tmp.setWindDirection(Integer.parseInt(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    case "WSD": // 풍속
                        tmp.setWindSpeed(Float.parseFloat(String.valueOf(weatherObject.get("fcstValue"))));
                        break;
                    default:
                        continue;
                }

                if(i == weatherArray.size()-1)
                {
                    weatherInfoList.add(tmp);
                    //weatherInfoList.get(weatherInfoList.size()-1).printInfo();
                }
            } 
        } catch (ParseException e) { e.printStackTrace(); }

        return weatherInfoList;
    }

    public String jsonParsor(String jsonData)
    {
        try { JSONParser jsonParse = new JSONParser(); 
            JSONObject jsonObj = (JSONObject) jsonParse.parse(jsonData);  
            JSONObject jsonObj2 = (JSONObject)jsonParse.parse(jsonObj.get("response").toString());
            JSONObject jsonObj3 = (JSONObject)jsonParse.parse(jsonObj2.get("body").toString());
            JSONObject jsonObj4 = (JSONObject)jsonParse.parse(jsonObj3.get("items").toString());
            System.out.println("json item : "+jsonObj3.get("item"));
            

            JSONArray weatherArray =  (JSONArray)jsonObj4.get("item"); 
            for(int i=0; i < weatherArray.size(); i++) 
            { 
                System.out.println("======== weather : " + i + " ========"); 
                JSONObject weatherObject = (JSONObject) weatherArray.get(i); 
                System.out.println("날짜 : "+weatherObject.get("fcstDate")); 
                System.out.println("시각 : "+weatherObject.get("fcstTime"));
                System.out.println("분류 : "+weatherObject.get("category"));
                System.out.println("값 : "+weatherObject.get("fcstValue"));
            } 
        } catch (ParseException e) { e.printStackTrace(); }

        return "result";
    }


    public String requestWeather(int x, int y) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(
                "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /* URL */
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + this.weatherkey); /* Service Key */
        urlBuilder
                .append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 페이지번호 */
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
                + URLEncoder.encode("1000", "UTF-8")); /* 한 페이지 결과 수 */
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
                + URLEncoder.encode("JSON", "UTF-8")); /* 요청자료형식(XML/JSON)Default: XML */
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
                + URLEncoder.encode(this.getDay(), "UTF-8")); /* yyyymmdd발표 */
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
                + URLEncoder.encode(this.getHour()+"00", "UTF-8")); /* HH시 발표 */
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "="
                + URLEncoder.encode(Integer.toString(x), "UTF-8")); /* 예보지점 X 좌표값 */
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "="
                + URLEncoder.encode(Integer.toString(y), "UTF-8")); /* 예보지점의 Y 좌표값 */
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        return sb.toString();
    }

    public Weather[] getWeatherInfo(int x, int y) throws IOException
    {
        APIManager a = new APIManager();
        String weatherJson = a.requestWeather(x,y);
        Weather[] weatherInfo = null;
        try {
            ArrayList<Weather> weatherInfoList = a.getWeather(weatherJson);
            weatherInfo = new Weather[weatherInfoList.size()];
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return weatherInfo;
    }

    public static void main(String[] args) throws IOException {
        APIManager a = new APIManager();
        String weatherJson = a.requestWeather(91,134);
        a.jsonParsor(weatherJson);
        try {
            ArrayList<Weather> weatherInfoList = a.getWeather(weatherJson);
            for(Weather w : weatherInfoList)
            {
                System.out.println("\n-------------------------");
                w.printInfo();
                System.out.println("-------------------------\n");
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }
}