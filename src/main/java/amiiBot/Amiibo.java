package amiiBot;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Amiibo {
	// 100% static data that would never change no matter what
	String amiiboName;
	String amiiboNameUpper;
	int amiiboID;
	String amiiboType;
	String amiiboTypeUpper;
	int amiiboTypeID;
	String amiiboSeries;
	String amiiboSeriesUpper;
	int amiiboSeriesID;
	String releaseAU;
	String releaseEU;
	String releaseJP;
	String releaseNA;
	Color backgroundColor;
	String imgUrl;
	String eeImgUrl;

	// Figure data that changes over time
	double avgUsedPriceCompletedNA;
	double avgUsedPriceCompletedUK;
	double avgNewPriceCompletedNA;
	double avgNewPriceCompletedUK;
	double avgUsedPriceListedNA;
	double avgUsedPriceListedUK;
	double avgNewPriceListedNA;
	double avgNewPriceListedUK;
	ArrayList<String[]> retailerList;

	// Data specific to each person
	int numberNIB;
	int numberOOB;

	public Amiibo(String name, int id, String type, int typeID, String series, int seriesID, String AU, String EU,
			String JP, String NA, String color, String image) {
		amiiboName = name.toLowerCase();
		amiiboNameUpper = name;
		amiiboID = id;
		amiiboType = type.toLowerCase();
		amiiboTypeUpper = type;
		amiiboTypeID = typeID;
		amiiboSeries = series.toLowerCase();
		amiiboSeriesUpper = series;
		amiiboSeriesID = seriesID;
		releaseAU = AU;
		releaseEU = EU;
		releaseJP = JP;
		releaseNA = NA;
		if (color != "null") {
			backgroundColor = new Color(Integer.valueOf(color.substring(1, 3), 16),
					Integer.valueOf(color.substring(3, 5), 16), Integer.valueOf(color.substring(5, 7), 16));
		} else {
			backgroundColor = Color.black;
			System.out.println("ERROR: " + color + " is the color for " + amiiboName);
		}
		imgUrl = image;
	}

	public void setNibAndOob(int NIB, int OOB) {
		numberNIB = NIB;
		numberOOB = OOB;
	}

	public void updateIndividualFigureData() {
		String discordID = "205877471067766784";
		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

		retailerList = new ArrayList<String[]>();

		
		websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getAmiiboData", discordID,
				new String[]{"amiibo_id"}, new String[]{amiiboID + ""});
		JSONObject data = new JSONObject(websiteData.getLastRequestString());

		JSONArray retailerJSON = data.getJSONObject("amiibo").getJSONArray("stock_count");

		for (int retailIndex = 0; retailIndex < retailerJSON.length(); retailIndex++) {
			JSONObject currRetailer = (JSONObject) retailerJSON.get(retailIndex);
			String[] retailArray = { (String) currRetailer.get("in_stock"), (String) currRetailer.get("retailer"),
					(String) currRetailer.get("web_url") };
			retailerList.add(retailArray);
		}

		try {
			FileWriter myWriter = new FileWriter("filename.txt");
			myWriter.write(data.toString());
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

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
	
	public String getNameUppercase() {
		return amiiboNameUpper;
	}

	public String getTypeUppercase() {
		return amiiboTypeUpper;
	}

	public String getSeriesUppercase() {
		return amiiboSeriesUpper;
	}

	public int getNumObtained() {
		return numberNIB + numberOOB;
	}

	public String getImage(EasterEgg egg, String discordID) {
		if (getEEImage() != null && egg.getEE(discordID) && egg.isActive()) {
			egg.setEE(false, discordID);
			return eeImgUrl;
		}
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

	public String getFormattedNewPriceCompletedNA() {
		if (avgNewPriceCompletedNA == 0) {
			return "*Lack of Data*";
		}
		return "$" + String.format("%.2f", avgNewPriceCompletedNA);
	}

	public String getFormattedNewPriceCompletedUK() {
		if (avgNewPriceCompletedUK == 0) {
			return "*Lack of Data*";
		}
		return "?" + String.format("%.2f", avgNewPriceCompletedUK);
	}

	public String getFormattedUsedPriceCompletedNA() {
		if (avgUsedPriceCompletedNA == 0) {
			return "*Lack of Data*";
		}
		return "$" + String.format("%.2f", avgUsedPriceCompletedNA);
	}

	public String getFormattedUsedPriceCompletedUK() {
		if (avgUsedPriceCompletedUK == 0) {
			return "*Lack of Data*";
		}
		return "?" + String.format("%.2f", avgUsedPriceCompletedUK);
	}

	public String getFormattedNewPriceListedNA() {
		if (avgNewPriceListedNA == 0) {
			return "*Lack of Data*";
		}
		return "$" + String.format("%.2f", avgNewPriceListedNA);
	}

	public String getFormattedNewPriceListedUK() {
		if (avgNewPriceListedUK == 0) {
			return "*Lack of Data*";
		}
		return "?" + String.format("%.2f", avgNewPriceListedUK);
	}

	public String getFormattedUsedPriceListedNA() {
		if (avgUsedPriceListedNA == 0) {
			return "*Lack of Data*";
		}
		return "$" + String.format("%.2f", avgUsedPriceListedNA);
	}

	public String getFormattedUsedPriceListedUK() {
		if (avgUsedPriceListedUK == 0) {
			return "*Lack of Data*";
		}
		return "?" + String.format("%.2f", avgUsedPriceListedUK);
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

	public Color getColor() {
		return backgroundColor;
	}

	public void setEEImage(String imageUrl) {
		eeImgUrl = imageUrl;
	}

	public String getEEImage() {
		return eeImgUrl;
	}

	public ArrayList<String[]> getRetailerList() {
		return retailerList;
	}

}
