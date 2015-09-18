package tcc.stopsAndMoves;

import java.util.Set;

/**
 * Here an Application means a collection of spatial features that are
 * relevant for a particular analysis (application of the algorithm).
 * 
 * @author Giovani Marzano
 *
 */
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
		if (regionSet == null) {
			minTime = 0;
		} else {
			minTime = Double.POSITIVE_INFINITY;
			for (SpatialFeature sp : regionSet) {
				if (sp.getMinimunTime() < minTime) {
					minTime = sp.getMinimunTime();
				}
			}
		}
		minTimeCalculated = true;
	}

	public SpatialFeature findIntersectingRegion(SamplePoint point) {
		if (regionSet == null) {
			return null;
		}
		for (SpatialFeature sf : regionSet) {
			if (sf.contains(point)) {
				return sf;
			}
		}

		return null;
	}
}
