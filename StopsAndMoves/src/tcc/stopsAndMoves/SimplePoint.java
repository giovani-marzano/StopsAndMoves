package tcc.stopsAndMoves;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class SimplePoint implements SamplePoint {

	private double time;
	private Point pos;

	public SimplePoint(double lon, double lat, double time) {
		super();
		Point lpos = new GeometryFactory().createPoint(new Coordinate(lon, lat));
		this.pos = lpos;
		this.time = time;
	}
	
	public SimplePoint(Point pos, double time) {
		super();
		this.pos = pos;
		this.time = time;
	}
	
	@Override
	public Point getPos() {
		return pos;
	}
	@Override
	public double getLat() {
		return pos.getY();
	}

	@Override
	public double getLon() {
		return pos.getX();
	}

	@Override
	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
}
