package tcc.stopsAndMoves;

import static org.junit.Assert.assertEquals;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestSMoTProcessTrajectory {

	private Trajectory trj;
	private SMoT smot = new SMoT();

	@Before
	public void prepareTrajectory() {
		trj = new Trajectory(1);

		// Monta uma trajetória simples
		for (int i = 0; i < 100; i++) {
			trj.add(new SimplePoint(i, 0, i));
		}
	}

	@Test
	public void testNoRegions() {
		// Crindo uma aplicação sem nenhuma região de interesse
		Application app = new Application(null);

		smot.processTrajectory(trj, app);

		assertEquals(1, trj.getMoves().size());
		assertEquals(trj.size(), trj.getMoves().get(0).getPath().size());
		assertEquals(0, trj.getStops().size());
	}

	@Test
	public void testEmptyRegions() {
		// Criando uma aplicação sem nenhuma região de interesse
		Set<SpatialFeature> set = new HashSet<>();
		Application app = new Application(set);

		smot.processTrajectory(trj, app);

		assertEquals(1, trj.getMoves().size());
		assertEquals(trj.size(), trj.getMoves().get(0).getPath().size());
		assertEquals(0, trj.getStops().size());
	}

	@Test
	public void testOnlyStop() {
		/*
		 * Criando uma aplicação cuja região de interesse cubra toda a
		 * trajetória.
		 */
		Set<SpatialFeature> set = new HashSet<>();
		Rectangle area = new Rectangle();
		area.setRect(-0.5, -0.5, 110, 2);
		SpatialFeature sp = new SpatialFeature(1, area, 2);
		set.add(sp);
		Application app = new Application(set);

		smot.processTrajectory(trj, app);

		assertEquals(1, trj.getStops().size());
		assertEquals(trj.size(), trj.getStops().get(0).getPath().size());
		assertEquals(sp, trj.getStops().get(0).getSpatialFeature());
		assertEquals(0, trj.getMoves().size());
	}

	@Test
	public void testStopMoveStop() {
		Set<SpatialFeature> set = new HashSet<>();
		Rectangle2D.Double area = new Rectangle2D.Double();

		area.setRect(-0.5, -0.5, 11, 2);
		SpatialFeature sp = new SpatialFeature(1, area, 2);
		set.add(sp);

		area = new Rectangle2D.Double();
		area.setRect(89.5, -0.5, 11, 2);
		sp = new SpatialFeature(2, area, 2);
		set.add(sp);

		Application app = new Application(set);

		smot.processTrajectory(trj, app);

		assertEquals(2, trj.getStops().size());
		Stop stop1, stop2;
		stop1 = trj.getStops().get(0);
		stop2 = trj.getStops().get(1);
		// Este stop deve ter os pontos [0,10]
		assertEquals(11, stop1.getPath().size());
		// Este stop deve ter os pontos [90,99]
		assertEquals(10, stop2.getPath().size());
		assertEquals(1, stop1.getSpatialFeature().getId());
		assertEquals(2, stop2.getSpatialFeature().getId());
		assertEquals(1, trj.getMoves().size());
		Move move = trj.getMoves().get(0);
		// Este move deve ter os pontos de [11,89]
		assertEquals(79, move.getPath().size());
		assertEquals(stop1, move.getOrigin());
		assertEquals(stop2, move.getDestination());
	}

	@Test
	public void testMoveStopMove() {
		Set<SpatialFeature> set = new HashSet<>();
		Rectangle2D.Double area = new Rectangle2D.Double();

		area.setRect(10.5, -0.5, 79, 2);
		SpatialFeature sp = new SpatialFeature(1, area, 2);
		set.add(sp);

		Application app = new Application(set);

		smot.processTrajectory(trj, app);

		assertEquals(1, trj.getStops().size());
		Stop stop1;
		stop1 = trj.getStops().get(0);
		// Este stop deve ter os pontos [11,89]
		assertEquals(79, stop1.getPath().size());
		assertEquals(1, stop1.getSpatialFeature().getId());
		assertEquals(2, trj.getMoves().size());
		Move move1 = trj.getMoves().get(0);
		Move move2 = trj.getMoves().get(1);
		// Este move deve ter os pontos de [0,10]
		assertEquals(11, move1.getPath().size());
		assertEquals(null, move1.getOrigin());
		assertEquals(stop1, move1.getDestination());
		// Este move deve ter os pontos de [90,99]
		assertEquals(10, move2.getPath().size());
		assertEquals(stop1, move2.getOrigin());
		assertEquals(null, move2.getDestination());
	}
}
