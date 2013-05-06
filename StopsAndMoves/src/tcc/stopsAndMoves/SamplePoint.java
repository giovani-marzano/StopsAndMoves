package tcc.stopsAndMoves;

/**
 * Representa uma amostra de uma trajetória.
 * @author giovani
 *
 */
public interface SamplePoint {
	/**
	 * @return Longitude da amostra
	 */
	double getLongitude();
	
	/**
	 * 
	 * @return Latitude da amostra
	 */
	double getLatitude();
	
	/**
	 * 
	 * @return Instante da amostra
	 */
	double getInstant();
}
