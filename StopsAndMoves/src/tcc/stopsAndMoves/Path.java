package tcc.stopsAndMoves;

import java.util.ArrayList;

/**
 * Representa uma parte de uma trajetória
 * @author giovani
 *
 */
public class Path extends ArrayList<SamplePoint> {
	private static final long serialVersionUID = 1L;

	private PathType type = PathType.MOVE;

	public PathType getType() {
		return type;
	}

	public void setType(PathType type) {
		this.type = type;
	}
	
	public double getEnterTime() {
		if (isEmpty()) {
			return 0;
		}
		return get(0).getTime();
	}
	
	public double getLeaveTime() {
		if (isEmpty()) {
			return 0;
		}
		return get(size() - 1).getTime();
	}
	
	public double getDeltaTime() {
		return getLeaveTime() - getEnterTime();
	}
	
}