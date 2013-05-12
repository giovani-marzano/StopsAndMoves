package tcc.stopsAndMoves;

public class SimplePoint implements SamplePoint {

	private double lon;
	private double lat;
	private double time;

	public SimplePoint(double lon, double lat, double time) {
		super();
		this.lon = lon;
		this.lat = lat;
		this.time = time;
	}

	@Override
	public double getLon() {
		return lon;
	}

	@Override
	public double getLat() {
		return lat;
	}

	@Override
	public double getTime() {
		return time;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setTime(double time) {
		this.time = time;
	}
}
