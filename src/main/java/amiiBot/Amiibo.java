package amiiBot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

public class Amiibo {
	// 100% static data that would never change no matter what
	String amiiboName;
	int amiiboID;
	String amiiboType;
	int amiiboTypeID;
	String amiiboSeries;
	int amiiboSeriesID;
	String releaseAU;
	String releaseEU;
	String releaseJP;
	String releaseNA;
	String backgroundColor;
	String imgUrl;

	// Figure data that changes over time
	double avgMonthPriceNA;
	double avgMonthPriceUK;
	ArrayList<String[]> retailerList = new ArrayList<String[]>();

	// Data specific to each person
	int numberNIB;
	int numberOOB;

	public Amiibo(String name, int id, String type, int typeID, String series, int seriesID, String AU, String EU,
			String JP, String NA, String color, String image) {
		amiiboName = name;
		amiiboID = id;
		amiiboType = type;
		amiiboTypeID = typeID;
		amiiboSeries = series;
		amiiboSeriesID = seriesID;
		releaseAU = AU;
		releaseEU = EU;
		releaseJP = JP;
		releaseNA = NA;
		backgroundColor = color;
		imgUrl = image;
	}

	public void setNibAndOob(int NIB, int OOB) {
		numberNIB = NIB;
		numberOOB = OOB;
	}
	
	public void updateIndividualData() {
		String discordID = "205877471067766784";
		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();
		
		JSONObject data = new JSONObject(websiteData
				.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getAmiiboData", discordID, amiiboID + ""));

		avgMonthPriceNA = (double) data.getJSONObject("amiibo").get("average_price_this_month_us");
		avgMonthPriceUK = (double) data.getJSONObject("amiibo").get("average_price_this_month_uk");
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

	public int getNumObtained() {
		return numberNIB + numberOOB;
	}

	public String getImage() {
		return imgUrl;
	}

	public int getAmiiboID() {
		return amiiboID;
	}

}
