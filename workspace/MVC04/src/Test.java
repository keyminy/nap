
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "redirect:/MVC04/memberList.do";
		String[] parsed = url.split(":");
		for(int i=0;i<parsed.length;i++) {
			System.out.println(parsed[i]);
		}
		System.out.println(url.indexOf("redirect"));
	}

}
