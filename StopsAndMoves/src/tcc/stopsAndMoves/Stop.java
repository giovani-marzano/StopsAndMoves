package tcc.stopsAndMoves;

public class Stop {
	private final Trajectory trajectory;
	private final long id;

	private SpatialFeature spatialFeature;
	private Path path;

	public Stop(Trajectory trajectory, long id, SpatialFeature sp) {
		super();
		this.trajectory = trajectory;
		this.id = id;
		this.spatialFeature = sp;
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

	public SpatialFeature getSpatialFeature() {
		return spatialFeature;
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
