package amiiBot;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserAmiiboList {

	ArrayList<ArrayList<ArrayList<Amiibo>>> masterList = new ArrayList<ArrayList<ArrayList<Amiibo>>>();
	AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

	ArrayList<String> typeList = new ArrayList<String>();
	ArrayList<ArrayList<String>> seriesList = new ArrayList<ArrayList<String>>();

	public UserAmiiboList(String userDiscordID) {

		JSONObject data = new JSONObject(websiteData
				.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionById", userDiscordID));

		for (int amiiboIndex = 0; amiiboIndex < data.getJSONArray("amiibo").length(); amiiboIndex++) {
			String amiiboType = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("type").get("type")
					.toString();
			String seriesName = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("amiibo_series")
					.get("name").toString();
			seriesName = seriesCheck(seriesName, amiiboType);

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
					data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("image_s3_full").toString());
			masterList.get(getTypeIndex(amiiboType)).get(getSeriesIndex(seriesName)).add(amiiboToAdd);
		}

	}

	public void updateCollectionData(String discordID) {
		JSONObject data = new JSONObject(
				websiteData.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionById", discordID));
		try {
			FileWriter myWriter = new FileWriter("filename.txt");
			myWriter.write(data.toString());
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
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
			
			System.out.println(data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("amiibo_series").toString());
			
			String seriesName = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("amiibo_series").getJSONObject("name").toString();
			String amiiboName = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("character").toString();
			
			getAmiibo(amiiboName, seriesName).setNibAndOob(NIB, OOB);
		}
	}

	public void updateUserData(String discordID) {

	}

	public ArrayList<ArrayList<ArrayList<Amiibo>>> getMasterList() {
		return masterList;
	}

	public String seriesCheck(String series, String type) {
		if (series.equals("Animal Crossing") || series.equals("Others")) {
			if (type.equals("Figure")) {
				series = series + " (Figures)";
			} else if (type.equals("Card")) {
				series = series + " (Cards)";
			}
		}

		return series;
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
			if (getAmiiboList(series).get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Amiibo getAmiibo(String name, String series) {
		for (int i = 0; i < getAmiiboList(series).size(); i++) {
			if (getAmiiboList(series).get(i).getName().equals(name)) {
				return getAmiiboList(series).get(i);
			}
		}
		return null;
	}

	public Amiibo amiiboNameLookup(String name) {
		for (int i = 0; i < getNumOfTypes(); i++) {
			for (int j = 0; j < getNumOfSeries(typeList.get(i)); j++) {
				if (getAmiibo(name, getSeriesList(typeList.get(i)).get(j)) != null) {
					return getAmiibo(name, getSeriesList(typeList.get(i)).get(i));
				}
			}
		}
		return null;
	}
}