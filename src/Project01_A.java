import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.nap.BookDTO;

public class Project01_A {

	public static void main(String[] args) {
		//Ojbect(BookDTO객체) -> JSON(String)
		BookDTO dto = new BookDTO("자바",21000,"에이콘",670);
		Gson g = new Gson();
		String json = g.toJson(dto);
		System.out.println(json); // {"title":"자바","price":21000,"company":"에이콘","page":670}
		
		//JSON(String) -> Object(BookDTO)
		BookDTO dto1 = g.fromJson(json,BookDTO.class);
		System.out.println(dto1.toString()); //BookDTO [title=자바, price=21000, company=에이콘, page=670]
		System.out.println(dto1.getTitle()+"\t"+dto1.getPrice());
		
		// Object(List<BookDTO>) -> JSON(String) : [{   },{   }, .... , {   }]
		List<BookDTO> list = new ArrayList<>();
		list.add(new BookDTO("자바1",21000,"에이콘1",570));
		list.add(new BookDTO("자바2",31000,"에이콘2",670));
		list.add(new BookDTO("자바3",11000,"에이콘3",370));
		
		String gList = g.toJson(list);
		System.out.println(gList);
		/*[{"title":"자바1","price":21000,"company":"에이콘1","page":570},{"title":"자바2","price":31000,"company":"에이콘2","page":670}
		 * ,{"title":"자바3","price":11000,"company":"에이콘3","page":370}] */
		
		/* JSON(String) -> Object(List<BookDTO>) */
		List<BookDTO> list1 =g.fromJson(gList,new TypeToken<List<BookDTO>>(){}.getType());
		list1.stream().forEach(System.out::println);
	}
}
