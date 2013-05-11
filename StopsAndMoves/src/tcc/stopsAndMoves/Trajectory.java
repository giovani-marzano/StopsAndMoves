package tcc.stopsAndMoves;


/**
 * Representa uma trajetória como uma coleção de pontos espaço-temporais
 * amostrados.
 * 
 * @author giovani
 *
 */
public class Trajectory extends Path {

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	public Trajectory(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void addStop(Path path, SpatialFeature sp) {
		// TODO
	}
	
	public void addMove(Path path) {
		// TODO
	}
}
