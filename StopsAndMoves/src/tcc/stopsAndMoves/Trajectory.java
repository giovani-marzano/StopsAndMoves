package tcc.stopsAndMoves;

import java.util.List;

/**
 * Representa uma trajetória como uma coleção de pontos espaço-temporais
 * amostrados.
 * 
 * @author giovani
 *
 */
public class Trajectory {
	/**
	 * Identificaor da trajetoria
	 */
	private final long id;
	
	private List<SamplePoint> pointList;

	public List<SamplePoint> getPointList() {
		return pointList;
	}

	public Trajectory(long id) {
		super();
		this.id = id;
	}

	public void setPointsList(List<SamplePoint> pointsList) {
		this.pointList = pointsList;
	}

	public long getId() {
		return id;
	}
}
