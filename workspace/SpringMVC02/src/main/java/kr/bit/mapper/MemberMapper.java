package kr.bit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.bit.model.MemberVO;

@Mapper //DAO를 거치지 않고 MyBatis(SqlSessionFactory + SqlSession)을 불러옴
public interface MemberMapper {
	@Select("select * from member")
	public List<MemberVO> memberList(); // - SQL id ="memberList"와 같아야한다.
	
	@Insert(" insert into member(id,pass,name,age,email,phone) \r\n" + 
			"    values(#{id},#{pass},#{name},#{age},#{email},#{phone})")
	public int memberInsert(MemberVO vo);
	
	@Delete("delete from member where num=#{num}")
	public int memberDelete(int num);

	@Select("select * from member where num=#{num}")
	public MemberVO memberContent(int num);
	
	@Update("update member \r\n" + 
			"	    set age=#{age},email=#{email},phone=#{phone} \r\n" + 
			"	    where num=#{num}")
	public int memberUpdate(MemberVO vo);
}
