package kr.narp.myapp1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileController {
	
	@RequestMapping("/upload.do")
	public String upload(MultipartHttpServletRequest multipartRequest,HttpServletRequest request,Model model) throws Exception{
		String UPLOAD_DIR = "repo"; //업로드할 경로,만들어 져잇지 않으면 새로 만들어짐
		String uploadPath=request.getServletContext().getRealPath("")+File.separator+UPLOAD_DIR;
		
		//id, name을 받아 서버에 넘겨서 다운로드 페이지에 이 사람 정보 넘기기.
		Map map = new HashMap(); //(key:parameter이름,value:파라매터의 값) 해쉬맵에다가 클라이언트로 부터 넘어온 정보 받기
		//String id = multipartRequest.getParameter("id"); // 111 이라는 value
		//String name = multipartRequest.getParameter("name"); // 111이라는 value
		Enumeration<String> e=multipartRequest.getParameterNames(); // 파라매터 이름인 name="id", name="name" 가져 올 수있다.
		while(e.hasMoreElements()) {
			String name = e.nextElement();
			String value = multipartRequest.getParameter(name);
			//System.out.println(name+ " : " + value); //테스트
			map.put(name,value); //id와 name이 들어감
		}
		//파일을 담고 있는 파라매터 읽어오기
		Iterator<String> it=multipartRequest.getFileNames(); //업로드한 파일 이름이 아니라 parameter이름인 file1,file2이거 읽는거
		List<String> fileList = new ArrayList<String>();
		while(it.hasNext()) {
			String paramfName = it.next();
			//System.out.println(paramfName);
			MultipartFile mFile =  multipartRequest.getFile(paramfName); //하나의 파일에는 여러 정보가 있다.. (이름,타입,크기.....) 받는 타입이 따로 있다.
			String oName = mFile.getOriginalFilename(); //업로드된 실제 파일 이름
			System.out.println("oName : " + oName);
			fileList.add(oName); //fileLIst에 file이름을 저장
			//파일을 업로드할 경로가 UPLOAD_DIR이 존재하는지 확인
			File file = new File(uploadPath+"\\"+paramfName); //file1 
			if(mFile.getSize()!=0) {
				//0이아니다 = 파일을 첨부했다.
				if(!file.exists()) { //파일이 존재하지 않으면
					if(file.getParentFile().mkdir()) { //상위 경로로 간 다음에 디렉토리생성,성공하면 true
						file.createNewFile(); //그 디렉토리안에다 임시로 파일 paramfName을 생성함
					}
				}
				//디렉토리 repo가 다 만들어졋으면 실제 파일인 mFile을 업로드 폴더에다가 써야겟지
				mFile.transferTo(new File(uploadPath+"\\"+oName)); //이동하겠다.Write함(파일 업로드됨)
			}
		}
		map.put("fileList", fileList); //파일 정보가 들어감
		model.addAttribute("map",map);
		return "result";
	}
	
	@RequestMapping("/download.do")
	public void download(@RequestParam("filename") String filename, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String UPLOAD_DIR = "repo"; //repo에서 다운로드 받기
		String uploadPath=request.getServletContext().getRealPath("")+File.separator+UPLOAD_DIR;//물리적 경로 가져오기
		File f = new File(uploadPath+"\\"+filename); //파일 객체만들기
		filename=URLEncoder.encode(filename,"UTF-8");//클라이언트로 넘어오는 파일의 한글이 깨지지않게
		filename=filename.replace("+", " "); //파일이름의 +를 공백처리로 바꿔주기 , 크롬에는 공백이 +기호로 바껴서 다운로드 되는 경우가 잇으므로
		//다운로드 준비(서버가 클라이언트에게 다운로드 받을 수 잇게해줘야 : response), 다운로드 창을 띄움,공식적으로 쓰기..
		response.setContentLength((int)f.length()); //파일의 크기가 얼만지 클라이언트 헤더에게 응답
		response.setContentType("application/x-msdownload;charset=utf-8");//어플리케이션에서 다운로드 하겠다., attachment할때 한글이 꺠지지않게 utf-8설정
		response.setHeader("Content-Disposition", "attachment;filename="+filename+";"); //attachment : 첨부파일
		response.setHeader("Content-Transfer-Encoding", "binary");//파일은 바이너리 데이터로 인코딩되어 클라이언트에게 전송되어야하므로
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		//실제 다운로드를 하는 부분 IO
		FileInputStream in = new FileInputStream(f);//업로드된 폴더에서 파일을 읽어야하므로 fileinputStream
		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[1024];//1024byte씩 버퍼를 만들거 출력
		while(true) {
			int count=in.read(buffer);
			if(count==-1) {
				break;
			}
			out.write(buffer, 0, count);//다운로드 0%~100%까지 다운로드 되는부분
		}//end while
		in.close();
		out.close();

	}
}
