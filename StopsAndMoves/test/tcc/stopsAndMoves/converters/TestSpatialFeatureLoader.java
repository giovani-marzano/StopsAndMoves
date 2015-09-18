package tcc.stopsAndMoves.converters;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.junit.Test;

import tcc.stopsAndMoves.SimplePoint;
import tcc.stopsAndMoves.SpatialFeature;
import tcc.stopsAndMoves.converters.weka.SpatialFeatureLoader;
import weka.core.converters.ArffLoader;

public class TestSpatialFeatureLoader {

	@Test
	public void testGetSpatialFeatures() {
		InputStream is = null;
		ArffLoader spLoader = new ArffLoader();
		ArffLoader pointLoader = new ArffLoader();

		try {
			is = getClass().getResourceAsStream(
					"resource/spatialFeatures1.arff");
			spLoader.setSource(is);

			is = getClass().getResourceAsStream(
					"resource/spatialFeaturePoints.arff");
			pointLoader.setSource(is);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Falha ao setar origem dos dados");
		}

		SpatialFeatureLoader spl = new SpatialFeatureLoader();
		spl.setRegionLoader(spLoader);
		spl.setRegionPointsLoader(pointLoader);

		// Pontos contidos nas regiões do arquivo
		SimplePoint pR1 = new SimplePoint(12, 12, 20);
		SimplePoint pR2 = new SimplePoint(25, 25, 20);
		SimplePoint pR3 = new SimplePoint(52, 52, 20);

		try {
			Set<SpatialFeature> set = spl.getSpatialFeatures();

			boolean ok1 = false, ok2 = false, ok3 = false, ok4 = false;
			for (SpatialFeature sf : set) {
				if (sf.getId() == 1) {
					ok1 = true;
					assertEquals(10, sf.getMinimunTime(), 0.1);
					assertNotNull(sf.getArea());
					assertTrue(sf.contains(pR1));
					assertFalse(sf.contains(pR2));
					assertFalse(sf.contains(pR3));
				} else if (sf.getId() == 2) {
					ok2 = true;
					assertEquals(20, sf.getMinimunTime(), 0.1);
					assertNotNull(sf.getArea());
					assertFalse(sf.contains(pR1));
					assertTrue(sf.contains(pR2));
					assertFalse(sf.contains(pR3));
				} else if (sf.getId() == 3) {
					ok3 = true;
					assertEquals(0, sf.getMinimunTime(), 0.1);
					assertNotNull(sf.getArea());
					assertFalse(sf.contains(pR1));
					assertFalse(sf.contains(pR2));
					assertTrue(sf.contains(pR3));
				} else if (sf.getId() == 4) {
					ok4 = true;
					assertEquals(40, sf.getMinimunTime(), 0.1);
					assertNull(sf.getArea());
					assertFalse(sf.contains(pR1));
					assertFalse(sf.contains(pR2));
					assertFalse(sf.contains(pR3));
				} else {
					fail("Regiao " + sf.getId() + " não deveria existir");
				}
			}

			assertTrue(ok1);
			assertTrue(ok2);
			assertTrue(ok3);
			assertTrue(ok4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
