package jeju_friend_server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException;


public class APIManager {
    private final String key= "q3XgjsnsW%2BPm%2FflDB3%2FwM%2B9kF1zrmWAiwe7zjU99saeCWbsx6fX5oIBe%2FvyVvoYElCIegVEvwgDNntaoKGL8dA%3D%3D";
    public APIManager()
    {
        
    }
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
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + this.key); /* Service Key */
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

    public static void main(String[] args) throws IOException {
        APIManager a = new APIManager();
        a.jsonParsor(a.requestWeather(91, 134));
    }
}