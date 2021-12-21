package model;
import java.sql.*;
import java.util.ArrayList;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public void getConnect() {
		String URL="jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&serverTimezone=UTC";
		String user="root";
		String password="admin12345";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL,user,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//회원 가입한거 저장
	public int memberInsert(MemberVO vo) {
		String query = "insert into member(id,pass,name,age,email,phone) values(?,?,?,?,?,?)";
		getConnect();
		int cnt=-1;
		try {
			pstmt = con.prepareStatement(query); //미리 컴파일 시킴(속도를 빠르게 하기위해)
			pstmt.setString(1,vo.getId());
			pstmt.setString(2,vo.getPass());
			pstmt.setString(3,vo.getName());
			pstmt.setInt(4,vo.getAge());
			pstmt.setString(5,vo.getEmail());
			pstmt.setString(6,vo.getPhone());
			cnt = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			dbColse();
		}
		return cnt;
	}
	//조회기능
	public ArrayList<MemberVO> memberList(){
		String query = "select * from member";
		getConnect();
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int num= rs.getInt("num");
				String id = rs.getString("id");
				String pass = rs.getString("pass");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				MemberVO vo = new MemberVO(num, id, pass, name, age, email, phone);
				list.add(vo);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbColse();
		}
		return list;
	}
	//삭제메소드
	public int memberDelete(int num) {
		String query = "delete from member where num=?";
		getConnect();
		int cnt=-1;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, num);
			cnt = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbColse();
		}
		return cnt;
	}
	//한 회원의 정보 가져오기
	public MemberVO memberContent(int num) {
		String query = "select * from member where num=?";
		getConnect();
		MemberVO vo = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				//회원 한명의 정보를 가져와서 vo에 묶기.
				num = rs.getInt("num");
				String id = rs.getString("id");
				String pass = rs.getString("pass");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				vo = new MemberVO(num, id, pass, name, age, email, phone);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			dbColse();
		}
		return vo;
	}
	//멤버 정보 수정
	public int memberUpdate(MemberVO vo) {
		String query= "update member set age=?,email=?,phone=? where num=?";
		getConnect();
		int cnt =-1;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, vo.getAge());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPhone());
			pstmt.setInt(4, vo.getNum());
			cnt=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbColse();
		}
		return cnt;
	}
	public void dbColse() {
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) rs.close();
			if(con!=null) con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
