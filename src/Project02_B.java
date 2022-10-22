import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kr.nap.DownLoadBroker;

public class Project02_B {

	public static void main(String[] args) {
		String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BodyMatter?qt_ty=QT1";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//키보드로 부터 읽음
		try {
			System.out.print("[입력->년(yyyy)-월(mm)-일(dd)] : ");
			String bible = br.readLine();
			url += "&Base_de=" + bible + "&bibleType=1";
			System.out.println("===============================");
			Document doc = Jsoup.connect(url).post(); //Post방식으로 연결
			Element bible_text = doc.select(".bible_text").first();
			System.out.println(bible_text.text());
			
			Element bibleinfo_box = doc.select(".bibleinfo_box").first();
			System.out.println(bibleinfo_box.text());
			
			Elements liList = doc.select(".body_list > li");
			for(Element li : liList) {
				System.out.print(li.select(".num").first().text() + " : ");
				System.out.println(li.select(".info").first().text());
			}
			//리소스 다운로드(mp3,image)
			/* mp3 다운받기 */
//			Element tag = doc.select("source").first();
//			String dPath = tag.attr("src").trim();
//			System.out.println("dPath : " + dPath); //https://meditation.su.or.kr/meditation_mp3/2022/20221022.mp3
//			String fileName = dPath.substring(dPath.lastIndexOf("/")+1);
			
			/* 이미지 다운로드*/
			Element imgTag = doc.select(".img > img").first();
			String iPath = "https://sum.su.or.kr:8888" + imgTag.attr("src").trim();
			System.out.println("iPath : " + iPath); //https://sum.su.or.kr:8888/attach/X07/9b7b128a76c140c48553f19115c3d7a7.jpg
			String ifileName = iPath.substring(iPath.lastIndexOf("/")+1);
			
			/* 해당 path와 file이름을 넘겨 다운로드 할 수 있는 스레드 생성 */
			Runnable r = new DownLoadBroker(iPath,ifileName);
			Thread dLoad = new Thread(r);//Thread하는 작업이 r
			dLoad.start();
			/* 쓰레드가 다운 받는 동안 10초 딜레이 */
			for(int i=0;i<10;i++) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.print((i+1) + " ");
			}
			System.out.println();
			System.out.println("===============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
