package selfOrganizingMap.converters;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

import selfOrganizingMap.GridType;
import selfOrganizingMap.NeighborType;
import selfOrganizingMap.SOMCell;
import selfOrganizingMap.SOMGrid;

public class SOMReader implements Closeable {
	public static final char COMMENT_CHAR = '#';
	public static final String SPLIT_REGEXP = "  *";
	public static final int MIN_VET_DIMENSION = 1;
	public static final int MAX_VET_DIMENSION = 1000;
	public static final int MIN_X_DIMENSION = 1;
	public static final int MAX_X_DIMENSION = 1000;
	public static final int MIN_Y_DIMENSION = 1;
	public static final int MAX_Y_DIMENSION = 1000;

	private BufferedReader reader = null;

	private int lineNumber = 0;

	private String readNextLine() throws IOException {
		String line = "";

		while (line != null
				&& (line.length() == 0 || line.charAt(0) == COMMENT_CHAR)) {
			line = reader.readLine();
			if (line != null) {
				line = line.trim();
			}
			lineNumber++;
		}

		if (line != null) {
			// Tratando comentario fora da primeira
			if (line.indexOf(COMMENT_CHAR) > 0) {
				throw new IOException("Comment only allowed on first column");
			}
		}

		return line;
	}

	public SOMReader(Reader in) {
		reader = new BufferedReader(in);
	}

	private SOMGrid readHeader() throws IOException {
		SOMGrid som = null;

		String line = readNextLine();

		if (line == null) {
			throw new IOException("No data found");
		}

		String[] fields = line.split(SPLIT_REGEXP, 5);

		int dimVet, dimX, dimY;
		GridType gridType;
		NeighborType neighType;

		if (fields.length < 5) {
			throw new IOException("Not enougth fields in header line: line "
					+ lineNumber);
		} else if (fields.length > 5) {
			throw new IOException("Spurious data at end of header line: line "
					+ lineNumber);
		}

		try {
			dimVet = Integer.valueOf(fields[0]);
		} catch (NumberFormatException e) {
			throw new IOException("Invalid vector dimension: line "
					+ lineNumber);
		}
		try {
			dimX = Integer.valueOf(fields[2]);
		} catch (NumberFormatException e) {
			throw new IOException("Invalid X dimension: line " + lineNumber);
		}
		if (dimX < 0) {
			throw new IOException("Dimension X negative");
		}

		try {
			dimY = Integer.valueOf(fields[3]);
		} catch (NumberFormatException e) {
			throw new IOException("Invalid Y dimension: line " + lineNumber);
		}
		if (dimY < 0) {
			throw new IOException("Dimension Y negative");
		}

		try {
			gridType = GridType.fromString(fields[1]);
		} catch (IllegalArgumentException e) {
			throw new IOException("Invalid grid type: line " + lineNumber, e);
		}

		try {
			neighType = NeighborType.fromString(fields[4]);
		} catch (IllegalArgumentException e) {
			throw new IOException("Invalid grid type: line " + lineNumber, e);
		}

		som = new SOMGrid(dimY, dimX, gridType);
		som.setNeighborType(neighType);
		som.setVetDimension(dimVet);

		return som;
	}

	private SOMCell readCell(int dimension) throws IOException {
		SOMCell cell = null;
		double[] vet = new double[dimension];
		String line;
		String[] fields;

		line = readNextLine();

		if (line == null) {
			return null;
		}

		fields = line.split(SPLIT_REGEXP);

		if (fields.length < dimension) {
			throw new IOException("Vector at line: " + lineNumber
					+ " with less than " + dimension + " elements");
		}

		try {
			for (int i = 0; i < dimension; i++) {
				vet[i] = Double.valueOf(fields[i]);
			}
		} catch (NumberFormatException ex) {
			throw new IOException("Bad number at line: " + lineNumber);
		}

		cell = new SOMCell();
		cell.setVet(vet);

		return cell;
	}

	public SOMGrid readSOM() throws IOException {
		if (reader == null) {
			throw new IOException("Reader not defined");
		}

		SOMGrid som = readHeader();

		for (int y = 0; y < som.getNumLines(); y++) {
			for (int x = 0; x < som.getNumCols(); x++) {
				SOMCell cell = readCell(som.getVetDimension());

				if (cell != null) {
					som.setCellAt(y, x, cell);
				} else {
					throw new IOException("Expected cell " + y + "," + x
							+ " not found: line " + lineNumber);
				}
			}
		}

		return som;
	}

	@Override
	public void close() throws IOException {
		if (reader != null) {
			reader.close();
			reader = null;
		}
	}
}
