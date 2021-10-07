package amiiBot;

public class Amiibo {
	//Set for all amiibo, saved to data sheet
	String amiiboName;
	TypeEnum amiiboType;
	SeriesEnum amiiboSeries;
	
	
	//Specific to each user, saved in user document
	int numberObtained;
	boolean wantsNotifications;
	
	public Amiibo(String name, TypeEnum type, SeriesEnum series) {
		amiiboName = name;
		amiiboType = type;
		amiiboSeries = series;
		numberObtained = 0;
		wantsNotifications = false;
	}
	
	public String getName() {
		return amiiboName;
	}
	
	public TypeEnum getType() {
		return amiiboType;
	}
	
	public SeriesEnum getSeries() {
		return amiiboSeries;
	}
	
}
