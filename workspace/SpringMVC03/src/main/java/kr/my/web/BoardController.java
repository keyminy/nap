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
		return "boardList"; //boardList.jsp
	}
	
	@RequestMapping(value="/register.do",method=RequestMethod.GET)
	public String registerGET() {
		
		return "register"; //register.jsp로감(게시글 등록화면)
	}
	
	@RequestMapping(value="/register.do",method=RequestMethod.POST)
	public String registerPOST(BoardVO board) { //게시물 등록하는것
		service.register(board); //db에 게시물 등록이됨
		return "redirect:/list.do"; //게시물 보기로 가기.
	}
	
	@RequestMapping("/get.do") //게시물 상세보기
	public String get(@RequestParam("bno") int bno, Model model) {
		BoardVO board = service.get(bno,"get"); // service.get으로 하나의 게시물이 넘어옴
		model.addAttribute("board",board); //get.jsp로 넘어갈 객체바인딩
		return "get"; //get.jsp로
	}
	
	@RequestMapping(value="/modify.do",method=RequestMethod.GET)
	public String modifyGET(@RequestParam("bno") int bno,Model model) {
		BoardVO board = service.get(bno,"modify"); // service.get으로 하나의 게시물이 넘어옴
		model.addAttribute("board",board); //get.jsp로 넘어갈 객체바인딩
		return "modify"; //modify.jsp
	}
	
	
	@RequestMapping(value="/modify.do",method=RequestMethod.POST)
	public String modifyPOST(BoardVO board) {
		service.modify(board);
		return "redirect:/list.do"; //수정됫으면 리스트로 돌아가기
	}
	
	@RequestMapping("/remove.do")
	public String remove(@RequestParam("bno") int bno) {
		service.remove(bno);
		return "redirect:/list.do";
	}
}
/*
 /list.do --> GET방식 요청 ---> list()
 /register.do --> POST방식 ----> register()
 /read.do ----> GET방식 요청 ---> read()
 /remove.do -----> GET방식 요청 ----> remove()   
 /modify.do ----> POST방식 요청 ----> modify()
*/