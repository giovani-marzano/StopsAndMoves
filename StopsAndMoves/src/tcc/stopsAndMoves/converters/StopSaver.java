package tcc.stopsAndMoves.converters;

import java.io.IOException;

import tcc.stopsAndMoves.Stop;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.Saver;

public class StopSaver extends AbstractSaver {
	public static final int INDEX_TID = 0;
	public static final int INDEX_SID = 1;
	public static final int INDEX_SFID = 2;
	public static final int INDEX_ENTER_TIME = 3;
	public static final int INDEX_LEAVE_TIME = 4;
	public static final int NUM_ATTRIBUTES = 5;
	
	private Instances structure;
	
	@Override
	public Instances getStructure() {
		if ( structure == null ) {
			FastVector attInfo = new FastVector(NUM_ATTRIBUTES);
			
			attInfo.addElement(new Attribute("TId"));
			attInfo.addElement(new Attribute("SId"));
			attInfo.addElement(new Attribute("SFId"));
			attInfo.addElement(new Attribute("EnterTime", dateFormat));
			attInfo.addElement(new Attribute("LeaveTime", dateFormat));
			
			structure = new Instances("Stop", attInfo, 0);
		}
		
		return structure;
	}
	
	@Override
	public void resetStructure() {
		structure = null;
	}

	public void writeIncremental(Stop stop) throws IOException {
		if (saver == null) {
			throw new IOException("Saver not defined");
		}
		
		if ( stop == null ) {
			if (saver.getWriteMode() == Saver.INCREMENTAL) {
				saver.writeIncremental(null);
			}
			
			return;
		}
		
		double [] values = new double[NUM_ATTRIBUTES];
		
		values[INDEX_TID] = stop.getTrajectory().getId();
		values[INDEX_SID] = stop.getId();
		values[INDEX_SFID] = stop.getSpatialFeature().getId();
		values[INDEX_ENTER_TIME] = stop.getEnterTime();
		values[INDEX_LEAVE_TIME] = stop.getLeaveTime();
		
		Instance ins = new Instance(1, values);
		
		saver.writeIncremental(ins);
	}	
}
