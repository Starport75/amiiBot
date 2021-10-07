package amiiBot;

public enum SeriesEnum {
	SMASH (TypeEnum.FIGURE),
	MARIO (TypeEnum.FIGURE),
	SPLATOON (TypeEnum.FIGURE);
	
	private final TypeEnum amiiboType;

	SeriesEnum(TypeEnum type) {
		amiiboType = type;
	}
		
	SeriesEnum[] SeriesArray = SeriesEnum.values();

	

	public SeriesEnum intToSeries(int num) {
		return SeriesArray[num];
	}
	
	public int seriesToInt() {
		for (int i = 0; i < SeriesArray.length; i++) {
			if (SeriesArray[i] ==  this) {
				return i;
			}
		}
		return -1;
	}
	
	public TypeEnum amiiboType() {
		return amiiboType;
	}
	
	public int getNumOfType(TypeEnum type) {
		int j = 0;
		for (int i = 0; i < SeriesEnum.values().length; i++) {
			if (this.intToSeries(i).amiiboType == type) {
				j++;
			}
		}
		return j;
	}
}
