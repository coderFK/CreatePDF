
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfWriter;

public class Main {
	
	private static List<File> file_list;
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
			System.out.println("输入需转化为PDF的总文件夹路径（总文件夹中有子文件夹，可以子文件夹名称生成目录）");
			dirPath = reader.readLine();
			System.out.println("输入PDF文件的保存路径和PDF名称（如D:\\example.pdf）");
			targetPath = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private static void getFileList(){
		file_list = new ArrayList<File>();
		File dir_1 = new File(dirPath);
		if(dir_1.exists() && dir_1.isDirectory()){
			System.out.println("正在解析文件...");
			for(File dir_2 : dir_1.listFiles()){
				if(dir_2.isDirectory()){
					file_list.add(dir_2);
				}
			}
		}
		else{
			System.out.println("文件夹名称错误，请输入正确文件夹路径!");
		}
	}
	
	private static void createPDF() throws Exception{
		Document document = new Document();
		PdfWriter  writer = PdfWriter.getInstance(document,
				new FileOutputStream(targetPath));
		document.open();
		
		for(File file : file_list){
			int flag = 1;
			for(File img : file.listFiles()){
				String img_path = img.getAbsolutePath();
				if(img_path.endsWith("png") || img_path.endsWith("jpg") ){
					Image png = Image.getInstance(img_path);
					
					document.newPage();
					if(flag == 1){
						//如果是第一页则添加至目录且定位
						document.add(new Chunk(png,0,-png.getHeight()).
								setLocalDestination(file.getName()));
					}
					else{
						document.add(png);	
					}
					
					flag++;
				}
			}
			System.out.println("正在添加" + file.getName() + "...");
		}
		
          
        PdfContentByte cb = writer.getDirectContent();  
        PdfOutline root = cb.getRootOutline();  
        System.out.println("正在生成目录...");  
        for(File file : file_list){
        	PdfOutline oline = new PdfOutline(root,   
                    PdfAction.gotoLocalPage(file.getName(), false),file.getName());  
        }
        
        document.close();
        System.out.println("转化完成！");
	}
}
