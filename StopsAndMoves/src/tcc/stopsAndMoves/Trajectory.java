package tcc.stopsAndMoves;

import java.util.ArrayList;

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

	private ArrayList<Move> moves;
	private ArrayList<Stop> stops;

	private PathType lastPathType;

	private long moveId;
	private long stopId;

	public Trajectory(long id) {
		super();
		this.id = id;

		moves = new ArrayList<>();
		stops = new ArrayList<>();
		lastPathType = PathType.NONE;

		moveId = 0;
		stopId = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void addStop(Path path, SpatialFeature sp) {
		if (!path.isEmpty()) {
			Stop stop = new Stop(this, stopId++, sp);
			stop.setPath(path);
			stops.add(stop);

			switch (lastPathType) {
			case MOVE:
				stop = new Stop(this, stopId++, sp);
				stop.setPath(path);
				stops.add(stop);
				// Este Stop é o destino do último Move
				moves.get(moves.size() - 1).setDestination(stop);
				break;
			default:
				break;
			}

			lastPathType = PathType.STOP;
		}
	}

	public void addMove(Path path) {
		if (!path.isEmpty()) {
			Move move;
			switch (lastPathType) {
			case NONE:
				move = new Move(this, moveId++);
				move.setPath(path);
				moves.add(move);
				lastPathType = PathType.MOVE;
				break;
			case MOVE:
				// Acrescentamos os pontos ao ultimo Move da lista
				moves.get(moves.size() - 1).getPath().addAll(path);
				break;
			case STOP:
				move = new Move(this, moveId++);
				move.setPath(path);
				moves.add(move);
				// Indicando em que Stop o Move começa (o ultimo Stop)
				move.setOrigin(stops.get(stops.size() - 1));
				lastPathType = PathType.MOVE;
				break;
			default:
				break;
			}
		}
	}
}
