package prac;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberVO;

/**
 * Servlet implementation class ForwardController
 */
@WebServlet("/fc.do")
public class ForwardController extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.forward실습
		String name="park";
		int age =45;
		String email = "aaa@empal.com";
		//이 3개의 변수를 VO에 묶고 vo객체를 setAttribute해서 넘기기
		MemberVO vo = new MemberVO();
		vo.setName(name);
		vo.setAge(age);
		vo.setEmail(email);
		request.setAttribute("vo", vo);
		//forward시켜서 보내기
		RequestDispatcher rd = request.getRequestDispatcher("view/forward.jsp");
		rd.forward(request, response);
		
		//View page(result.jsp)로 data를 전달하여 View page에서 출력해줌.
		//Controller에서 View로 페이지 전환하는 방법은
		//1.redirect -> response.sendRedirect(""); url이 바뀐다.
		//2.forward가 있음 
		//request.setAttribute("data", data); //객체 바인딩
		//response.sendRedirect("view/result.jsp?name="+name+"&age="+age+"&email="+email);
		
	}

}
