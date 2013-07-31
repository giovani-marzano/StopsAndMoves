package selfOrganizingMap;

/**
 * Representa um SOM em topologia de grade
 * 
 * @author giovani
 */
public class SOMGrid {
	private GridType gridType;
	private NeighborType neighborType;
	private int vetDimension;
	
	private SOMCell[][] grid;
	
	public SOMGrid(int lines, int cols, GridType type) {
		grid = new SOMCell[lines][cols];
		gridType = type;
	}
	
	public SOMCell getCellAt(int i, int j) {
		return grid[i][j];
	}
	
	public void setCellAt(int i, int j, SOMCell cell) {
		grid[i][j] = cell;
	}

	public GridType getGridType() {
		return gridType;
	}
	
	public int getNumLines() {
		return grid.length;
	}
	
	public int getNumCols() {
		return grid[0].length;
	}

	public NeighborType getNeighborType() {
		return neighborType;
	}

	public void setNeighborType(NeighborType neighborType) {
		this.neighborType = neighborType;
	}

	public void setGridType(GridType gridType) {
		this.gridType = gridType;
	}

	public int getVetDimension() {
		return vetDimension;
	}

	public void setVetDimension(int vetDimension) {
		this.vetDimension = vetDimension;
	}
}
