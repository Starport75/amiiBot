package amiiBot;

public enum TypeList {
	FIGURE,
	CARD,
	OTHER;
	
	TypeList[] TypeArray = TypeList.values();
	
	public TypeList intToType(int num) {
		return TypeArray[num];
	}
	
	public int typeToInt(TypeList type) {
		for (int i = 0; i < TypeArray.length; i++) {
			if (TypeArray[i] ==  type) {
				return i;
			}
		}
		return -1;
	}
}
