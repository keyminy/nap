package kr.my.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.my.model.BoardVO;
import kr.my.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("/list.do")
	public String list(Model model) {
		List<BoardVO> list = service.getList();
		model.addAttribute("list",list);
		return "boardList";
	}
	
	@RequestMapping(value="/register.do",method=RequestMethod.GET)
	public String registerGET() {
		return "register"; //register.jsp 등록화면으로 가기
	}
	
	@RequestMapping(value="/register.do",method=RequestMethod.POST)
	public String registerPOST(BoardVO board) {
		service.register(board);
		return "redirect:/list.do"; //register.jsp 등록화면으로 가기
	}
	
	@RequestMapping("/get.do")
	public String get(@RequestParam("bno") int bno,Model model) {
		BoardVO board = service.get(bno,"get");
		model.addAttribute("board",board);
		return "get"; // get.jsp로 넘어감
	}
	
	@RequestMapping(value="/modify.do",method=RequestMethod.GET)
	public String modifyGET(@RequestParam("bno") int bno,Model model) {
		BoardVO board = service.get(bno,"modify");
		model.addAttribute("board",board);
		return "modify";
	}
	
	@RequestMapping(value="/modify.do",method=RequestMethod.POST)
	public String modifyPOST(BoardVO board) {
		service.modify(board);
		return "redirect:/list.do";
	}
	
	@RequestMapping("/remove.do")
	public String remove(@RequestParam("bno") int bno) {
		service.remove(bno);
		return "redirect:/list.do";
	}
}
