package tcc.stopsAndMoves;

import com.vividsolutions.jts.geom.Polygon;

/**
 * Regiao de interesse.
 * 
 * @author giovani
 * 
 */
public class SpatialFeature {
	/**
	 * Identificador da região
	 */
	private final long id;

	/**
	 * Definição da área coberta pela regiao de interesse
	 */
	private Polygon area;

	/**
	 * Tempo minimo que uma trajetoria deve permanecer nesta região de
	 * interesse para se considerar que parou nesta região. (em segundos)
	 */
	private double minimunTime;

	public SpatialFeature(long id) {
		this(id, null, 0);
	}

	public SpatialFeature(long id, Polygon area, double minimunTime) {
		super();
		this.id = id;
		this.area = area;
		this.minimunTime = minimunTime;
	}

	public boolean contains(SamplePoint p) {
		if (getArea() == null) {
			return false;
		} else {
			return getArea().contains(p.getPos());
		}
	}

	public Polygon getArea() {
		return area;
	}

	public void setArea(Polygon area) {
		this.area = area;
	}

	public double getMinimunTime() {
		return minimunTime;
	}

	public void setMinimunTime(double minimunTime) {
		this.minimunTime = minimunTime;
	}

	public long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SpatialFeature) {
			return id == ((SpatialFeature) obj).id;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

}
