package kr.bit.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberLogoutController implements Controller{

	@Override
	public String requestHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ctx=request.getContextPath(); // /MVC06가져옴
		//세션을 가져와서 세션을 제거하자
		request.getSession().invalidate(); //1.강제로 하는법
		//2.브라우저를 종료하는것(Jsessionid가 브라우저쿠키에 저장이 되기땜에 끄면 없어짐)
		//3.세션이 종료될때까지 기다리는것(로그인 후 계속 가만히 있으면됨) <-세션 타임아웃 : 30분(1800초)
		return "redirect:"+ctx+"/memberList.do";
	}
}
