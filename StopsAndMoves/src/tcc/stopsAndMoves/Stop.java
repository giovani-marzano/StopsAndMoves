package tcc.stopsAndMoves;

public class Stop {	
	private final Trajectory trajectory;
	private final long id;

	private Path path;
	
	public Stop(Trajectory trajectory, long id) {
		super();
		this.trajectory = trajectory;
		
		this.id = id;
	}

	public double getEnterTime() {
		return path.getEnterTime();
	}

	public double getLeaveTime() {
		return path.getLeaveTime();
	}

	public Trajectory getTrajectory() {
		return trajectory;
	}

	public long getId() {
		return id;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}
}
