package tcc.stopsAndMoves.converters;

import java.io.IOException;

import tcc.stopsAndMoves.Trajectory;

public interface ITrajectorySaver {

	/**
	 * Persiste os stops e moves de uma trajetória.
	 * 
	 * Ao saver será apresentada um Trajectory que possui sua coleção de
	 * Stops e Moves preenchida.
	 * 
	 * @param trj
	 * @throws IOException
	 */
	void writeStopsAndMoves(Trajectory trj) throws IOException;

}