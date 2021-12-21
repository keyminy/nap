package kr.bit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.bit.model.*;

public class MemberAjaxListController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		List<MemberVO> list = dao.memberList();
		//ArrayList -> json으로 바꾸는 API : Gson API
		Gson g = new Gson();
		String json = g.toJson(list);
		System.out.println("list : " + list);
		System.out.println(json); // [{		}, {	}......]
		// $.ajax()로 dataType : json형태로 응답해줘야함.
		response.setContentType("text/json;charset=euc-kr");
		response.getWriter().print(json); //ajax로 json데이터 응답해주면됨
		return null; //다른 페이지로 가면 안되니까
	}

}
