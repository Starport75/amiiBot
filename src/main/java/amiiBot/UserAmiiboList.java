package amiiBot;

import org.json.JSONObject;

import java.util.ArrayList;

public class UserAmiiboList {

	ArrayList<ArrayList<ArrayList<Amiibo>>> masterList = new ArrayList<ArrayList<ArrayList<Amiibo>>>();
	//AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

	ArrayList<String> typeList = new ArrayList<String>();
	ArrayList<ArrayList<String>> seriesList = new ArrayList<ArrayList<String>>();

	ArrayList<String[]> retailerList;

	public UserAmiiboList() {

		AmiiboHuntAccess websiteData = new AmiiboHuntAccess();
		websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/init", null, null, null);
		JSONObject data = new JSONObject(websiteData.getLastRequestString());
		
		/*
		try {
		      FileWriter myWriter = new FileWriter("filename.txt");
		      myWriter.write(data.toString());
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		*/

		for (int amiiboIndex = 0; amiiboIndex < data.getJSONArray("amiibo").length(); amiiboIndex++) {
		
			String amiiboType = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("type").get("type")
					.toString().toLowerCase();
			String seriesName = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("amiibo_series")
					.get("name").toString().toLowerCase();
			amiiboType = typeCheck(amiiboType, seriesName);
			int amiiboCardNumber = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("card_number").getInt("animal_crossing_number");
			
			seriesName = seriesCheck(seriesName, amiiboType, amiiboCardNumber);
						
			if (!typeList.contains(amiiboType)) {
				typeList.add(amiiboType);
				seriesList.add(new ArrayList<String>());
				masterList.add(new ArrayList<ArrayList<Amiibo>>());
			}

			if (!seriesList.get(getTypeIndex(amiiboType)).contains(seriesName)) {
				seriesList.get(getTypeIndex(amiiboType)).add(seriesName);
				masterList.get(getTypeIndex(amiiboType)).add(new ArrayList<Amiibo>());
			}

			Amiibo amiiboToAdd = new Amiibo(
					data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("name").toString(),
					(int) data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("amiibo_id"), amiiboType,
					(int) data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("typeId"), seriesName,
					(int) data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("amiiboSeriesId"),
					data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("release_au").toString(),
					data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("release_eu").toString(),
					data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("release_jp").toString(),
					data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("release_na").toString(),
					data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("background_color").toString(),
					data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("image_imgix_full_card").toString());
			masterList.get(getTypeIndex(amiiboType)).get(getSeriesIndex(seriesName)).add(amiiboToAdd);
		}

	}

	public boolean updateCollectionData(String discordID, AmiiboHuntAccess websiteData) {
		JSONObject data = null;
		if (websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionById", discordID, null, null)) {
			data = new JSONObject(websiteData.getLastRequestString());
		} else {
			return false;
		}
		
		for (int amiiboIndex = 0; amiiboIndex < data.getJSONArray("amiibo").length(); amiiboIndex++) {
			int NIB = 0;
			int OOB = 0;
			for (int ownedDataIndex = 0; ownedDataIndex < data.getJSONArray("amiibo").getJSONObject(amiiboIndex)
					.getJSONArray("owned_data").length(); ownedDataIndex++) {
				if ((int) data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONArray("owned_data")
						.getJSONObject(0).get("isBoxed") == 1) {
					NIB++;
				} else {
					OOB++;
				}
			}

			String seriesName = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("amiibo_series")
					.get("name").toString().toLowerCase();
			String amiiboType = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("type").get("type")
					.toString().toLowerCase();
			int amiiboCardNumber = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("card_number").getInt("animal_crossing_number");
			seriesName = seriesCheck(seriesName, amiiboType, amiiboCardNumber);
			String amiiboName = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("name").toString().toLowerCase();

			getAmiibo(amiiboName, seriesName).setNibAndOob(NIB, OOB);
		}
		return true;
	}

	public ArrayList<ArrayList<ArrayList<Amiibo>>> getMasterList() {
		return masterList;
	}

	public String seriesCheck(String series, String type, int cardNumber) {
		if (series.equals("animal crossing")) {
			if (type.equals("figure")) {
				series = series + " (figures)";
			} else if (type.equals("card")) {
				if (cardNumber <= 100) {
					series = series + " (series 1)";
				} else if (cardNumber >= 101 && cardNumber <= 200) {
					series = series + " (series 2)";
				} else if (cardNumber >= 201 && cardNumber <= 300) {
					series = series + " (series 3)";
				} else if (cardNumber >= 301 && cardNumber <= 400) {
					series = series + " (series 4)";
				} else if (cardNumber >= 401 && cardNumber <= 500) {
					series = series + " (series 5)";
				} else if (cardNumber >= 501 && cardNumber <= 550) {
					series = series + " (welcome amiibo)";
				} else if (cardNumber >= 601 && cardNumber <= 650) {
					series = series + " (sanrio)";
				} else if (cardNumber >= 651 && cardNumber <= 700) {
					series = series + " (promo cards)";
				} else {
					series = series + " (error)";
				}
			}
		}
		
		if (series.charAt(series.length() - 1) == '.') {
			series = series.substring(0, series.length() - 1);
		}
		
		return series;
	}

	public String typeCheck(String type, String series) {
		if (type.equals("yarn") || series.equals("super mario cereal")) {
			type = "figure";
		}
		return type;
	}

	public ArrayList<String> getTypeList() {
		return typeList;
	}

	public ArrayList<String> getSeriesList(String type) {
		return seriesList.get(getTypeIndex(type));

	}

	public int getTypeIndex(String type) {
		return typeList.indexOf(type);
	}

	public int getSeriesIndex(String series) {
		for (int i = 0; i < typeList.size(); i++) {
			if (seriesList.get(i).contains(series)) {
				return seriesList.get(i).indexOf(series);
			}
		}
		return -1;
	}

	public int seriesToTypeIndex(String series) {
				
		for (int i = 0; i < typeList.size(); i++) {
			if (seriesList.get(i).contains(series)) {
				return i;
			}
		}
		return -1;
	}

	public String getTypeAt(int location) {
		return typeList.get(location);
	}

	public String getSeriesAt(int location, String type) {
		return seriesList.get(getTypeIndex(type)).get(location);
	}

	public int getNumOfTypes() {
		return typeList.size();
	}

	public int getNumOfSeries(String type) {
		return getSeriesList(type).size();
	}

	public int getNumOfAmiibo(String series) {
		return getAmiiboList(series).size();
	}

	public ArrayList<Amiibo> getAmiiboList(String series) {
		int typeIndex = seriesToTypeIndex(series);
		int seriesIndex = getSeriesIndex(series);
				
		return masterList.get(typeIndex).get(seriesIndex);
	}

	public int getNumCollectedInSeries(String series) {
		int total = 0;
		for (int i = 0; i < getAmiiboList(series).size(); i++) {
			if (getAmiiboList(series).get(i).getNumObtained() > 0) {
				total++;
			}
		}
		return total;
	}

	public boolean doesSeriesExist(String series) {
		for (int i = 0; i < typeList.size(); i++) {
			if (seriesList.get(i).contains(series)) {
				return true;
			}
		}
		return false;
	}

	public boolean isInSeries(String name, String series) {
		for (int i = 0; i < getAmiiboList(series).size(); i++) {
			if (getAmiiboList(series).get(i).getName().equals(name.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public Amiibo getAmiibo(String name, String series) {
		name = name.toLowerCase();
		series = series.toLowerCase();
		for (int i = 0; i < getAmiiboList(series).size(); i++) {
			if (getAmiiboList(series).get(i).getName().equals(name.toLowerCase())) {
				return getAmiiboList(series).get(i);
			}
		}
		return null;
	}

	public Amiibo amiiboNameLookup(String name) {
		for (int i = 0; i < getNumOfTypes(); i++) {
			for (int j = 0; j < getNumOfSeries(typeList.get(i)); j++) {
				if (getAmiibo(name, getSeriesList(typeList.get(i)).get(j)) != null) {
					return getAmiibo(name, getSeriesList(typeList.get(i)).get(j));
				}
			}
		}
		return null;
	}
	
	public int getNumInType(String type) {
		int total = 0;
		for (int i = 0; i < this.getNumOfSeries(type); i++) {
			total = total + this.getNumOfAmiibo(this.getSeriesAt(i, type));
		}
		return total;
	};
	
	public int getNumCollectedInType(String type) {
		int total = 0;
		for (int i = 0; i < this.getNumOfSeries(type); i++) {
			total = total + this.getNumCollectedInSeries(this.getSeriesAt(i, type));
		}
		return total;
	};
	
	public int getCollectionWorth(String type) {
		int total = 0;
		for (int i = 0; i < this.getNumOfSeries(type); i++) {
			
		}
		return total;
	}
}