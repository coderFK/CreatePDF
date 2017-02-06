
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfWriter;

public class ToPDF3 {
	
	private static Set<String> file_list;
	private static List<String> str_list;
	private static String dirPath;
	private static String targetPath;
	
	public static void main(String [] args) throws Exception{
		getFilePath();
		getFileList();
		createPDF();
	}
	
	private static void getFilePath() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("输入需转化为PDF的总文件夹路径");
			dirPath = reader.readLine();
			System.out.println("输入PDF文件的保存路径和PDF名称（如D:\\example.pdf）");
			targetPath = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private static void getFileList(){
		file_list = new TreeSet<String>(new MyTreeSetCmp());
		File dir_1 = new File(dirPath);
		if(dir_1.exists() && dir_1.isDirectory()){
			System.out.println("正在解析文件...");
			for(String dir_2 : dir_1.list()){
				if(dir_2.endsWith(".jpg")){
					file_list.add(dir_2);
				}
			}
			
		}
		else{
			System.out.println("文件夹名称错误，请输入正确文件夹路径!");
		}
	}
	
	private static void createPDF() throws Exception{
		
		str_list = new ArrayList<String>();
		Document document = new Document();
		PdfWriter  writer = PdfWriter.getInstance(document,
				new FileOutputStream(targetPath));
		document.open();
		
		for(String file : file_list){
			
			Image png = Image.getInstance(dirPath + "\\"+file);
			document.setPageSize(new Rectangle(1.1f*png.getWidth(), 1.1f*png.getHeight()));
			document.newPage();
			document.add(png);	
			
			System.out.println("正在添加" + file);
		}
          
        PdfContentByte cb = writer.getDirectContent();  
        PdfOutline root = cb.getRootOutline();  
        System.out.println("正在生成目录...");  
        for(String file_name : str_list){
        	PdfOutline oline = new PdfOutline(root,   
                    PdfAction.gotoLocalPage(file_name, false),file_name);  
        }
        document.close();
        System.out.println("转化完成！");
	}
}


class MyTreeSetCmp implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		if(o1.length() < o2.length()){
			return -1;
		}
		else if(o1.length() > o2.length()){
			return 1;
		}
		else{
			return o1.compareTo(o2);
		}
	}

	
}
