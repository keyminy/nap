package kr.nap;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ExcelDAO {
	private List<ExcelVO> list;
	private XSSFWorkbook wb;
	
	public ExcelDAO() {
		list=new ArrayList<ExcelVO>();
		wb=new XSSFWorkbook();
	}
    public void excel_input() {
    	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    	try {
            XSSFSheet firstSheet = wb.createSheet("BOOK SHEET");
            XSSFRow rowA = firstSheet.createRow(0); //첫 row생성(제목행)
            XSSFCell cellA = rowA.createCell(0);
            cellA.setCellValue(new XSSFRichTextString("책제목"));
            XSSFCell cellB = rowA.createCell(1);
            cellB.setCellValue(new XSSFRichTextString("저자"));
            XSSFCell cellC = rowA.createCell(2);
            cellC.setCellValue(new XSSFRichTextString("출판사"));
            XSSFCell cellD = rowA.createCell(3);
            cellD.setCellValue(new XSSFRichTextString("isbn"));
            XSSFCell cellE = rowA.createCell(4);
            cellE.setCellValue(new XSSFRichTextString("이미지이름"));            
            XSSFCell cellF = rowA.createCell(5);
            cellF.setCellValue(new XSSFRichTextString("이미지"));

            int i=1;
            while(true) {
	        	System.out.print("책제목:");
	    		String title=br.readLine();
	    		System.out.print("책저자:");
	    		String author=br.readLine();
	    		System.out.print("출판사:");
	    		String company=br.readLine();
	    		
	            XSSFRow rowRal = firstSheet.createRow(i);//i=1 -> 제목 다음 행인 2행부터 시작	            
	            XSSFCell cellTitle = rowRal.createCell(0);            
	            cellTitle.setCellValue(new XSSFRichTextString(title));	            
	            XSSFCell cellAuthor = rowRal.createCell(1);
	            cellAuthor.setCellValue(new XSSFRichTextString(author));	            
	            XSSFCell cellCompany = rowRal.createCell(2);
	            cellCompany.setCellValue(new XSSFRichTextString(company));	            
	            i++;
	            
	            ExcelVO vo=new ExcelVO(title, author, company);
	            // isbn, image 검색
	            ExcelVO data=naver_search(vo);
	            list.add(data);
	            System.out.print("계속입력 하시면 Y / 입력종료 N:");
	            String key=br.readLine();
	            if(key.equals("N")) break;
	        }
            System.out.println("데이터 추출중...........");
            excel_save();
		} catch (Exception e) {
            e.printStackTrace();
		}
    }		
	//상세 검색은 책 제목(d_titl), 저자명(d_auth), 목차(d_cont), ISBN(d_isbn), 출판사(d_publ) 5개 항목 중에서 1개 이상 값을 입력해야함.
    public ExcelVO naver_search(ExcelVO vo) {
		try {
			String URL_STATICMAP = "https://openapi.naver.com/v1/search/book_adv.xml?d_titl="+URLEncoder.encode(vo.getTitle(), "UTF-8")+"&d_auth="+URLEncoder.encode(vo.getAuthor(), "UTF-8")+"&d_publ="+URLEncoder.encode(vo.getCompany(), "UTF-8");
	        URL url = new URL(URL_STATICMAP);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", "afmoqJ4zSQhUkgBEmCCF");
            con.setRequestProperty("X-Naver-Client-Secret", "R0sx9s2mFf");
            int responseCode = con.getResponseCode();
            
            BufferedReader br;            
            if(responseCode==200) { 
                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            } else {  
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer(); //문자열 추가 변경시 사용            
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            Document doc = Jsoup.parse(response.toString());
            //isbn
            Element isbn = doc.select("isbn").first();
            System.out.println("isbn: "+ isbn.text());
            vo.setIsbn(isbn.text());
            
            //img
            String imgDoc = doc.toString();
            String imgTag=imgDoc.substring(imgDoc.indexOf("<img>")+5);
        	String imgURL = imgTag.substring(0,imgTag.indexOf("<author>"));
            System.out.println(imgURL);
			//String fileName = imgURL.substring(imgURL.indexOf(".",imgURL.lastIndexOf("/")+1)+1);
            String fileName = imgURL.substring(imgURL.indexOf("/")+1);
			//String fileName = imgURL;
			System.out.println("fileName : " + fileName);
            vo.setImgurl(fileName);   
            System.out.println("vo : " + vo);
            // DownloadBroker            
            Runnable dl=new DownLoadBroker(imgURL, fileName);
            Thread t=new Thread(dl);
            t.start();
         } catch (Exception e) {
            System.out.println(e);
        }
		return vo;
	}//naver_search
	
 public void excel_save() {
		  try {
		  XSSFSheet sheet = wb.getSheetAt(0);
		   if(wb != null && sheet != null) {
			  Iterator rows = sheet.rowIterator();
	          rows.next();
	          int i=0; // list의 index
		      while (rows.hasNext()) {
		    	    XSSFRow row = (XSSFRow) rows.next();
	               	/* isbn,img열에 값 넣어주기 */
		    	    XSSFCell cell=row.createCell(3);//3열	
	               	cell.setCellType(CellType.STRING);
					cell.setCellValue(list.get(i).getIsbn());				
					
					cell=row.createCell(4);//4열	
					cell.setCellType(CellType.STRING);
					cell.setCellValue(list.get(i).getImgurl());
					
		    	    InputStream inputStream = new FileInputStream(list.get(i).getImgurl());
		    	    byte[] bytes = IOUtils.toByteArray(inputStream);

		    	    int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);//이미지 생성
		    	    inputStream.close();
				        
					CreationHelper helper = wb.getCreationHelper();
					Drawing drawing = sheet.createDrawingPatriarch();
					ClientAnchor anchor = helper.createClientAnchor();
					   
				    anchor.setCol1(5); //Column B
			    	anchor.setRow1(i+1); //Row 3
			    	anchor.setCol2(6); //Column C
			    	anchor.setRow2(i+2); //Row 4

			    	Picture pict = drawing.createPicture(anchor, pictureIdx);
			    	Cell cellImg = row.createCell(5);
			    	int widthUnits = 20*256; 
			    	sheet.setColumnWidth(5, widthUnits);
			    	short heightUnits = 120*20; // 1/20
			    	cellImg.getRow().setHeight(heightUnits);

				    i++;
		        }		
				FileOutputStream fos = new FileOutputStream("isbn.xlsx");
				wb.write(fos); //workbook에 있는 가상 엑셀파일을 write
				fos.close();
				System.out.println("ISBN,ImageURL 저장성공");
		   }
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
	}
}