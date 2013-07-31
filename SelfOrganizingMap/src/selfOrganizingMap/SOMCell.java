package selfOrganizingMap;

public class SOMCell {
	private double[] vet;
	
	public SOMCell() {
	}
	
	public int getDimension() {
		return vet.length;
	}
	
	public double[] getVet() {
		return vet;
	}

	public void setVet(double[] vet) {
		this.vet = vet;
	}
	
}
