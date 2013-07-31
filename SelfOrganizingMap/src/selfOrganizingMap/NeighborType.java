package selfOrganizingMap;

public enum NeighborType {
	BUBBLE,
	GAUSSIAN;
	
	static public NeighborType fromString(String s) {
		switch (s) {
		case "BUBBLE":
		case "bubble":
			return BUBBLE;
		case "GAUSSIAN":
		case "gaussian":
			return GAUSSIAN;
		default:
			throw new IllegalArgumentException(s);
		}
	}
}
