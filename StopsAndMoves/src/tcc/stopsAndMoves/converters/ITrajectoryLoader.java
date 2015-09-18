package tcc.stopsAndMoves.converters;

import java.io.IOException;

import tcc.stopsAndMoves.Trajectory;

public interface ITrajectoryLoader {

	/**
	 * O carregador deve retornar uma trajetória preenchida com a sequência de
	 * SamplePoints que a compõe.
	 * 
	 * @return A próxima trajetória ou null se não existem mais trajetórias a
	 *         serem lidas
	 * @throws IOException
	 *             Se ocorrer algum erro durante o processamento.
	 */
	Trajectory getNextTrajectory() throws IOException;

}