package frontcontroller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;

import java.util.*;

import model.MemberDAO;
import model.MemberVO;

@WebServlet("*.do")
public class MemberFrontController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");//넘어오는 한글데이터 꺠지지 않게 하기위해
		// FC : 클라이언트가 어떤 요청을 했는지 파악하기
		String url = request.getRequestURI();
		String ctx = request.getContextPath();

		//실제로 요청한 명령이 무엇인지 memberInsert.do만 뽑아내보자.
		String command = url.substring(ctx.length());
		System.out.println("command : " + command);
		//요청에 따른 분기작업 해주면 됨.
		Controller controller = null;
		String nextPage = null;
		HandlerMapping mapping = new HandlerMapping();
		controller = mapping.getController(command); //HandlerMapping에 command보냄
		//nextPage = controller. //메소드가 다 달라서 문제가 된다..
		
		/*
		if(command.equals("/memberList.do")) {
			//회원리스트 보기
			controller =new MemberListController();
			nextPage = controller.requestHandler(request, response);
		}else if(command.equals("/memberInsert.do")) {
			controller =new MemberInsertController();
			nextPage = controller.requestHandler(request, response);
		}else if(command.equals("/memberRegister.do")) {
			controller =new MemberRegisterController();
			nextPage = controller.requestHandler(request, response);
			
			//회원 가입 화면
			RequestDispatcher rd = request.getRequestDispatcher("member/memberRegister.html");
			//dispatcher로하면 form action ../memberInsert.do 안해도됨
			rd.forward(request, response);
		}else if(command.equals("/memberContent.do")) {
			controller =new MemberContentController();
			nextPage = controller.requestHandler(request, response);
		}else if(command.equals("/memberUpdate.do")) {
			controller =new MemberUpdateController();
			nextPage = controller.requestHandler(request, response);

		}else if(command.equals("/memberDelete.do")) {
			controller =new MemberDeleteController();
			nextPage = controller.requestHandler(request, response);
		}//if end*/
		//forward와 Redirect를 구분
		if(nextPage!=null) {
			if(nextPage.indexOf("redirect:")!=-1) {
				response.sendRedirect(nextPage.split(":")[1]);
			}else {//포워딩일때
				RequestDispatcher rd = request.getRequestDispatcher(ViewResolver.makeView(nextPage));
				rd.forward(request, response);
			}
		}
	}

}
