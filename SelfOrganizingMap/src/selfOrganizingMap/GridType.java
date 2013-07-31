package selfOrganizingMap;

public enum GridType {
	RECT, HEXA;

	static public GridType fromString(String s) {
		switch (s) {
		case "RECT":
		case "rect":
			return RECT;
		case "HEXA":
		case "hexa":
			return HEXA;
		default:
			throw new IllegalArgumentException(s);
		}
	}
}
