package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MemberDAO;
import model.MemberVO;
//6개의 POJO가 해야 할 일들을 -------> 1개의 POJO로 바꾸게됨
public class MemberController {//implements Controller
	//MemberContentController
	@RequestMaping("/memberContent.do")
	public String memberContent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int num = Integer.parseInt(request.getParameter("num"));
		MemberDAO dao = new MemberDAO();
		MemberVO vo = dao.memberContent(num);
		//객체 바인딩
		request.setAttribute("vo", vo);
		//뷰의 이름만 리턴하는 방식으로하면 편하다.
		return "memberContent";
		//return "/WEB-INF/member/memberContent.jsp"; //요렇게 하면 경로바뀌면 모두다 수정해줘야함..
	}
	//MemberDeleteController
	public String memberDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ctx = request.getContextPath(); // /MVC04 가옴
		int num = Integer.parseInt(request.getParameter("num"));
		MemberDAO dao = new MemberDAO();
		int cnt=dao.memberDelete(num);
		String nextPage = null;
		if(cnt>0) {
			nextPage = "redirect:"+ctx+"/memberList.do";
		}else {
			throw new ServletException("not delete");
		}
		return nextPage;
	}
	public String memberInsert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ctx = request.getContextPath(); // /MVC04 가옴
		//회원 가입
		//넘어온 파라매터를 getParameter로 받고 vo로 묶어줬었따.
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		MemberVO vo = new MemberVO(id, pass, name, age, email, phone);
		MemberDAO dao = new MemberDAO();
		int cnt = dao.memberInsert(vo);
		//PrintWriter out = response.getWriter();
		String nextPage = null;
		if(cnt>0) {
			//가입성공
			//out.println("insert success");
			//회원 리스트 보기로 가기.(/MVC01/memberList.do)
			//response.sendRedirect("/MVC04/memberList.do"); sendRedirect는 프론트 컨트롤러가 할 일임
			nextPage = "redirect:"+ctx+"/memberList.do";
		}else {
			//가입실패, 예외객체 만들어서 was에게 던지자.
			throw new ServletException("not insert");
		}
		return nextPage;
	}
	public String memberList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//FrontController가 알바생한테 request와 response를 넘겨줌
		MemberDAO dao = new MemberDAO();
		List<MemberVO> list = dao.memberList();
		//list를 member/memberList.jsp에 줘서 받게 해야한다..
		//jsp가 list를 가져오기위해선 request.setAttribute로 객체바인딩 해야함.
		request.setAttribute("list", list);
		//다음페이지로 넘어가는 정보를 return해주면 Frontcontroller는 nextPage만 받아서 포워딩하면됨
		return "memberList";
	}
	public String memberRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		return "memberRegister";
	}
	//MemberUpdateController
	public String memberUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ctx = request.getContextPath(); // /MVC04 가옴
		//vo의 파라매터 수집
		int num = Integer.parseInt(request.getParameter("num"));
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		MemberVO vo = new MemberVO();
		vo.setNum(num);
		vo.setAge(age);
		vo.setEmail(email);
		vo.setPhone(phone);
		MemberDAO dao = new MemberDAO();
		int cnt = dao.memberUpdate(vo);
		String nextPage = null;
		if(cnt>0) {
			//가입성공
			nextPage="redirect:"+ctx+"/memberList.do";
		}else {
			throw new ServletException("not update");
		}
		return nextPage;
	}
}
