package manganotifications;

// Get_web Stuff
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

//HTMLCleaner Stuff
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.XPatherException;

//XPath Stuff
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.util.ArrayList;
public class Main {
	static ArrayList <Manga> coll = new ArrayList <Manga>();
	//Maybe I Need implement a ArrayList with websites supported (?)
	//WebSites Supported
    final String pages[]={"https://www.mangaupdates.com/series.html?id="};
    //XPath for-each website 
    final String cod[] = {"//*[@class='sContent'][7]/text()"};
    public Main(){
    }

    public void go() throws Exception{
    	Document manga;
    	for (int i=0;i<coll.size();i++){
    		manga = clear(page(pages[coll.get(i).getSite()].substring(0)+coll.get(i).getID().substring(0)));
    		System.out.println(coll.get(i).getName()+": "+get_vol(manga,cod[coll.get(i).getSite()]));
    	}	
    }
    
    public int get_vol(Document i,String cod) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, XPatherException, Exception{
    	String a =xpath(i,cod);
    	try{
    	if (! a.isEmpty()){
    	return Integer.parseInt(a.substring(0, a.indexOf(' ')));}
    	else{System.out.println("No se ha obtenido ningun valor de la pagina");return -1;}
    	}
    	catch(Exception e){
    		System.out.println("Error al Convertir a Numerico");return -1;
    	}
    }
    
    public static void main(String[] args)throws Exception {
    	// Add Example Manga
    	coll.add(new Manga(0,"Hayate no Gotoku!","778",0,0));
    	coll.add(new Manga(0,"Jojo","162",0,0));
    	coll.add(new Manga(0,"INVALID","99999999",0,0));
    	new Main().go();
    	
    }
	
    // This Function return a page in format HTML
    // @param url in a String
    // @return a website into a String
    public String page(String url) throws Exception {
    try{
    	System.setProperty("java.protocol.handler.pkgs","javax.net.ssl.HttpsURLConnection");
    	URL manga = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection)manga.openConnection();
		StringBuilder sb = new StringBuilder();
        BufferedReader page = new BufferedReader(new InputStreamReader(con.getInputStream()));
        int cp;
        while((cp = page.read())!=-1){
                  sb.append((char)cp);
        }
    return sb.toString();}
    catch(Exception e){
    	System.out.println("ERROR al obtener la pagina");return null;
    }
    }
    
    //Clear a HTML Page not well-formed to a w3c Document
    //@param HTML Page in a String (You can use page function for obtain it)
    //@return A w3c Document
	public Document clear(String page) throws ParserConfigurationException{
		CleanerProperties props = new CleanerProperties();
		props.setOmitComments(true);
		HtmlCleaner cleaner = new HtmlCleaner();
    	return new DomSerializer(new CleanerProperties()).createDOM(cleaner.clean(page));
	}
	
    public String xpath(Document document,String cod) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
    	XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(cod);
        return expr.evaluate(document, XPathConstants.STRING).toString();
    }
}
