import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class CreateContents {
	private static Map<Integer, String> contents = new TreeMap<Integer, String>(); 
	public static void main(String[] args) throws IOException, DocumentException {
		// TODO Auto-generated method stub
		getFile();
		first();
	}

	public static void getFile() throws IOException{
		BufferedReader sr = new BufferedReader(
				new InputStreamReader(new FileInputStream("d:\\t.txt")));
		String firstline = sr.readLine();
		String [] ss = firstline.split(",");
		int chp = 1;
		for(String n : ss){
			Integer i = Integer.parseInt(n);
			contents.put(i + 1, "Chapter_" + chp++);
		}
		String line;
		while((line=sr.readLine())!=null){
			//System.out.println(line);
			String [] sss = line.split(",");
			Integer num = Integer.parseInt(sss[0]);
			contents.put( num, sss[1] );
		}
		
//		for(String keys : contents.keySet()){
//			System.out.println(keys + " " + contents.get(keys));
//		}
	}
	
	public static void first() throws IOException, DocumentException{

		PdfReader reader = new PdfReader(
				"C:\\Users\\Administrator\\Desktop\\Moment In Peking\\1.PDF");
		Document doc = new Document();
		PdfWriter writer = PdfWriter.getInstance(
				doc, new FileOutputStream("d:\\aaa.pdf"));
		doc.open();
		for(int i = 1; i <= reader.getNumberOfPages() -2; i++){
			Set<Integer> setlit = contents.keySet();
			
			PdfImportedPage image = writer.getImportedPage(reader, i);
			Image image2 = Image.getInstance(image);
			doc.setPageSize(new Rectangle(1.05f*image2.getWidth(), 1.05f*image2.getHeight()));
			doc.newPage();
			if(setlit.contains(i)){
				doc.add(new Chunk(image2, 0, -image2.getHeight()).setLocalDestination(i + ""));
			}
			else{
				doc.add(image2);
			}
		}
		
		PdfContentByte cb = writer.getDirectContent();  
        PdfOutline root = cb.getRootOutline();  
        System.out.println("正在生成目录...");  
        
        for(Integer key : contents.keySet()){
			System.out.println(key + " " + contents.get(key));
			
			PdfOutline oline = new PdfOutline(root,   
                    PdfAction.gotoLocalPage(key + "", false),contents.get(key)); 
		}
        
		
		doc.close();
		
//		System.out.println(reader.getNumberOfPages());
	}
	
}
