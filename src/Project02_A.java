import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Project02_A {

	public static void main(String[] args) {
		String url = "https://sports.news.naver.com/wfootball/index.nhn";
		Document doc = null;
		try {
			//요청한 결과를 받는 타입은 Document클래스 타입 입니다.
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//주요 뉴스로 나오는 태그를 찾아서 가져오도록 한다.
		Elements element = doc.select("div.good_news");

		//1.헤더 부분의 제목 가져오기
		String title = element.select("h2").text();
		System.out.println("=================================================");
		System.out.println(title);
		System.out.println("=================================================");
		
		for(Element e1 : element.select("li")) {
			System.out.println(e1.select("a").text());
		}
		System.out.println("===============================================");
	}
}
