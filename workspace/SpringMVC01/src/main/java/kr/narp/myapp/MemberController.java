package kr.narp.myapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.bit.model.MemberDAO;
import kr.bit.model.MemberVO;

@Controller
public class MemberController {
	@Autowired
	private MemberDAO dao;
	
	@RequestMapping("/memberList.do")
	public String memberList(Model model) {
		//MemberDAO dao = new MemberDAO(); 하지말자.
		List<MemberVO> list=dao.memberList();
		model.addAttribute("list",list);
		return "memberList";
	}
	@RequestMapping("/memberInsert.do")
	public String memberInsert(MemberVO vo) {

		int cnt = dao.memberInsert(vo);
		return "redirect:/memberList.do";
	}
	@RequestMapping("/memberRegister.do")
	public String memberRegister() {
		return "memberRegister";
	}
	@RequestMapping("memberDelete.do")
	public String memberDelete(@RequestParam("num") int num) {
		//넘어온 파라매터 ${ctx}/memberDelete.do?num=
		//위와 같이 int num으로 하면 자동으로 받아짐
		int cnt = dao.memberDelete(num);
		return "redirect:/memberList.do";
	}
	@RequestMapping("/memberContent.do")
	public String memberContent(int num,Model model) {
		MemberVO vo = dao.memberContent(num);
		//vo를 가지고 memberContent로 가야한다.. 객체바인딩 필요
		model.addAttribute("vo",vo);
		return "memberContent";
	}
	@RequestMapping("memberUpdate.do")
	public String memberUpdate(MemberVO vo) {
		int cnt = dao.memberUpdate(vo);
		return "redirect:/memberList.do";
	}
}
