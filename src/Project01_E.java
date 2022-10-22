import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class Project01_E {
	//지도 이미지 생성 메서드
	public static void map_service(String point_x,String point_y,String address) {
		String URL_STATTICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
		String client_id = "h693kfismq";
		String client_secret = "XQW27Ehs6f08vvVvA4usrrEqlCsgDALtUKYO7sug";
		try {
			String pos = URLEncoder.encode(point_x+" "+point_y,"UTF-8");
			String url = URL_STATTICMAP;
			url += "center=" + point_x + "," + point_y;
			url += "&level=16&w=700&h=500";
			url += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address,"UTF-8");
			
			URL u = new URL(url);
			HttpURLConnection con = (HttpURLConnection)u.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
			
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode==200) {
				//성공 시, png나 jpg형식의 이미지 binary로 날라옴
				InputStream is = con.getInputStream(); //IO 읽기
				int read = 0;
				byte[] bytes = new byte[1024];//이미지를 byte[] 단위로 읽기
				String tempName = Long.valueOf(new Date().getTime()).toString();
				File f = new File(tempName+".jpg");
				f.createNewFile();
				OutputStream outputStream = new FileOutputStream(f);
				while((read = is.read(bytes)) != -1) {
					//read담에 byte[]배열 인수를 적어서 byte[]단위로 읽게함
					outputStream.write(bytes,0,read);
				}
				is.close();
			}else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				System.out.println(response.toString());
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

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
			
			//<추가된 부분> x : 경도, y : 위도, z : 주소
			String x = ""; String y = ""; String z = "";
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
				//추가된 부분
				x = (String)temp.get("x");
				y = (String)temp.get("y");
				z = (String)temp.get("roadAddress");
			}
			//추가된 부분 함수 호출
			map_service(x,y,z);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
