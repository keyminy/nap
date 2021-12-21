package kr.bit.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.bit.model.MemberDAO;

public class MemberDbcheckController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// $.ajax()로 부터 id가 넘어옴
		String id = request.getParameter("id"); // { "id" : id }
		MemberDAO dao = new MemberDAO();
		String dbDouble = dao.memberDbcheck(id); 
		response.getWriter().print(dbDouble); //POJO가 ajax()함수에 만들어 놓은 콜백함수로 응답이 된다.
		return null; //다른 jsp페이지로 바뀌지않도록
	}

}
