package amiiBot;

import java.util.ArrayList;

import org.json.JSONObject;

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
                    data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("name").toString(), amiiboType,
                    seriesName, (int) data.getJSONArray("amiibo").getJSONObject(amiiboIndex).get("owned_qty"));
            masterList.get(getTypeIndex(amiiboType)).get(getSeriesIndex(seriesName)).add(amiiboToAdd);
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
}
