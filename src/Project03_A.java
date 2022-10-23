import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.nap.ExcelVO;

public class Project03_A {

	public static void main(String[] args) {
		String fileName = "bookList.xlsx";
		List<ExcelVO> data = new ArrayList<ExcelVO>();
		String[] imsi = new String[5];
		try (FileInputStream fis = new FileInputStream(fileName)){
			//엑셀파일을 FileInputStream과 연결
			//fis 파일인풋스트림(엑셀파일)을 읽어, 메모리에 올리기 -> XSSFWorkbook
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0); //0번쨰 sheet 가져오기
			Iterator<Row> rows= sheet.rowIterator(); //row전체를 Iterator열거형으로 가져오기
			rows.next(); //제목 행 건너뛰기
			while(rows.hasNext()) {
				XSSFRow row = (XSSFRow)rows.next();
				/* cell(칼럼)가져오기 */
				Iterator<Cell> cells = row.cellIterator(); //해당 row에 해당하는 셀의 열거형 가져오기(열정보들)
				int i = 0;
				while(cells.hasNext()) {
					XSSFCell cell = (XSSFCell)cells.next();
					imsi[i] = cell.toString();
					i++;
				}//end cell while
				//vo구조로 묶고,list에 담기
				ExcelVO vo = new ExcelVO(imsi[0], imsi[1], imsi[2],imsi[3],imsi[4]);
				data.add(vo);
			}//end row while
			showExcelData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showExcelData(List<ExcelVO> data) {
		for(ExcelVO vo : data) {
			System.out.println(vo);
		}
	}

}
