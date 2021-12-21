package kr.bit.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.bit.model.MemberDAO;

public class FileDeleteController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ctx = request.getContextPath();
		String filename = request.getParameter("filename");
		int num = Integer.parseInt(request.getParameter("num"));
		filename = URLEncoder.encode(filename,"UTF-8"); //넘어온 파일 이름 한글이 꺠지지 않도록 해주세요
		filename = filename.replace("+"," "); //파일 이름이 + 기호가 있는 경우 공백으로 
		String UPLOAD_DIR = "file_repo"; //업로드하는 실제 경로임
		String uploadPath= request.getServletContext().getRealPath("")+File.separator+UPLOAD_DIR; //실제 업로드하는 물리적인 경로를 가져오기.
		File file = new File(uploadPath+"\\"+filename);//파일 객체 만들기
		if(file.exists()) {
			file.delete();
			System.out.println("디렉토리에서 파일 삭제");
		}
		//테이블에서도 파일을 null처리하기(update)
		MemberDAO dao = new MemberDAO();
		dao.memberDeleteFile(num);
		
		return "redirect:"+ctx+"/memberContent.do?num="+num; //현재 가지고 있는 number를 가지고 삭제 후 Content로 되돌아가야함.
	}
	
}
