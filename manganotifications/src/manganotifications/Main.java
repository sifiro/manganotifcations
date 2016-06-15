package manganotifications;
import org.htmlcleaner.*;

import java.net.URL;
import java.nio.charset.Charset;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.ArrayList;
public class Main {
	static ArrayList <Manga> coll = new ArrayList <Manga>();
    final String template="https://www.mangaupdates.com/series.html?id=";
    public Main(){
    }

    public void go() throws Exception{
    	for (int i=0;i<coll.size();i++){
    		System.out.println(get_vol(i));
    	}	
    }
    
    public int get_vol(int i) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, XPatherException, Exception{
    	String a =(xpath(clear(page(template.substring(0)+coll.get(i).getID().substring(0)))));
    	return Integer.parseInt(a.substring(0, a.indexOf(' ')));
    }
    
    public static void main(String[] args)throws Exception {
    	// Add Example Manga
    	coll.add(new Manga("Hayate no Gotoku!","778",0));
    	coll.add(new Manga("Jojo","162",0));
    	coll.add(new Manga("INVALID","99999999",0));
    	new Main().go();
    	
    }
	
    // This Function return a page in format HTML
    public String page(String url) throws Exception {
    System.setProperty("java.protocol.handler.pkgs","javax.net.ssl.HttpsURLConnection");
    URL manga = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection)manga.openConnection();
        InputStreamReader isr = new InputStreamReader(con.getInputStream());
		StringBuilder sb = new StringBuilder();
        BufferedReader page = new BufferedReader(isr);
        //String inputLine;
        int cp;
        while((cp = page.read())!=-1){
                  sb.append((char)cp);
        }
    return sb.toString();
    }
    
	public static String page3(String myURL) {
		System.out.println("crunchifyGetURLContents() is hitting : " + myURL);
		StringBuilder sb = new StringBuilder();
		HttpsURLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(myURL);
			urlConn = (HttpsURLConnection)url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:" + myURL, e);
		}
		
		return sb.toString();
	}
    
	public Document clear(String page) throws XPatherException, ParserConfigurationException{
		CleanerProperties props = new CleanerProperties();
		props.setOmitComments(true);
		HtmlCleaner cleaner = new HtmlCleaner();
    	TagNode la = cleaner.clean(page);
    	return new DomSerializer(new CleanerProperties()).createDOM(la);
	}
	
    public String xpath(Document document) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
    //Hayate no Gotoku Example:
    	String cod="//*[@class='sContent'][7]/text()";
    	XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(cod);
        return expr.evaluate(document, XPathConstants.STRING).toString();
    }
}
