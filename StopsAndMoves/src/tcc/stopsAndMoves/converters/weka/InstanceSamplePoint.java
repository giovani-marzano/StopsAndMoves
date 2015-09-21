package tcc.stopsAndMoves.converters.weka;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import tcc.stopsAndMoves.SamplePoint;
import weka.core.Instance;

public class InstanceSamplePoint implements SamplePoint {
	public static final int INDEX_TID = 0;
	public static final int INDEX_TIME = 1;
	public static final int INDEX_LAT = 2;
	public static final int INDEX_LON = 3;
	
	private Instance instance;
	private Point pos;

	public InstanceSamplePoint(Instance instance) {
		super();
		this.instance = instance;
		GeometryFactory geoFact = new GeometryFactory();
		double lat = instance.value(InstanceSamplePoint.INDEX_LAT);
		double lon = instance.value(InstanceSamplePoint.INDEX_LON);
		this.pos = geoFact.createPoint(new Coordinate(lon, lat));
	}

	public long getTId() {
		return (long) instance.value(InstanceSamplePoint.INDEX_TID);
	}
	
	@Override
	public double getLon() {
		return pos.getX();
	}

	@Override
	public double getLat() {
		return pos.getY();
	}

	@Override
	public double getTime() {
		return instance.value(InstanceSamplePoint.INDEX_TIME)/1000.0;
	}

	@Override
	public Point getPos() {
		return pos;
	}
}
