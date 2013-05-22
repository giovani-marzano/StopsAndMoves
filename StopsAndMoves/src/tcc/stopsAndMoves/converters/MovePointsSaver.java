package tcc.stopsAndMoves.converters;

import java.io.IOException;

import tcc.stopsAndMoves.Move;
import tcc.stopsAndMoves.SamplePoint;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class MovePointsSaver extends AbstractSaver {
	public static final int INDEX_TID = 0;
	public static final int INDEX_MID = 1;
	public static final int INDEX_TIME = 2;
	public static final int INDEX_LAT = 3;
	public static final int INDEX_LON = 4;
	public static final int NUM_ATTRIBUTES = 5;

	private Instances structure;

	@Override
	public Instances getStructure() {
		if (structure == null) {
			FastVector attInfo = new FastVector(NUM_ATTRIBUTES);

			attInfo.addElement(new Attribute("TId"));
			attInfo.addElement(new Attribute("MID"));
			attInfo.addElement(new Attribute("Time", dateFormat));
			attInfo.addElement(new Attribute("Lat"));
			attInfo.addElement(new Attribute("Lon"));

			structure = new Instances("Move_Points", attInfo, 0);
		}

		return structure;
	}

	@Override
	public void resetStructure() {
		structure = null;
	}

	public void writeIncremental(Move move) throws IOException {
		if (saver == null) {
			throw new IOException("Saver not defined");
		}

		if (move == null) {
			saver.writeIncremental(null);
			return;
		}

		double[] values = new double[NUM_ATTRIBUTES];

		for (SamplePoint sp : move.getPath()) {
			values[INDEX_TID] = move.getTrajectory().getId();
			values[INDEX_MID] = move.getId();
			values[INDEX_TIME] = sp.getTime();
			values[INDEX_LAT] = sp.getLat();
			values[INDEX_LON] = sp.getLon();

			Instance ins = new Instance(1, values);

			saver.writeIncremental(ins);
		}
	}
}
