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
	double getLon();
	
	/**
	 * 
	 * @return Latitude da amostra
	 */
	double getLat();
	
	/**
	 * 
	 * @return Instante da amostra
	 */
	double getTime();
}
