package tcc.stopsAndMoves.converters.sql;

import java.sql.Timestamp;

import tcc.stopsAndMoves.SamplePoint;

public class SQLSamplePoint implements SamplePoint {

	private double lat;
	private double lon;
	private Timestamp time;

	public SQLSamplePoint(double lat, double lon, Timestamp time) {
		super();
		this.lon = lon;
		this.lat = lat;
		this.time = time;
	}

	@Override
	public double getLat() {
		return lat;
	}

	@Override
	public double getLon() {
		return lon;
	}

	@Override
	public double getTime() {
		return time.getTime()/1000.0;
	}
}
