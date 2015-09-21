package tcc.stopsAndMoves;

import com.vividsolutions.jts.geom.Point;

/**
 * Representa uma amostra de uma trajetória.
 * @author giovani
 *
 */
public interface SamplePoint {
	/***
	 * Posição da amostra
	 * @return
	 */
	Point getPos();
	
	double getLat();
	
	double getLon();
	
	/**
	 * 
	 * @return Instante da amostra (em segundos)
	 */
	double getTime();
}
