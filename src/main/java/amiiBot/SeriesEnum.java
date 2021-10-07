package amiiBot;

public enum SeriesEnum {
	SMASH (TypeEnum.FIGURE),
	MARIO (TypeEnum.FIGURE),
	SPLATOON (TypeEnum.FIGURE);
	
	private final TypeEnum amiiboType;

	SeriesEnum(TypeEnum type) {
		amiiboType = type;
	}
		
	static SeriesEnum[] SeriesArray = SeriesEnum.values();

	

	public static SeriesEnum intToSeries(int num) {
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
	
	public static int getNumOfType(TypeEnum type) {
		int j = 0;
		for (int i = 0; i < SeriesEnum.values().length; i++) {
			if (intToSeries(i).amiiboType == type) {
				j++;
			}
		}
		return j;
	}
}
