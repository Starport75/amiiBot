package amiiBot;

public class Amiibo {
	//Set for all amiibo, saved to data sheet
	
	
	String amiiboName;
	String amiiboType;
	String amiiboSeries;
	
	
	//Specific to each user, saved in user document
	int numberObtained;
	boolean wantsNotifications;
	
	public Amiibo(String name, String type, String series) {
		amiiboName = name;
		amiiboType = type;
		amiiboSeries = series;
	}
	
	public String getName() {
		return amiiboName;
	}
	
	public String getType() {
		return amiiboType;
	}
	
	public String getSeries() {
		return amiiboSeries;
	}
	
}
