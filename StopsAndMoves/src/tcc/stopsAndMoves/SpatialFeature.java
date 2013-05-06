package tcc.stopsAndMoves;

import java.awt.Shape;

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
	private final String id;
	
	/**
	 * Definição da área coberta pela regiao de interesse
	 */
	private Shape area;
	
	/**
	 * Tempo minimo que uma trajetoria deve permaneceer nesta região de
	 * interesse para se considerar que parou nesta região. 
	 */
	private double minimunTime;

	public SpatialFeature(String id, Shape area, double minimunTime) {
		super();
		if (id == null) {
			throw new NullPointerException("id is null");
		}
		this.id = id;
		this.area = area;
		this.minimunTime = minimunTime;
	}

	public Shape getArea() {
		return area;
	}

	public void setArea(Shape area) {
		this.area = area;
	}

	public double getMinimunTime() {
		return minimunTime;
	}

	public void setMinimunTime(double minimunTime) {
		this.minimunTime = minimunTime;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SpatialFeature) {
			return id.equals(((SpatialFeature) obj).id);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
}
