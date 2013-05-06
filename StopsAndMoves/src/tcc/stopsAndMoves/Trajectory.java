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
	
	private List<SamplePoint> pointsList;

	public List<SamplePoint> getPointsList() {
		return pointsList;
	}

	public Trajectory(long id) {
		super();
		this.id = id;
	}

	public void setPointsList(List<SamplePoint> pointsList) {
		this.pointsList = pointsList;
	}

	public long getId() {
		return id;
	}
}
