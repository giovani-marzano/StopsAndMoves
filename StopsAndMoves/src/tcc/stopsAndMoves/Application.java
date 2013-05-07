package tcc.stopsAndMoves;

import java.util.Set;

public class Application {
	private Set<SpatialFeature> regionSet;
	private double minTime;
	private boolean minTimeCalculated = false;
	
	public Application(Set<SpatialFeature> regionSet) {
		super();
		this.regionSet = regionSet;
	}

	public double getMinTime() {
		if (!minTimeCalculated) {
			calculateMinTime();
		}
		return minTime;
	}

	public void calculateMinTime() {
		minTime = Double.POSITIVE_INFINITY;
		for (SpatialFeature sp : regionSet ) {
			if (sp.getMinimunTime() < minTime) {
				minTime = sp.getMinimunTime();
			}
		}
		minTimeCalculated = true;
	}
	
	public SpatialFeature findIntersectingRegion(SamplePoint point) {
		for (SpatialFeature sf : regionSet) {
			if (sf.contains(point)) {
				return sf;
			}
		}

		return null;
	}
}
