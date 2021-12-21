package kr.bit.model;
// JDBC->myBatis, JPA

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
public class MemberDAO {
  private static SqlSessionFactory sqlSessionFactory; //메모리에 저장된 커넥션 풀을 가리킴 [O O O O]
  //초기화 블럭 static 블럭 - 프로그램 실행시 딱 한번만 실행되는 코드 영역임
  static {
	  try {
		String resource = "kr/bit/mybatis/config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	} catch (Exception e) {
	e.printStackTrace();
	}
  }
  //회원 전체 리스트 보기
  public List<MemberVO> memberList() {
	  //만들어진 Connection을 sqlSessionFactory에서 빼내오기 
	  SqlSession session = sqlSessionFactory.openSession();
	  List<MemberVO> list=session.selectList("memberList");
	  session.close();
	  return list;
  }
  //회원가입(Insert)
  public int memberInsert(MemberVO vo) {
	  SqlSession session = sqlSessionFactory.openSession();
	  int cnt = session.insert("memberInsert", vo);
	  session.commit();
	  session.close();
	  return cnt;
  }
  //회원 삭제
  public int memberDelete(int num) {
	  SqlSession session = sqlSessionFactory.openSession();
	  int cnt = session.delete("memberDelete",num);
	  session.commit();
	  session.close();
	  return cnt;
  }
  //회원 상세보기
  public MemberVO memberContent(int num) {
	  SqlSession session = sqlSessionFactory.openSession();
	  MemberVO vo = session.selectOne("memberContent", num);
	  session.close();
	  return vo;
  }
  //회원 수정하기
  public int memberUpdate(MemberVO vo) {
	  SqlSession session = sqlSessionFactory.openSession();
	  int cnt = session.update("memberUpdate",vo);
	  session.commit();
	  session.close();
	  return cnt;
  }
}


