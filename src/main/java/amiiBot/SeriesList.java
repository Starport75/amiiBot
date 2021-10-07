package amiiBot;

public enum SeriesList {
	SMASH,
	MARIO,
	SPLATOON;
	
	SeriesList[] SeriesArray = SeriesList.values();

	public SeriesList intToSeries(int num) {
		return SeriesArray[num];
	}
	
	public int seriesToInt(SeriesList series) {
		for (int i = 0; i < SeriesArray.length; i++) {
			if (SeriesArray[i] ==  series) {
				return i;
			}
		}
		return -1;
	}
}
