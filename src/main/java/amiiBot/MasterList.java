package amiiBot;

import java.util.ArrayList;

import org.json.JSONObject;

public class MasterList {

	ArrayList<Amiibo>[][] masterList;
	AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

	JSONObject data = new JSONObject(websiteData
			.sendPostRequest("https://www.amiibohunt.com/api/discord/v1/getCollectionById", "240899417010470912"));

	ArrayList<String> seriesList = new ArrayList<String>();
	ArrayList<String> typeList = new ArrayList<String>();
	ArrayList<String> comparisonList = new ArrayList<String>();
	String[][] seriesListByType;

	@SuppressWarnings("unchecked")
	public MasterList() {

		for (int i = 0; i < data.getJSONArray("amiibo").length(); i++) {
			String amiiboType = data.getJSONArray("amiibo").getJSONObject(i).getJSONObject("type").get("type")
					.toString();
			String seriesName = data.getJSONArray("amiibo").getJSONObject(i).getJSONObject("amiibo_series").get("name")
					.toString();

			seriesName = seriesCheck(seriesName, amiiboType);

			if (!typeList.contains(amiiboType)) {
				typeList.add(amiiboType);
			}

			if (!seriesList.contains(seriesName)) {
				seriesList.add(seriesName);
				comparisonList.add(amiiboType);
			}
			
			
			seriesListByType = new String[typeList.size()][];

			for (int l = 0; l < typeList.size(); l++) {
				int count = 0;
				for (String x : comparisonList) {
					if (x.equals(amiiboType))
						count++;
				}
				seriesListByType[l] = new String[count - 1];
				for (int m = 0; m < seriesListByType[l].length; m++) {
					
				seriesListByType[l][m] = "test";
				}
			}
			System.out.println(seriesListByType.toString());

		}

		masterList = new ArrayList[typeList.size()][];

		for (int typeIndex = 0; typeIndex < typeList.size(); typeIndex++) {
			int count = 0;
			for (String x : comparisonList) {
				if (x.equals(typeList.get(typeIndex)))
					count++;
			}
			masterList[typeIndex] = new ArrayList[count];

			int currSeriesIndex = 0;
			for (int seriesIndex = 0; seriesIndex < seriesList.size(); seriesIndex++) {
				if (comparisonList.get(seriesIndex).equals(typeList.get(typeIndex))) {
					masterList[typeIndex][currSeriesIndex] = new ArrayList<Amiibo>();
					currSeriesIndex++;
				}
			}
		}

		for (int i = 0; i < data.getJSONArray("amiibo").length(); i++) {
			String series = data.getJSONArray("amiibo").getJSONObject(i).getJSONObject("amiibo_series").get("name")
					.toString();
			String type = data.getJSONArray("amiibo").getJSONObject(i).getJSONObject("type").get("type").toString();
			series = seriesCheck(series, type);
			Amiibo amiiboToAdd = new Amiibo(data.getJSONArray("amiibo").getJSONObject(i).get("name").toString(), type,
					series);
			// masterList[typeList.indexOf(amiiboToAdd.getType())][getSeriesListByType()[typeIndex][1]].add(amiiboToAdd);
		}
	}

	public void updateAmiibo() {

	}

	public void updateDataToUser() {

	}

	public ArrayList<Amiibo>[][] getMasterList() {
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

	public ArrayList<String> getSeriesList() {
		return seriesList;
	}

	public ArrayList<String> getTypeList() {
		return typeList;
	}

	public String[][] getSeriesListByType() {
		return seriesListByType;
	}

}
