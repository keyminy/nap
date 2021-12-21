package kr.narp.myapp1;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.bit.mapper.MemberMapper;
import kr.bit.model.MemberVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemberMapper memberMapper;
	
	//private MemberDAO dao; 이제 DAO없다
	
	@RequestMapping("/memberList.do")
	public String memberList(Model model) {
		//MemberDAO dao = new MemberDAO(); 하지말자.
		List<MemberVO> list=memberMapper.memberList();
		model.addAttribute("list",list);
		return "memberList";
	}
	@RequestMapping("/memberInsert.do")
	public String memberInsert(MemberVO vo) {

		int cnt = memberMapper.memberInsert(vo);
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
		int cnt = memberMapper.memberDelete(num);
		return "redirect:/memberList.do";
	}
	@RequestMapping("/memberContent.do")
	public String memberContent(int num,Model model) {
		MemberVO vo = memberMapper.memberContent(num);
		//vo를 가지고 memberContent로 가야한다.. 객체바인딩 필요
		model.addAttribute("vo",vo);
		return "memberContent";
	}
	@RequestMapping("memberUpdate.do")
	public String memberUpdate(MemberVO vo) {
		int cnt = memberMapper.memberUpdate(vo);
		return "redirect:/memberList.do";
	}
	@RequestMapping("memberAjaxList.do")
	public @ResponseBody List<MemberVO> memberAjaxList() {
		List<MemberVO> list=memberMapper.memberList();
		//$.ajax()의 callback함수로 응답(Json형태로 응답해줘야함..)
		return list;
	}
	@RequestMapping("/form.do")
	public String form() {
		return "uploadForm";
	}
}
