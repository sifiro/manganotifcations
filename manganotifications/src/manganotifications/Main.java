package manganotifications;

import org.htmlcleaner.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
    final String pages[]={"https://www.mangaupdates.com/series.html?id="};
    final String cod[] = {"//*[@class='sContent'][7]/text()"};
    public Main(){
    }

    public void go() throws Exception{
    	Document manga;
    	for (int i=0;i<coll.size();i++){
    		manga = clear(page(pages[0].substring(0)+coll.get(i).getID().substring(0)));
    		System.out.println(coll.get(i).getName()+": "+get_vol(manga,cod[0]));
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
    	coll.add(new Manga("Hayate no Gotoku!","778",0));
    	coll.add(new Manga("Jojo","162",0));
    	coll.add(new Manga("INVALID","99999999",0));
    	new Main().go();
    	
    }
	
    // This Function return a page in format HTML
    public String page(String url) throws Exception {
    try{
    	System.setProperty("java.protocol.handler.pkgs","javax.net.ssl.HttpsURLConnection");
    	URL manga = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection)manga.openConnection();
		StringBuilder sb = new StringBuilder();
        BufferedReader page = new BufferedReader(new InputStreamReader(con.getInputStream()));
        //String inputLine;
        int cp;
        while((cp = page.read())!=-1){
                  sb.append((char)cp);
        }
    return sb.toString();}
    catch(Exception e){
    	System.out.println("ERROR al obtener la pagina");return null;
    }
    }
    
	public Document clear(String page) throws XPatherException, ParserConfigurationException{
		CleanerProperties props = new CleanerProperties();
		props.setOmitComments(true);
		HtmlCleaner cleaner = new HtmlCleaner();
    	return new DomSerializer(new CleanerProperties()).createDOM(cleaner.clean(page));
	}
	
    public String xpath(Document document,String cod) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
    //Hayate no Gotoku Example:
    	XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(cod);
        return expr.evaluate(document, XPathConstants.STRING).toString();
    }
}
