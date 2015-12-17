package jp.co.worksap.intern.entities.room;

public enum RoomType {
	SINGLE, SEMIDOUBLE, DOUBLE, TWIN, TRIPLE, SUITE;
	
	public Integer toInteger() {
		switch (this) {
		case SINGLE:
			return 0;
		case SEMIDOUBLE:
			return 1;
		case DOUBLE:
			return 2;
		case TWIN:
			return 3;
		case TRIPLE:
			return 4;
		case SUITE:
			return 5;
		default:
			return -1; 
		}
	}
	
	public static Integer getRoomTypeNums() {
		return RoomType.values().length;
	}

	public String toString() {
		switch (this) {
		case SINGLE:
			return "SINGLE";
		case SEMIDOUBLE:
			return "SEMIDOUBLE";
		case DOUBLE:
			return "DOUBLE";
		case TWIN:
			return "TWIN";
		case TRIPLE:
			return "TRIPLE";
		case SUITE:
			return "SUITE";
		default:
			return ""; 
		}
	}

	public static RoomType valueOfString(String src) {
		String raw = src.toLowerCase();

		if (raw.equals("single")) {
			return SINGLE;
		}
		
		if (raw.equals("semidouble")) {
			return SEMIDOUBLE;
		}
		
		if (raw.equals("double")) {
			return DOUBLE;
		}
		
		if (raw.equals("twin")) {
			return TWIN;
		}
		
		if (raw.equals("triple")) {
			return TRIPLE;
		}
		
		if (raw.equals("suite")) {
			return SUITE;
		}

		throw new IllegalArgumentException("Unknown Room Type : " + raw);
	}
}
