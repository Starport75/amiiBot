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
	double avgUsedPriceCompletedNA;
	double avgUsedPriceCompletedUK;
	double avgNewPriceCompletedNA;
	double avgNewPriceCompletedUK;
	double avgUsedPriceListedNA;
	double avgUsedPriceListedUK;
	double avgNewPriceListedNA;
	double avgNewPriceListedUK;
	
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
	
	public void updateIndividualFigureData() {
		String discordID = "205877471067766784";
		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();
		
		JSONObject data = new JSONObject(websiteData
				.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getAmiiboData", discordID, amiiboID + ""));
		
		avgUsedPriceCompletedNA = data.getJSONObject("amiibo").getDouble("average_price_this_month_us_used");
		avgUsedPriceCompletedUK = data.getJSONObject("amiibo").getDouble("average_price_this_month_uk_used");
		avgNewPriceCompletedNA = data.getJSONObject("amiibo").getDouble("average_price_this_month_us_new");
		avgNewPriceCompletedUK = data.getJSONObject("amiibo").getDouble("average_price_this_month_uk_new");
		avgUsedPriceListedNA = data.getJSONObject("amiibo").getDouble("average_listed_this_month_us_used");
		avgUsedPriceListedUK = data.getJSONObject("amiibo").getDouble("average_listed_this_month_uk_used");
		avgNewPriceListedNA = data.getJSONObject("amiibo").getDouble("average_listed_this_month_us_new");
		avgNewPriceListedUK = data.getJSONObject("amiibo").getDouble("average_listed_this_month_uk_new");
	
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
	
	public double getNewPriceCompletedNA() {
		return avgNewPriceCompletedNA;
	}
	
	public double getNewPriceCompletedUK() {
		return avgNewPriceCompletedUK;
	}
	
	public double getUsedPriceCompletedNA() {
		return avgUsedPriceCompletedNA;
	}
	
	public double getUsedPriceCompletedUK() {
		return avgUsedPriceCompletedUK;
	}
	
	public double getNewPriceListedNA() {
		return avgNewPriceListedNA;
	}
	
	public double getNewPriceListedUK() {
		return avgNewPriceListedUK;
	}
	
	public double getUsedPriceListedNA() {
		return avgUsedPriceListedNA;
	}
	
	public double getUsedPriceListedUK() {
		return avgUsedPriceListedUK;
	}
	
	public String getReleaseJP() {
		if (releaseJP.equals("null")) {
			return "N/A";
		}
		return releaseJP;
	}
	
	public String getReleaseNA() {
		if (releaseNA.equals("null")) {
			return "N/A";
		}
		return releaseNA;
	}
	
	public String getReleaseEU() {
		if (releaseEU.equals("null")) {
			return "N/A";
		}
		return releaseEU;
	}
	
	public String getReleaseAU() {
		if (releaseAU.equals("null")) {
			return "N/A";
		}
		return releaseAU;
	}

}
