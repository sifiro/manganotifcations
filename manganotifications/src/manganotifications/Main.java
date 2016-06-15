package manganotifications;

import java.net.URL;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import java.security.*;
public class Main {
    final String template="https://www.mangaupdates.com/series.html?id=";
    String url;
    public Main(String ID){
    url=template.substring(0)+ID.substring(0);
    }

    public static void main(String[] args)throws Exception {
        Main manga = new Main("778");
        manga.page();
    }
	
    public BufferedReader page() throws Exception {
    System.setProperty("java.protocol.handler.pkgs","javax.net.ssl.HttpsURLConnection");
    URL manga = new URL(this.url);
        HttpsURLConnection con = (HttpsURLConnection)manga.openConnection();
        InputStreamReader isr = new InputStreamReader(con.getInputStream());
        BufferedReader page = new BufferedReader(isr);
        String inputLine;
        
        while((inputLine = page.readLine())!=null){
                  System.out.println(inputLine);
        }
    return page;
    }
    
    public void xpath(){
    //Hayate no Gotoku Example:
    // /html/head/meta/meta/meta/meta/link/link/link/link/link/body/div/table/tbody/tr/tr/td/table/tbody/tr/td/td/tr/td/table/tbody/tr/td/table/tbody/tr/tr/td/form/input/table/tbody/tr/td/img/br/br/br/br/br/td/table/tbody/tr/td/div/tr/td/table/tbody/tr/td/div/div/div/div/br/br/div/br/br/div/br/br/br/br/br/div/br/br/br/br/div/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/br/div/br/br/br/div
    
    }
}
