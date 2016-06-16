package manganotifications;

public class Manga {
private int site;

private String name;
private String ID;
private int vol[] = new int[2];

public Manga(int site,String name,String ID,int vol_user,int vol_online){
	this.setSite(site);
	this.setID(ID);
	this.setName(name);
	this.setVol_user(vol_user);
	this.setVol_online(vol_online);
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public int getVol_online() {
	return vol[0];
}
public void setVol_online(int vol) {
	this.vol[0] = vol;
}
public int getVol_user() {
	return vol[1];
}
public void setVol_user(int vol) {
	this.vol[1] = vol;
}
public int getSite() {
	return site;
}
public void setSite(int site) {
	this.site = site;
}
}
