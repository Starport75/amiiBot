package amiiBot;

public enum TypeEnum {
	FIGURE,
	CARD,
	OTHER;
	
	static TypeEnum[] TypeArray = TypeEnum.values();
	
	public static TypeEnum intToType(int num) {
		return TypeArray[num];
	}
	
	public int typeToInt() {
		for (int i = 0; i < TypeArray.length; i++) {
			if (TypeArray[i] ==  this) {
				return i;
			}
		}
		return -1;
	}
}
