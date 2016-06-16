package manganotifications;

public class Manga {
private String name;
private String ID;
private int vol;
private int user_vol;

public Manga(String name,String ID,int vol){
	this.setID(ID);
	this.setName(name);
	this.setVol(vol);
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
public int getVol() {
	return vol;
}
public void setVol(int vol) {
	this.vol = vol;
}
}
