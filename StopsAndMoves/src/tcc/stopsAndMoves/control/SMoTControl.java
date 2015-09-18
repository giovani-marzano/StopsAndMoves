package tcc.stopsAndMoves.control;

import java.io.IOException;
import java.util.Set;

import tcc.stopsAndMoves.Application;
import tcc.stopsAndMoves.SMoT;
import tcc.stopsAndMoves.SMoTException;
import tcc.stopsAndMoves.SpatialFeature;
import tcc.stopsAndMoves.converters.weka.SpatialFeatureLoader;
import tcc.stopsAndMoves.converters.weka.TrajectoryLoader;
import tcc.stopsAndMoves.converters.weka.TrajectorySaver;
import weka.core.converters.Loader;
import weka.core.converters.Saver;

public class SMoTControl {
	private SMoT smot;
	
	private TrajectoryLoader trjLoader;
	private TrajectorySaver trjSaver;
	private Application application;
	
	/**
	 * 
	 * @return The number of processed trajectories
	 */
	public int getNumProcessedTrj() {
		if ( smot != null ) {
			return smot.getNumProcessedTrajectories();
		}
		return 0;
	}
	
	public void createTrajectoryLoader(Loader pointsLoader) {
		trjLoader = new TrajectoryLoader();
		
		trjLoader.setPointsLoader(pointsLoader);
	}
	
	public void createTrajectorySaver(Saver stopSaver, Saver moveSaver, Saver movePtSaver) {
		trjSaver = new TrajectorySaver();
		
		trjSaver.setMoveSaver(moveSaver);
		trjSaver.setMovePointsSaver(movePtSaver);
		trjSaver.setStopSaver(stopSaver);
	}
	
	public void createApplication(Loader regionLoader, Loader regionPtLoader) throws IOException {
		SpatialFeatureLoader spLoader = new SpatialFeatureLoader();
		
		spLoader.setRegionLoader(regionLoader);
		spLoader.setRegionPointsLoader(regionPtLoader);
		
		Set<SpatialFeature> set = spLoader.getSpatialFeatures();
		
		application = new Application(set);
	}
	
	public void createSMoT() throws SMoTException {
		smot = new SMoT();
		
		if (application == null) {
			throw new SMoTException("Application not created");
		}
		
		if (trjLoader == null) {
			throw new SMoTException("Trajectory Loader not created");
		}
		
		if (trjLoader == null) {
			throw new SMoTException("Trajectory Saver not created");
		}
		
		smot.setApplication(application);
		smot.setTrajectoryLoader(trjLoader);
		smot.setTrajectoySaver(trjSaver);
	}
	
	public boolean processSomeTrajectories(int num) throws SMoTException, IOException {
		boolean hasNext = false;
		
		if (smot == null) {
			throw new SMoTException("SMoT not created");
		}
		
		hasNext = smot.processSome(num);
		
		if ( !hasNext ) {
			// Acabou o processamento. Escrevendo null no Saver para fechar os arquivos
			trjSaver.writeStopsAndMoves(null);
		}
		
		return hasNext;
	}
}
