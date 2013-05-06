package tcc.stopsAndMoves;

public class Stop {
	static private IdGenerator idGenerator;
	
	private final Trajectory trajectory;
	private final long id;
	private double enterTime;
	private double leaveTime;
	
	public Stop(Trajectory trajectory) {
		super();
		this.trajectory = trajectory;
		
		this.id = generateId(trajectory.getId());
	}

	static private long generateId( long trajectoryId ) {
		return getIdGenerator().generateId(trajectoryId);
	}
	
	public static IdGenerator getIdGenerator() {
		if ( idGenerator == null ) {
			idGenerator = new IdGenerator();
		}
		return idGenerator;
	}

	public static void setIdGenerator(IdGenerator idGenerator) {
		Stop.idGenerator = idGenerator;
	}

	public double getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(double enterTime) {
		this.enterTime = enterTime;
	}

	public double getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(double leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Trajectory getTrajectory() {
		return trajectory;
	}

	public long getId() {
		return id;
	}
	
}
