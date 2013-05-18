package tcc.stopsAndMoves.converters;

import java.io.IOException;

import tcc.stopsAndMoves.Move;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class MoveSaver extends AbstractSaver {
	public static final int INDEX_TID = 0;
	public static final int INDEX_MID = 1;
	public static final int INDEX_SID_ORIG = 2;
	public static final int INDEX_SID_DEST = 3;
	public static final int NUM_ATTRIBUTES = 4;

	private Instances structure = null;
	private MovePointsSaver pointsSaver = null;

	@Override
	public Instances getStructure() {
		if (structure == null) {
			FastVector attInfo = new FastVector(NUM_ATTRIBUTES);

			attInfo.addElement(new Attribute("TId"));
			attInfo.addElement(new Attribute("MID"));
			attInfo.addElement(new Attribute("SIdOrig"));
			attInfo.addElement(new Attribute("SIdDest"));

			structure = new Instances("Move", attInfo, 0);
		}

		return structure;
	}

	@Override
	public void resetStructure() {
		structure = null;
	}

	public void writeIncremental(Move move) throws IOException {
		if (move == null || saver == null) {
			return;
		}

		double[] values = new double[NUM_ATTRIBUTES];

		values[INDEX_TID] = move.getTrajectory().getId();
		values[INDEX_MID] = move.getId();
		if (move.getOrigin() != null) {
			values[INDEX_SID_ORIG] = move.getOrigin().getId();
		} else {
			values[INDEX_SID_ORIG] = Instance.missingValue();
		}
		if (move.getDestination() != null) {
			values[INDEX_SID_DEST] = move.getDestination().getId();
		} else {
			values[INDEX_SID_DEST] = Instance.missingValue();
		}

		Instance ins = new Instance(1, values);

		saver.writeIncremental(ins);
		
		// Slava também os pontos se houver um pointSaver definido
		if ( pointsSaver != null ) {
			pointsSaver.writeIncremental(move);
		}
	}

	public MovePointsSaver getPointsSaver() {
		return pointsSaver;
	}

	public void setPointsSaver(MovePointsSaver pointsSaver) {
		this.pointsSaver = pointsSaver;
	}
}
