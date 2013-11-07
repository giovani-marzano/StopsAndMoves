package selfOrganizingMap.converters;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import selfOrganizingMap.GridType;
import selfOrganizingMap.NeighborType;
import selfOrganizingMap.SOMCell;
import selfOrganizingMap.SOMGrid;

import static org.junit.Assert.*;

public class SOMReaderTest {

	private SOMReader reader;

	private static final double[][][] dadosTeste1 = {
			{ { 2.1, -3.2, 5.5 }, { 2.0, 1.2, 4.2 }, { 8.2, 8.1, -2.2 },
					{ 1.4, -2.32, 8.12 }, },
			{ { 1.0, 2.3, 8.2 }, { 1.3, -2.4, 8.1 }, { 7.2, 3.2, 8.1 },
					{ 1.2, 3.2, -7.7 }, },
			{ { 3.3, 2.2, 1.1 }, { 2.2, -1.1, 3.3 }, { 1.1, 6.6, -7.3 },
					{ -2.3, 4.4, 2.1 }, }, };

	private static final double[][][] dadosTeste2 = {
			{ { 2.1, 3.2, 5.5 }, { 2.0, -1.2, 4.2 }, { 8.2, 8.1, 2.2 }, },
			{ { 1.4, 2.32, 8.12 }, { 1.0, 2.3, -8.2 }, { 1.3, 2.4, 8.1 }, },
			{ { 7.2, -3.2, 8.1 }, { 1.2, 3.2, 7.7 }, { 3.3, -2.2, 1.1 }, },
			{ { -2.2, 1.1, 3.3 }, { 1.1, -6.6, 7.3 }, { 2.3, 4.4, -2.1 }, }, };

	private SOMReader getReaderFromResource(String name) {
		InputStream is = getClass().getResourceAsStream(name);

		InputStreamReader isr = new InputStreamReader(is);

		reader = new SOMReader(isr);

		return reader;
	}

	@After
	public void closeReader() {
		if (reader != null) {
			try {
				reader.close();
			} catch (Exception e) {
			}
			reader = null;
		}
	}

	private void assertVetValues(SOMGrid som, double[][][] vet) {

		for (int i = 0; i < som.getNumLines(); i++) {
			for (int j = 0; j < som.getNumCols(); j++) {

				SOMCell cell = som.getCellAt(i, j);

				double[] cellVet = cell.getVet();

				assertEquals(vet[i][j].length, cellVet.length);

				for (int k = 0; k < vet[i][j].length; k++) {
					assertEquals("Value " + i + "," + j + "," + k,
							vet[i][j][k], cellVet[k], 0.01);
				}
			}
		}
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testTC_01() throws IOException {
		SOMReader sr = getReaderFromResource("resource/TC_01.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
		assertEquals(3, som.getVetDimension());
		assertEquals(4, som.getNumCols());
		assertEquals(3, som.getNumLines());
		assertEquals(GridType.RECT, som.getGridType());
		assertEquals(NeighborType.BUBBLE, som.getNeighborType());

		assertVetValues(som, dadosTeste1);
	}

	@Test
	public void testTC_02() throws IOException {
		SOMReader sr = getReaderFromResource("resource/TC_02.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
		assertEquals(3, som.getVetDimension());
		assertEquals(3, som.getNumCols());
		assertEquals(4, som.getNumLines());
		assertEquals(GridType.HEXA, som.getGridType());
		assertEquals(NeighborType.GAUSSIAN, som.getNeighborType());

		assertVetValues(som, dadosTeste2);
	}

	@Test
	public void testTC_03() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_03.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_04() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_04.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_05() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_05.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_06() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_06.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_07() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_07.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_08() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_08.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_09() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_09.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_10() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_10.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_11() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_11.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_12() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_12.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_13() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_13.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_14() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_14.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_15() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_15.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_16() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_16.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_17() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_17.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_18() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_18.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_19() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_19.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_20() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_20.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_21() throws IOException {
		thrown.expect(IOException.class);
 
		SOMReader sr = getReaderFromResource("resource/TC_21.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_22() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_22.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_23() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_23.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}

	@Test
	public void testTC_24() throws IOException {
		thrown.expect(IOException.class);

		SOMReader sr = getReaderFromResource("resource/TC_24.txt");

		SOMGrid som = sr.readSOM();

		assertNotNull(som);
	}
}
