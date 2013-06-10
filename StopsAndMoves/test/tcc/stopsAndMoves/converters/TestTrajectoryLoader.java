package tcc.stopsAndMoves.converters;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import tcc.stopsAndMoves.SamplePoint;
import tcc.stopsAndMoves.Trajectory;
import weka.core.converters.ArffLoader;

public class TestTrajectoryLoader {

	@Test
	public void testGetNextTrajectory() {
		ArffLoader loader = new ArffLoader();

		InputStream is = getClass().getResourceAsStream(
				"resource/trajectories1.arff");

		try {
			loader.setSource(is);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Falha ao setar a origem de dados no carregador");
		}

		TrajectoryLoader tloader = new TrajectoryLoader();

		tloader.setPointsLoader(loader);

		Trajectory trj = null;

		try {
			// Recuperando a primeira trajetoria - possui 10 amostras
			trj = tloader.getNextTrajectory();
			assertNotNull(trj);
			assertEquals(10, trj.size());
			
			double tes = 0.1;
			double time = 0;
			for ( SamplePoint p : trj ) {
				assertEquals(1+tes, p.getLat(), 0.05);
				assertEquals(-2-tes, p.getLon(), 0.05);
				tes += 0.1;
				if ( time != 0 ) {
					assertEquals(60000.0, p.getTime()-time, 0.5);
				}
				time = p.getTime();
			}

			// Recuperando a segunda trajetoria - 1 amostra
			trj = tloader.getNextTrajectory();
			assertNotNull(trj);
			assertEquals(1, trj.size());
			
			tes = 0.1;
			time = 0;
			for ( SamplePoint p : trj ) {
				assertEquals(1+tes, p.getLat(), 0.05);
				assertEquals(-2-tes, p.getLon(), 0.05);
				tes += 0.1;
				if ( time != 0 ) {
					assertEquals(60000.0, p.getTime()-time, 0.5);
				}
				time = p.getTime();

			}

			// Recuperando a terceira trajetoria trajetoria - 5 amostras
			trj = tloader.getNextTrajectory();
			assertNotNull(trj);
			assertEquals(5, trj.size());
			
			tes = 0.1;
			time = 0;
			for ( SamplePoint p : trj ) {
				assertEquals(1+tes, p.getLat(), 0.05);
				assertEquals(-2-tes, p.getLon(), 0.05);
				tes += 0.1;
				if ( time != 0 ) {
					assertEquals(60000.0, p.getTime()-time, 0.5);
				}
				time = p.getTime();
			}
			
			// Não há mais trajetórias no arquivo
			trj = tloader.getNextTrajectory();
			assertNull(trj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
