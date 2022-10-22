package kr.nap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownLoadBroker implements Runnable{
	private String address;
	private String fileName;
	
	public DownLoadBroker(String address, String fileName) {
		super();
		this.address = address;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		//run에서 다운로드 작업 수행
		try {
			FileOutputStream fos = new FileOutputStream(fileName);//파일이름으로 파일생성
			BufferedOutputStream bos = new BufferedOutputStream(fos);//fos랑 버퍼 연결
			
			URL url = new URL(address);//주소 정보와 연결
			InputStream is = url.openStream(); //inputStream생성
			BufferedInputStream input = new BufferedInputStream(is); //버퍼쓰면 속도 빨라
			
			int data;
			while((data = input.read()) != -1) {
				bos.write(data);
			}
			bos.close();
			input.close();
			System.out.println("download complete...!");
			System.out.println("다운된 fileName : " + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
