package amiiBot;

public class Amiibo {
    String amiiboName;
    String amiiboType;
    String amiiboSeries;


    int numberObtained;
    boolean wantsNotifications;

    public Amiibo(String name, String type, String series, int num) {
        amiiboName = name;
        amiiboType = type;
        amiiboSeries = series;
        numberObtained = num;

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
        return numberObtained;
    }

}
