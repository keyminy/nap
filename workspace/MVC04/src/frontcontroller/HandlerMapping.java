package frontcontroller;

import java.util.HashMap;

import controller.Controller;
import controller.MemberController;


public class HandlerMapping {
	private HashMap<String,Controller> mappings;
	public HandlerMapping() {
		mappings = new HashMap<String,Controller>();
		mappings.put("/memberList.do", new MemberController());
		mappings.put("/memberInsert.do", new MemberController());
		mappings.put("/memberRegister.do", new MemberController());
		mappings.put("/memberContent.do", new MemberController());
		mappings.put("/memberUpdate.do", new MemberController());
		mappings.put("/memberDelete.do",new MemberController());
	}
	//키에 해당하는 value인 pojo넘겨주기
	public Controller getController(String key) {
		return mappings.get(key);
	}
}
