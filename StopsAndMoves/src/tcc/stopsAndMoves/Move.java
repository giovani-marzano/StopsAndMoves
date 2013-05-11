package tcc.stopsAndMoves;

public class Move {
	private final Trajectory trajectory;
	private final long id; 

	private Stop origin;
	private Stop destination;
	
	private Path path;
	
	public Move(Trajectory trajectory, long id) {
		super();
		if (trajectory == null) {
			throw new NullPointerException("trajectory null");
		}
		this.trajectory = trajectory;
		
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public Stop getOrigin() {
		return origin;
	}

	public void setOrigin(Stop origin) {
		this.origin = origin;
	}

	public Stop getDestination() {
		return destination;
	}

	public void setDestination(Stop destination) {
		this.destination = destination;
	}

	public Trajectory getTrajectory() {
		return trajectory;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}
}
