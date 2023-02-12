import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Project03_B {
	
	public static void main(String[] args) {
		try {
			Workbook wb = new XSSFWorkbook();//메모리에 가상의 workbook생성
			Sheet sheet = wb.createSheet("My Sample Excel");//Sheet생성
			InputStream is = new FileInputStream("pic.jpg");//이미지 파일 읽기 Input스트림 생성
			byte[] bytes = IOUtils.toByteArray(is);//Stream에 연결된 이미지를 byte[]단위로 reading
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);//이미지를 workboox 메모리에 만들기
			is.close();
			/* Excel에 이미지를 그리기위해, anchor잡기 */
			CreationHelper helper = wb.getCreationHelper();
			//sheet에 실제 이미지 그리기 위해 Drawing객체 생성
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			
			anchor.setCol1(1);
			anchor.setRow1(2);
			
			anchor.setCol2(2);
			anchor.setRow2(3);
			
			/*실제 excel이아닌 가상 객체인 workbook에 그림 그리는 메서드 createPicture */
			Picture pict = drawing.createPicture(anchor, pictureIdx); //anchor위치에, 해당 pictureIdx 그리기
			
			/* cell을 생성해, 이미지를 잘 볼 수 있게, cell의 width와 height 조정 */
			Cell cell = sheet.createRow(2).createCell(1); //2행 1열 cell에 이미지 생성
			//set width n character widths = count characters * 256
			int widthUnits = 20 * 256;//20px정도..
			sheet.setColumnWidth(1, widthUnits);
			//set height to n points in twips = n * 20
			short heightUnits  = 120 * 20;
			cell.getRow().setHeight(heightUnits);
			
			FileOutputStream fileOut = null;
			fileOut = new FileOutputStream("myFile.xlsx");
			wb.write(fileOut);//workbook의 내용을 fileOutputStream에 write하기
			fileOut.close();
			System.out.println("엑셀에 이미지 생성 성공!");
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
