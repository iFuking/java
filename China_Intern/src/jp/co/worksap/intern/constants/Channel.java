package jp.co.worksap.intern.constants;

public enum Channel {
	TELEPHONE, OFFICIAL_WEBSITE, _3RD_PARTY_WEBSITE, TRAVEL_AGENT, OTHERS;
	
	public Integer toInteger() {
		switch (this) {
		case TELEPHONE:
			return 0;
		case OFFICIAL_WEBSITE:
			return 1;
		case _3RD_PARTY_WEBSITE:
			return 2;
		case TRAVEL_AGENT:
			return 3;
		case OTHERS:
			return 4;
		default:
			return -1; 
		}
	}
	
	public static Integer getChannelNums() {
		return Channel.values().length;
	}

	public String toString() {
		switch (this) {
		case TELEPHONE:
			return "TELEPHONE";
		case OFFICIAL_WEBSITE:
			return "OFFICIAL_WEBSITE";
		case _3RD_PARTY_WEBSITE:
			return "_3RD_PARTY_WEBSITE";
		case TRAVEL_AGENT:
			return "TRAVEL_AGENT";
		case OTHERS:
			return "OTHERS";
		default:
			return ""; 
		}
	}
	
	public static Channel valueOfString(String src) {
		String raw = src.toLowerCase();

		if (raw.equals("telephone")) {
			return TELEPHONE;
		}
		
		if (raw.equals("official_website")) {
			return OFFICIAL_WEBSITE;
		}
		
		if (raw.equals("_3rd_party_website")) {
			return _3RD_PARTY_WEBSITE;
		}
		
		if (raw.equals("travel_agent")) {
			return TRAVEL_AGENT;
		}
		
		if (raw.equals("others")) {
			return OTHERS;
		}

		throw new IllegalArgumentException("Unknown Channel Type : " + raw);
	}
}
