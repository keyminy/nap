import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Project03_C {
	public static void main(String[] args) {
		String fileName = "cellDataType.xlsx";
		try(FileInputStream fis = new FileInputStream(fileName)) {
			XSSFWorkbook workbook = new XSSFWorkbook(fis); //excel파일 읽어서 메모리에 workbook객체 생성
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows =  sheet.rowIterator();
			while(rows.hasNext()) {
				XSSFRow row = (XSSFRow)rows.next();
				Iterator<Cell> cells =  row.cellIterator();
				while(cells.hasNext()) {
					XSSFCell cell = (XSSFCell)cells.next();
					/* cell의 type이 다를 땐, cell의 데이터를 가지고 오는 메서드가 다르다!! */
					CellType type = cell.getCellType();
					if(type == CellType.STRING) {
						System.out.println("[" + cell.getRowIndex() + ", "
								+ cell.getColumnIndex() + "] = STRING; Value = "
								+ cell.getRichStringCellValue().toString());
					}else if(type == CellType.NUMERIC) {
						System.out.println("[" + cell.getRowIndex() + ", "
								+ cell.getColumnIndex() + "] = NUMERIC; Value = "
								+ cell.getNumericCellValue());						
					}else if(type == CellType.BOOLEAN) {
						System.out.println("[" + cell.getRowIndex() + ", "
								+ cell.getColumnIndex() + "] = BOOLEAN; Value = "
								+ cell.getBooleanCellValue());
					}else if(type == CellType.BLANK) {
						System.out.println("[" + cell.getRowIndex() + ", "
								+ cell.getColumnIndex() + "] = BLANK CELL");	
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
