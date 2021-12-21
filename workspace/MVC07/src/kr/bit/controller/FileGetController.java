package kr.bit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileGetController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filename = request.getParameter("filename");
		System.out.println(filename);
		String UPLOAD_DIR = "file_repo"; //여기서 다운로드 받기
		String uploadPath=request.getServletContext().getRealPath("")+File.separator+UPLOAD_DIR;//물리적 경로 가져오기
		File f = new File(uploadPath+"\\"+filename); //파일 객체만들기
		filename=URLEncoder.encode(filename,"UTF-8");//클라이언트로 넘어오는 파일의 한글이 깨지지않게
		filename=filename.replace("+", " "); //파일이름의 +를 공백처리로 바꿔주기 , 크롬에는 공백이 +기호로 바껴서 다운로드 되는 경우가 잇으므로
		
		//다운로드 준비(서버가 클라이언트에게 다운로드 받을 수 잇게해줘야 : response), 다운로드 창을 띄움,공식적으로 쓰기..
		response.setContentLength((int)f.length()); //파일의 길이가 얼만지 클라이언트 헤더에게 응답
		//f.length() : long타입 -> int로 바꿈
		//내려보내는 데이터의 타입이뭔지? file일때는 application/x-msdownload; 마임타입설정
		response.setContentType("application/x-msdownload;charset=utf-8");//어플리케이션에서 다운로드 하겠다., attachment할때 한글이 꺠지지않게 utf-8설정
		//밑에 다운로드 화면을 만들어주는거 response.setHeader
		response.setHeader("Content-Disposition", "attachment;filename="+filename+";"); //attachment : 첨부파일
		response.setHeader("Content-Transfer-Encoding", "binary");//파일은 바이너리 데이터로 인코딩되어 클라이언트에게 전송되어야하므로
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");

		//실제 다운로드를 하는 부분 IO
		FileInputStream in = new FileInputStream(f);//업로드된 폴더에서 파일을 읽어야하므로 fileinputStream
		OutputStream out = response.getOutputStream();
		byte[] buffer = new byte[1024];//1024byte씩 버퍼를 만들거 출력
		while(true) {
			int count=in.read(buffer); //1024byte씩 버퍼 읽기
			if(count==-1) {
				break;
			}
			out.write(buffer, 0, count);//다운로드 0%~100%까지 다운로드 되는부분
		}//end while
		in.close();
		out.close();
		return null;
	}

}
