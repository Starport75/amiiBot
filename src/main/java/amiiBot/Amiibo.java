package amiiBot;

public class Amiibo {
	//Set for all amiibo, saved to data sheet
	String amiiboName;
	TypeList amiiboType;
	SeriesList amiiboSeries;
	
	
	//Specific to each user, saved in user document
	int numberObtained;
	boolean wantsNotifications;
	
	public Amiibo(String name, TypeList type, SeriesList series) {
		amiiboName = name;
		amiiboType = type;
		amiiboSeries = series;
		numberObtained = 0;
		wantsNotifications = false;
	}
	
	public String getName() {
		return amiiboName;
	}
	
	public TypeList getType() {
		return amiiboType;
	}
	
	public SeriesList getSeries() {
		return amiiboSeries;
	}
	
}
