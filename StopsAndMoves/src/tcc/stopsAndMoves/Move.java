package tcc.stopsAndMoves;

import java.util.List;

public class Move {
	static private IdGenerator idGenerator;
	
	private final Trajectory trajectory;
	private final long id; 
	private List<SamplePoint> pointsList;
	private Stop origin;
	private Stop destination;
	
	public Move(Trajectory trajectory) {
		super();
		if (trajectory == null) {
			throw new NullPointerException("trajectory null");
		}
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
		Move.idGenerator = idGenerator;
	}

	public long getId() {
		return id;
	}

	public List<SamplePoint> getPointsList() {
		return pointsList;
	}

	public void setPointsList(List<SamplePoint> pointsList) {
		this.pointsList = pointsList;
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
	
}
