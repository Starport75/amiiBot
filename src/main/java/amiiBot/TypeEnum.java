package amiiBot;

public enum TypeEnum {
	FIGURE ("Figures"),
	CARD ("Cards"),
	OTHER ("Other");
	
	static TypeEnum[] TypeArray = TypeEnum.values();
	
	String formalName;
	
	TypeEnum(String name) {
		formalName = name;
	}

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
	
	public static int getNumOfTypes() {
		return TypeEnum.values().length;
	}
	
	public String toString() {
		return formalName;
	}
}
