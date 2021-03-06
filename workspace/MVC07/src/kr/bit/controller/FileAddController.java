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

public class FileAddController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String UPLOAD_DIR = "file_repo"; //업로드할 경로
		String uploadPath=request.getServletContext().getRealPath("")+File.separator+UPLOAD_DIR;
		//uploadPath = D:\nap\eGovFrame-3.10.0\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\MVC07\\file_repo
		File currentDirPath=new File(uploadPath); // 실제 파일의 물리적 경로가됨, 업로드할 경로를 File객체로 만들기
		if(!currentDirPath.exists()) {
			currentDirPath.mkdir();
		} 
		//파일을 업로드 할 때 먼저 저장될 임시저장 경로를 설정해야함.  
		DiskFileItemFactory factory = new DiskFileItemFactory(); //임시저장 메모리경로
		factory.setRepository(currentDirPath);  //실제 파일이 저장될 디렉토리 설정
		factory.setSizeThreshold(1024*1024); //저장될 용량 설정 1MB
		
		String fileName = null;
		//request(client로부터 넘어온 파일이있음)에서 Data를 좀더 쉽게 꺼내기위해 사용하는 클래스
		ServletFileUpload upload = new ServletFileUpload(factory); //실제 파일 업로드는 factory에다 파일 업로드
		//upload가 파일을 읽어들일떄 예외가 발생가능하므로
		try { //items에는 [  ],[  ],[  ] 여기 []하나당 FileItem이라 부름
			List<FileItem> items=upload.parseRequest(request); //request에 저장된 파일 정보를 쉽게 읽어올수있게하는 parseRequest
			//request안에 여러개의 파일이 업로드 된 경우를 고려한것 그래서 List로 받어
			for(int i=0;i<items.size();i++) {
				FileItem fileItem = items.get(i); //하나의 파일정보 [ ] 가 담김
				//fileItem에는 폼의 파일(바이너리데이터) or 파라매터 일수도 있음.
				if(fileItem.isFormField()) {//넘어온 데이터가 파라메터일떄(=폼필드) 
					System.out.println(fileItem.getFieldName()+"="+fileItem.getString("utf-8"));
					//그냥 필드 이름과 값을 출력할게 , 지정한 인코딩으로 파일아이템 내용 반환
				}else { //넘어온게 파일이 맞을떄
					if(fileItem.getSize()>0) { //사이즈가 0보다 커야 정상적인 파일
						int idx = fileItem.getName().lastIndexOf("\\");//getName은 전체 이름 가져오는거므로, 맨뒤에서부터 \\찾기
						if(idx==-1) {idx = fileItem.getName().lastIndexOf("/");} //리눅스일때는 /부터 찾기
						fileName = fileItem.getName().substring(idx+1); // 실제 파일 이름이 가져와짐
						File uploadFile = new File(currentDirPath+"\\"+fileName);//임시로 만든 디렉토리에 파일 만들기,파일객체 생성
						//임시 디렉토리에서 실제 디렉토리로 옮겨오는 과정임
						if(uploadFile.exists()) { //파일 중복체크
							fileName = System.currentTimeMillis()+"_"+fileName;//uploadFile이 중복되면 이름을 바꿔주기
							uploadFile = new File(currentDirPath+"\\"+fileName);
						}
						fileItem.write(uploadFile); //임시경로에서 새로운 파일 경로로 쓰기 , 우리는 임시파일 = 새로운파일경로 같음
					}
				}
			}//end for
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//$.ajax()로 업로드된 최종파일이름을 응답해주기(db에 저장해줘야하므로)
		response.setContentType("text/html;charset=euc-kr"); //응답파일이름 : 한글 이름이 있을수 잇으므로
		response.getWriter().print(fileName);
		return null;
	}

}
