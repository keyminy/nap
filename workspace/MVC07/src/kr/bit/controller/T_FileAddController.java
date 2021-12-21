package kr.bit.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class T_FileAddController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String UPLOAD_DIR = "file_repo";
		String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
		File currentDirPath = new File(uploadPath); // 실제 파일의 물리적 경로가됨, 업로드할 경로를 File객체로 만들기
		if(!currentDirPath.exists()) {
			currentDirPath.mkdir();
		}
		//파일을 업로드 할 때 먼저 저장될 임시저장 경로를 설정해야함.  
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath); //실제 파일이 저장될 디렉토리 설정
		factory.setSizeThreshold(1024*1024); //저장될 용량 : 1MB
		
		String fileName = null;
		ServletFileUpload upload = new ServletFileUpload(factory);
		// upload가 파일을 읽어 들일때 발생하는 예외처리 필요
		try {
			List<FileItem> items = upload.parseRequest(request);
			for(int i=0;i<items.size();i++) {
				FileItem fileItem = items.get(i);
				if(fileItem.isFormField()) {
					//파일이 아님
					System.out.println(fileItem.getFieldName()+"="+fileItem.getString("utf-8"));
				}else {
					//파일임
					if(fileItem.getSize()>0) {
						//정상적인 파일, 파일의 이름을 얻어야한다...!
						int idx = fileItem.getName().lastIndexOf("\\");
						if(idx==-1) { idx=fileItem.getName().lastIndexOf("/");	}
						fileName = fileItem.getName().substring(idx+1);
						File uploadFile = new File(currentDirPath+File.separator+fileName);
						//이름 중복체크..
						if(uploadFile.exists()) {
							fileName = System.currentTimeMillis()+"_"+fileName;
							uploadFile = new File(currentDirPath+File.separator+fileName);
						}
						fileItem.write(uploadFile);
					}
				}
			}// end for
		} catch (Exception e) {
			e.printStackTrace();
		}
		//$.ajax()로 업로드된 최종 파일 이름을 전송시켜주기.
		response.setContentType("text/html;charset=euc-kr");
		response.getWriter().print(fileName);
		return null;
	}

}
