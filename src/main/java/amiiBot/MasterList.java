package amiiBot;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class MasterList {

	ArrayList<Amiibo>[][] masterList;
	AmiiboHuntAccess websiteData = new AmiiboHuntAccess();

	JSONObject data = new JSONObject(websiteData.sendPostRequest());

	ArrayList<String> seriesList = new ArrayList<String>();
	ArrayList<String> typeList = new ArrayList<String>();
	ArrayList<String> comparisonList = new ArrayList<String>();

	@SuppressWarnings("unchecked")
	public MasterList() {

		for (int i = 0; i < data.getJSONArray("amiibo").length(); i++) {
			String amiiboType = data.getJSONArray("amiibo").getJSONObject(i).getJSONObject("type").get("type")
					.toString();
			String seriesName = data.getJSONArray("amiibo").getJSONObject(i).getJSONObject("amiibo_series").get("name")
					.toString();

			if (seriesName.equals("Animal Crossing")) {
				if (amiiboType.equals("Figure")) {
					seriesName = seriesName + " (Figures)";
				} else if (amiiboType.equals("Card")) {
					seriesName = seriesName + " (Cards)";
				}
			}

			if (!typeList.contains(amiiboType)) {
				typeList.add(amiiboType);
			}

			if (!seriesList.contains(seriesName)) {
				seriesList.add(seriesName);
				comparisonList.add(amiiboType);
			}
		}

		masterList = new ArrayList[typeList.size()][];

		for (int typeIndex = 0; typeIndex < typeList.size(); typeIndex++) {
			int count = 0;
			for (String x : comparisonList) {
				if (x.equals(typeList.get(typeIndex)))
					count++;
			}
			System.out.println(comparisonList);
			System.out.println(typeList);
			System.out.println("Counting " + typeList.get(typeIndex) + ". Count is " + count);

			masterList[typeIndex] = new ArrayList[count];

			System.out.println("i size is " + masterList.length + ", j size is " + masterList[typeIndex].length);

			int currSeriesIndex = 0;
			for (int seriesIndex = 0; seriesIndex < seriesList.size(); seriesIndex++) {
				
				System.out.print("Testing arrayList (" + typeIndex + " " + seriesIndex + ") of amiibo for type ");
				System.out.print(typeList.get(typeIndex));
				System.out.println(", series " + seriesList.get(seriesIndex));

				if (comparisonList.get(seriesIndex).equals(typeList.get(typeIndex))) {
					System.out.println(comparisonList.get(seriesIndex) + " = " + typeList.get(typeIndex));
					
					masterList[typeIndex][currSeriesIndex] = new ArrayList<Amiibo>();
					currSeriesIndex++;
				}
			}
		}
		
		for (int i = 0; i < data.getJSONArray("amiibo").length(); i++) {
			
		}

		System.out.println(seriesList);
	}
	
	public void updateAmiibo() {
		
	}

	public void updateDataToUser() {

	}

	public ArrayList<Amiibo>[][] getMasterList() {
		return masterList;
	}
}
