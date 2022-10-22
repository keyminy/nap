import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {
	public static void main(String[] args) {
		String src = "info.json";
		//json파일을 읽기위해 Stream 객체가 필요하다
		//해당 클래스에 있는 resource인 src와 스트림 연결
		InputStream is = Project01_C.class.getResourceAsStream(src);
		if(is==null) {
			throw new NullPointerException("Cannot find resource file");
		}
		/*info.json파일의 내용은 문자열인데, JSON형태로 불러 들어야함 JSONTokener 사용
		 * 하면 문자열을 JSON객체로 변환하여 메모리에 올라옴 */
		JSONTokener tokener = new JSONTokener(is);
		JSONObject object = new JSONObject(tokener);//token을 다시 JSONObject로 변환
		JSONArray students = object.getJSONArray("students"); //JSONArray가져오기
		
		for(int i=0;i<students.length();i++) {
			JSONObject student = (JSONObject)students.get(i);
			System.out.print(student.get("name")+"\t");
			System.out.print(student.get("address")+"\t");
			System.out.println(student.get("phone"));
		}
	}
}
