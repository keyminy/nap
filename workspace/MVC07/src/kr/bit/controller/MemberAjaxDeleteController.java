package kr.bit.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.bit.model.MemberDAO;

public class MemberAjaxDeleteController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// data : {"num" : num} 받는다.
		int num=Integer.parseInt(request.getParameter("num"));
		MemberDAO dao=new MemberDAO();
		int cnt=dao.memberDelete(num);
		response.getWriter().print(cnt); //cnt는 형식상 response한것 역할없음
		return null;
	}

}
