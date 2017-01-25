

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FC {
	public static void main(String [] args) throws IOException{
		useboth();
	}
	private static void useboth() throws IOException {
		Document doc = Jsoup.connect("http://ac.qq.com/VIP").
				userAgent("Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0").
				ignoreContentType(true).timeout(30000).get();
		Elements ele = doc.getElementsByClass("in-works-name");
		
		for (Element element : ele) {
			System.out.println(element.text());
		}
		
	}

}
