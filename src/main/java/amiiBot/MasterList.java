package amiiBot;

import java.util.ArrayList;

import org.json.JSONObject;

public class MasterList {

	ArrayList<Amiibo>[][] masterList;
	AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

	JSONObject data = new JSONObject(websiteData.sendPostRequest());

	ArrayList<String> seriesList = new ArrayList<String>();
	ArrayList<String> typeList = new ArrayList<String>();
	ArrayList<String> comparisonList = new ArrayList<String>();
	ArrayList<Integer> seriesIndexLookup = new ArrayList<Integer>();

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
				int count = 0;
				for (String x : comparisonList) {
					if (x.equals(amiiboType))
						count++;
				}
				seriesIndexLookup.add(count - 1);

			}
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
			System.out.println(seriesList);
			System.out.println("Series: " + amiiboToAdd.getSeries());
			System.out.println(seriesList.indexOf(amiiboToAdd.getSeries()));
			System.out.println(seriesIndexLookup);
			System.out.println("Attempting to add " + amiiboToAdd.getName() + " of type " + amiiboToAdd.getType()
					+ " to series " + amiiboToAdd.getSeries() + " (index "
					+ seriesIndexLookup.get(seriesList.indexOf(amiiboToAdd.getSeries())) + ")");
			masterList[typeList.indexOf(amiiboToAdd.getType())][seriesIndexLookup
					.get(seriesList.indexOf(amiiboToAdd.getSeries()))].add(amiiboToAdd);
		}

		System.out.println(seriesList);
		System.out.println(seriesIndexLookup);
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

	public String getSeriesIndexInType(int seriesIndex, int typeIndex) {
		int count = 0;
		for (int i = 0; i < comparisonList.size(); i++) {
			if (comparisonList.get(i).equals(typeList.get(typeIndex))) {
				count++;
			}
			if (count == seriesIndex + 1) {
				System.out.println("Returning " + seriesList.get(i));
				return seriesList.get(i);
			}	
		}
		return ("No series found");
	}
}
