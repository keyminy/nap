
public class LastIndex_test {

	public static void main(String[] args) {
		/*2번째 lastIndex얻기*/
		String imgURL = "https://shopping-phinf.pstatic.net/main_3246668/32466681076.20230207163634.jpg";
		String wanted = imgURL.substring(imgURL.lastIndexOf(".",imgURL.lastIndexOf('.')-1)+1);
		System.out.println(wanted);
	}

}
