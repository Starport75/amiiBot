package amiiBot;

import java.util.ArrayList;

import org.json.JSONObject;

public class MasterList {

	ArrayList<ArrayList<ArrayList<Amiibo>>> masterList = new ArrayList<ArrayList<ArrayList<Amiibo>>>();
	AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

	JSONObject data = new JSONObject(websiteData
			.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionById", "240899417010470912"));

	ArrayList<String> typeList = new ArrayList<String>();
	ArrayList<ArrayList<String>> seriesList = new ArrayList<ArrayList<String>>();

	public MasterList() {
		for (int amiiboIndex = 0; amiiboIndex < data.getJSONArray("amiibo").length(); amiiboIndex++) {
			String amiiboType = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("type").get("type")
					.toString();
			String seriesName = data.getJSONArray("amiibo").getJSONObject(amiiboIndex).getJSONObject("amiibo_series").get("name")
					.toString();
			seriesName = seriesCheck(seriesName, amiiboType);
			
			if (!typeList.contains(amiiboType)) {
				typeList.add(amiiboType);
				masterList.add(new ArrayList<ArrayList<Amiibo>>());
			}
			//TODO seriesList stuff yayyyy
			if (!seriesList.get(typeIndex).contains(seriesName)){
				getSeriesList(amiiboType).get(amiiboIndex)
			}
			
			Amiibo amiiboToAdd = new Amiibo(data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("name").toString(), amiiboType, seriesName);
			masterList.get(getTypeIndex(amiiboType)).get(getSeriesIndex(seriesName)).add(amiiboToAdd);
			System.out.println(masterList);
		}
	}

	public void updateAmiibo() {

	}

	public void updateDataToUser() {

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

	public ArrayList<ArrayList<String>> getSeriesList(String type) {
		return seriesList;
	}
	
	public int getTypeIndex(String type) {
		return typeList.indexOf(type);
	}
	
	public int getSeriesIndex(String series) {
		for (int i = 0; i < typeList.size(); i++) {
			int currIndex = seriesList.get(i).indexOf(series);
			if (currIndex != -1) {
				return currIndex;
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
}
