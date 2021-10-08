package amiiBot;

public enum SeriesEnum {
	SMASH (TypeEnum.FIGURE, "Super Smash Brothers"),
	MARIO (TypeEnum.FIGURE, "Super Mario"),
	SPLATOON (TypeEnum.FIGURE, "Splatoon");
	
	private final TypeEnum amiiboType;
	private final String amiiboName;

	SeriesEnum(TypeEnum type, String name) {
		amiiboType = type;
		amiiboName = name;
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
	
	public String toString() {
		return amiiboName;
	}
}
