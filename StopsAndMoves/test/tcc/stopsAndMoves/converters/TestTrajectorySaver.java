package tcc.stopsAndMoves.converters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import tcc.stopsAndMoves.Path;
import tcc.stopsAndMoves.SimplePoint;
import tcc.stopsAndMoves.SpatialFeature;
import tcc.stopsAndMoves.Trajectory;
import weka.core.converters.ArffSaver;

public class TestTrajectorySaver {

	private TrajectorySaver trajectorySaver;
	private File moveFile;
	private File movePointsFile;
	private File stopFile;
	private Trajectory trj;
	
	private static List<SpatialFeature> spList;

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@BeforeClass
	public static void createSpList() {
		spList = new ArrayList<>();
		
		spList.add(new SpatialFeature(0));
		spList.add(new SpatialFeature(1));
	}
	
	@AfterClass
	public static void destroySpList() {
		spList = null;
	}
	
	@Before
	public void createSaver() throws IOException {
		moveFile = tempFolder.newFile("moves.arff");
		movePointsFile = tempFolder.newFile("movePoints.arff");
		stopFile = tempFolder.newFile("stops.arff");
		
		trajectorySaver = new TrajectorySaver();

		ArffSaver saver = new ArffSaver();
		saver.setFile(moveFile);

		trajectorySaver.setMoveSaver(saver);

		saver = new ArffSaver();
		saver.setFile(movePointsFile);

		trajectorySaver.setMovePointsSaver(saver);

		saver = new ArffSaver();
		saver.setFile(stopFile);

		trajectorySaver.setStopSaver(saver);
	}
	
	@Before
	public void createTrajectory() {
		trj = new Trajectory(1);

		// Monta uma trajetória simples
		for (int i = 0; i < 100; i++) {
			trj.add(new SimplePoint(i, 0, i));
		}
	}
	
	@Test
	public void noStopsOrMoves() throws IOException {
		trajectorySaver.writeIncremental(trj);
		trajectorySaver.writeIncremental(null);
		System.out.println(moveFile.getPath());
	}
	
	@Test
	public void justOneMove() throws IOException {
		trj.addMove(trj);
		trajectorySaver.writeIncremental(trj);
		trajectorySaver.writeIncremental(null);
		
		System.out.println(moveFile.getPath());
	}
	
	@Test
	public void justOneStop() throws IOException {
		trj.addStop(trj, spList.get(0));
		trajectorySaver.writeIncremental(trj);
		trajectorySaver.writeIncremental(null);
		System.out.println(moveFile.getPath());
	}
	
	@Test
	public void moveStopMove() throws IOException {
		trj.addMove(new Path(trj.subList(0, 30)));
		trj.addStop(new Path(trj.subList(30, 60)), spList.get(0));
		trj.addMove(new Path(trj.subList(60, 100)));
		trajectorySaver.writeIncremental(trj);
		trajectorySaver.writeIncremental(null);
		System.out.println(moveFile.getPath());
	}
	
	@Test
	public void stopMoveStop() throws IOException {
		trj.addStop(new Path(trj.subList(0, 30)), spList.get(0));
		trj.addMove(new Path(trj.subList(30, 60)));
		trj.addStop(new Path(trj.subList(60, 100)), spList.get(1));
		trajectorySaver.writeIncremental(trj);
		trajectorySaver.writeIncremental(null);
		System.out.println(moveFile.getPath());
	}
}
