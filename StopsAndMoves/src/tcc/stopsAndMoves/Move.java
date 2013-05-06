package tcc.stopsAndMoves;

import java.util.ArrayList;
import java.util.List;

public class Move {
	private final Trajectory trajectory;
	private final long id; 
	private List<SamplePoint> pointsList;
	private Stop origin;
	private Stop destination;
	
	public Move(Trajectory trajectory, long id) {
		super();
		if (trajectory == null) {
			throw new NullPointerException("trajectory null");
		}
		this.trajectory = trajectory;
		
		this.id = id;
		
		pointsList = new ArrayList<>();
	}

	public long getId() {
		return id;
	}
	
	public void addPoint(SamplePoint p) {
		pointsList.add(p);
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
