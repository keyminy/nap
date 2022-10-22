import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class Project01_D {

	public static void main(String[] args) {
		String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
		String client_id = "h693kfismq";
		String client_secret = "XQW27Ehs6f08vvVvA4usrrEqlCsgDALtUKYO7sug";
		//키보드로부터 주소 입력 받기
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("주소를 입력하세요 : ");
			String address = io.readLine();
			//공백을 끝으로 인식해버리기 땜에 입력의 공백도 인코딩하여 문자처리
			String addr = URLEncoder.encode(address,"UTF-8");
			String reqUrl = apiURL + addr;
			
			//URL객체 생성(유효한 URL이면 연결 객체 생성)
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
			
			int responseCode = con.getResponseCode(); //connection이 정상적이면 200
			
			BufferedReader br;
			if(responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			}else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String line;
			StringBuffer response = new StringBuffer();
			while((line=br.readLine())!=null) {
				response.append(line);
			}
			br.close();
			
			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject object = new JSONObject(tokener);
			System.out.println(object.toString(2));
			
			JSONArray arr = object.getJSONArray("addresses");
			for(int i=0; i<arr.length(); i++) {
				JSONObject temp = (JSONObject)arr.get(i);
				System.out.println("address : " + temp.get("roadAddress"));
				System.out.println("jibunAddress : " + temp.get("jibunAddress"));
				System.out.println("경도 : " + temp.get("x"));
				System.out.println("위도 : " + temp.get("y"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
