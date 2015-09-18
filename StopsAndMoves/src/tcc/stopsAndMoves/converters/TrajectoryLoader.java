package tcc.stopsAndMoves.converters;

import java.io.IOException;

import tcc.stopsAndMoves.Trajectory;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.Loader;

/**
 * Carregador de trajetórias
 * 
 * As trajetórias são carregadas a partir do Loader definido com
 * setPointsLoader. Assume-se que os pontos estão agrupados por trajetória no
 * Loader fornecido.
 * 
 * @author giovani
 * 
 */
public class TrajectoryLoader implements ITrajectoryLoader {
	private Loader pointsLoader = null;

	private Instances structure = null;

	private InstanceSamplePoint lastInstance = null;

	private InstanceSamplePoint getNextSamplePoint() throws IOException {
		Instance ins = pointsLoader.getNextInstance(getStructure());
		if (ins != null) {
			return new InstanceSamplePoint(ins);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see tcc.stopsAndMoves.converters.ITrajectoryLoader#getNextTrajectory()
	 */
	@Override
	public Trajectory getNextTrajectory() throws IOException {
		Trajectory trj = null;
		long tID = 0;

		if (lastInstance == null) {
			lastInstance = getNextSamplePoint();
		}

		if (lastInstance != null) {
			tID = lastInstance.getTId();

			trj = new Trajectory(tID);

			while (lastInstance != null && lastInstance.getTId() == trj.getId()) {
				trj.add(lastInstance);
				lastInstance = getNextSamplePoint();
			}
		}

		return trj;
	}

	public Instances getStructure() throws IOException {
		if (structure == null) {
			if (pointsLoader != null) {
				structure = pointsLoader.getStructure();
			}
		}
		return structure;
	}

	public Loader getPointsLoader() {
		return pointsLoader;
	}

	public void setPointsLoader(Loader pointsLoader) {
		// Resetando o estado de leitura
		lastInstance = null;
		this.pointsLoader = pointsLoader;
	}
}
