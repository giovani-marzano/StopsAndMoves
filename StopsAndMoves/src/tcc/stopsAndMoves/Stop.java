package tcc.stopsAndMoves;

public class Stop {	
	private final Trajectory trajectory;
	private final long id;
	private double enterTime;
	private double leaveTime;
	
	public Stop(Trajectory trajectory, long id) {
		super();
		this.trajectory = trajectory;
		
		this.id = id;
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
