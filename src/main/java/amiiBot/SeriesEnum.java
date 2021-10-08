package amiiBot;

public enum SeriesEnum {
	SMASH(TypeEnum.FIGURE, "Super Smash Brothers"), MARIO(TypeEnum.FIGURE, "Super Mario"),
	SPLATOON(TypeEnum.FIGURE, "Splatoon"), MARIO30(TypeEnum.FIGURE, "Mario 30th Anniversary"),
	SKY(TypeEnum.OTHER, "Skylanders Super Chargers"), ACARD1(TypeEnum.CARD, "Animal Crossing amiibo Cards - Series 1"),
	SOLOF(TypeEnum.FIGURE, "Solo Figure"), YARN(TypeEnum.FIGURE, "Yoshi's Wooly World"),
	CROSSING(TypeEnum.FIGURE, "Animal Crossing"), ACARDP(TypeEnum.CARD, "Animal Crossing amiibo Cards - Promotional"),
	SHOVEL(TypeEnum.FIGURE, "Shovel Knight"), ACARD2(TypeEnum.CARD, "Animal Crossing amiibo Cards - Series 2"),
	ZELDA(TypeEnum.FIGURE, "The Legend of Zelda"), ACARD3(TypeEnum.CARD, "Animal Crossing amiibo Cards - Series 3"),
	SOLOC(TypeEnum.CARD, "Solo Card"), KIRBY(TypeEnum.FIGURE, "Kirby"),
	ACARD4(TypeEnum.CARD, "Animal Crossing amiibo Cards - Series 4"),
	ACARDW(TypeEnum.CARD, "Animal Crossing amiibo Cards - Welcome amiibo"),
	MSPORT(TypeEnum.CARD, "Mario Sports Superstars Cards"), FIREE(TypeEnum.FIGURE, "Fire Emblem"),
	METROID(TypeEnum.FIGURE, "Metroid"), CEREAL(TypeEnum.OTHER, "Delicious amiibo");

	private final TypeEnum amiiboType;
	private final String amiiboName;

	SeriesEnum(TypeEnum type, String name) {
		amiiboType = type;
		amiiboName = name;
	}

	static SeriesEnum[] SeriesArray = SeriesEnum.values();

	public static SeriesEnum intToSeries(int num, TypeEnum type) {
		int j = 0;
		for (int i = 0; i < SeriesArray.length; i++) {
			if (j == num & SeriesArray[i].amiiboType() == type) {
				return SeriesArray[i];
			}
			if (SeriesArray[i].amiiboType() == type) {
				j++;
			}
		}
		return null;
	}

	public int seriesToInt(TypeEnum type) {
		int j = 0;
		for (int i = 0; i < SeriesArray.length; i++) {
			if (SeriesArray[i] == this) {
				return j;
			}
			if (SeriesArray[i].amiiboType() == type) {
				j++;
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
			if (SeriesArray[i].amiiboType == type) {
				j++;
			}
		}
		return j;
	}

	public String toString() {
		return amiiboName;
	}
}
