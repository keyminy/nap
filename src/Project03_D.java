import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kr.nap.ExcelVO;

public class Project03_D {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("책 제목 : ");
			String title = br.readLine();
			System.out.print("책 저자 : ");
			String author = br.readLine();
			System.out.print("출판사 : ");
			String company = br.readLine();
			
			ExcelVO vo = new ExcelVO(title, author, company);
			getIsbnImage(vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void getIsbnImage(ExcelVO vo) {
		try {
			String openApi = "https://openapi.naver.com/v1/search/book_adv.xml?d_titl="
					+ URLEncoder.encode(vo.getTitle(),"UTF-8")
					+ "&d_auth=" + URLEncoder.encode(vo.getAuthor(),"UTF-8")
					+ "&d_publ" + URLEncoder.encode(vo.getCompany(),"UTF-8");
			URL url = new URL(openApi);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", "afmoqJ4zSQhUkgBEmCCF");
			con.setRequestProperty("X-Naver-Client-Secret", "R0sx9s2mFf");
			int responseCode = con.getResponseCode(); //200이면 성공
			
			BufferedReader br1;
			if(responseCode == 200) {
				br1 = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			}else {
				br1 = new BufferedReader(new InputStreamReader(con.getErrorStream()));				
			}
			String inputLine;
			StringBuffer response = new StringBuffer(); //row 한 줄 씩 읽어서 추가하여 xml문서 형식으로 저장
			
			while((inputLine = br1.readLine()) != null) {
				response.append(inputLine);
			}
			br1.close();
			/*Java의 정석
			 * 남궁성
			 * 도우출판 */
			//System.out.println("response : " + response.toString());
			
			/* isbn,image 정보 뽑아내기 */
			Document doc = Jsoup.parse(response.toString());
			//System.out.println(doc.toString());
			Element total =  doc.select("total").first();
			//System.out.println(total.text()); //892
			if(!(total.text().equals("0"))) {
				//isbn
				Element isbn = doc.select("isbn").first();
				System.out.println("isn : " + isbn.text());
				vo.setIsbn(isbn.text());
				
				//img
				String imgDoc = doc.toString();
				String imgTag = imgDoc.substring(imgDoc.indexOf("<img>")+5);
				String imgURL = imgTag.substring(0,imgTag.indexOf("<author>"));
				String fileName = imgURL.substring(imgURL.indexOf(".",imgURL.lastIndexOf("/")+1)+1);
				System.out.println("fileName : " + fileName);
				vo.setImgurl(fileName);
				
				System.out.println("vo : " + vo);
			}else {
				System.out.println("검색 데이터가 없습니다!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
