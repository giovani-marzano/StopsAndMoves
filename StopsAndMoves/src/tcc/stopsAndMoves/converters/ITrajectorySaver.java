package tcc.stopsAndMoves.converters;

import java.io.IOException;

import tcc.stopsAndMoves.Trajectory;

public interface ITrajectorySaver {

	void writeIncremental(Trajectory trj) throws IOException;

}