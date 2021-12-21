package kr.my.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.my.model.BoardVO;

//영속계층 만들기
@Mapper
public interface BoardMapper { //XML Mapper file과 연동시키자
	public List<BoardVO> getList(); //게시물 리스트 가져오기
	public void insert(BoardVO board); //게시물 등록
	public BoardVO read(int bno); //게시물의 번호를 받아 게시물 상세보기
	public int delete(int bno); //게시물 번호받아 게시물 삭제
	public int update(BoardVO board); //게시물 수정
	public void count(int bno); //조회수 증가
}
