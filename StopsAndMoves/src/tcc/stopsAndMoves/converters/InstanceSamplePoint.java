package tcc.stopsAndMoves.converters;

import tcc.stopsAndMoves.SamplePoint;
import weka.core.Instance;

public class InstanceSamplePoint implements SamplePoint {
	public static final int INDEX_TID = 0;
	public static final int INDEX_TIME = 1;
	public static final int INDEX_LAT = 2;
	public static final int INDEX_LON = 3;
	
	private Instance instance;

	public InstanceSamplePoint(Instance instance) {
		super();
		this.instance = instance;
	}

	public long getTId() {
		return (long) instance.value(InstanceSamplePoint.INDEX_TID);
	}
	
	@Override
	public double getLon() {
		return instance.value(InstanceSamplePoint.INDEX_LON);
	}

	@Override
	public double getLat() {
		return instance.value(InstanceSamplePoint.INDEX_LAT);
	}

	@Override
	public double getTime() {
		return instance.value(InstanceSamplePoint.INDEX_TIME)/1000.0;
	}
}
