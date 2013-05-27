package tcc.stopsAndMoves;

import java.io.IOException;

import tcc.stopsAndMoves.converters.TrajectoryLoader;
import tcc.stopsAndMoves.converters.TrajectorySaver;

/**
 * Implementação do algoritmo Stops and Moves on Trajectories (SMoT).
 * 
 * A implementação se baseia no algoritmo descrito no artigo "A Model for
 * Enriching Trajectories with Semantic Geographical Information" de Luiz Otavio
 * Alvares et al.
 * 
 * @author giovani
 * 
 */
public class SMoT {
	
	private Application application;
	private TrajectorySaver trajectoySaver;
	private TrajectoryLoader trajectoryLoader;

	public boolean processNextTrajectory() throws IOException {
		Trajectory trj;
		
		trj = trajectoryLoader.getNextTrajectory();
		
		if (trj != null) {
			processTrajectory(trj);
			
			trajectoySaver.writeIncremental(trj);
			
			return true;
		}
		else {
			return false;
		}
	}
	
	public void processAll() throws IOException {
		boolean hasNext = true;
		
		while (hasNext) {
			hasNext = processNextTrajectory();
		}
	}
	
	public boolean processSome(int quant) throws IOException {
		boolean hasNext = true;
		int count = 0;
		
		while ( count < quant && hasNext ) {
			hasNext = processNextTrajectory();
			count++;
		}
		
		return hasNext;
	}
	
	public void processTrajectory(Trajectory trj) {

		Path path = new Path();

		SpatialFeature regionCandidate = null;

		int atual = 0;
		while (atual < trj.size()) {
			regionCandidate = application.findIntersectingRegion(trj.get(atual));
			if (regionCandidate != null) {
				// O ponto intercepta uma região, aqui estamos no início de um
				// possível STOP. Criamos um novo path atual, guardando o
				// anterior na trajetoria como um Move.
				trj.addMove(path);
				path = new Path();

				// Adicionando o ponto ao path atual
				path.add(trj.get(atual));
				atual++;

				while (atual < trj.size()
						&& regionCandidate.contains(trj.get(atual))) {
					// Equanto estiver na mesma regiao adiciona o ponto no path
					// atual
					path.add(trj.get(atual));
					atual++;
				}

				if (path.getDeltaTime() >= regionCandidate.getMinimunTime()) {
					// Neste ponto sabemos que todos os pontos do path sao um
					// STOP. Inserimos este stop na trajetória
					trj.addStop(path, regionCandidate);

					// Inicia um novo path
					path = new Path();
				}
			} else {
				// O ponto atual não intercepta nenhuma região de interesse.
				// Adicionamos ele ao path corrente.
				path.add(trj.get(atual));
				atual++;
			}
			if (atual < trj.size()) {
				/*
				 * Este trecho é uma otimização para não ficar procurando região
				 * de interesse para cada ponto da trajetória (custoso). Aqui o
				 * algoritmo busca uma sequencia de pontos que não poderia ser
				 * um STOP por ter seu delta T menor que o menor tempo mínimo de
				 * todas as regiões.
				 */
				int j = 1;
				while (atual + j < trj.size()) {
					double t1 = trj.get(atual).getTime();
					double t2 = trj.get(atual + j).getTime();
					if (t2 - t1 < application.getMinTime()) {
						j++;
					} else {
						/*
						 * A sequencia de pontos [atual, atual+j-1) com certeza
						 * não constitui um STOP pois não tem a duração mínima
						 * para tal
						 */
						break;
					}
				}
				/*
				 * Para ter certeza que nenhum ponto de [atual, atual+j-1]
				 * participa de um STOP, analisa-se se o ultimo ponto
				 * (atual+j-1) intercepta alguma região de interesse.
				 */
				regionCandidate = application.findIntersectingRegion(trj.get(atual + j
						- 1));
				if (regionCandidate == null) {
					/*
					 * Aqui pode-se afirmar que nenhum ponto de atual até
					 * atual+j-1 pertence a um STOP. Assim adiciona-se estes
					 * pontos ao path atual e seta-se atual com indice do
					 * primeiro ponto após este intervalo
					 */
					path.addAll(trj.subList(atual, atual + j));
					atual = atual + j;
				}
			}
		}

		// Por fim adiciona-se o ultimo path à trajetoria como um Move
		trj.addMove(path);
	}

	public TrajectorySaver getTrajectoySaver() {
		return trajectoySaver;
	}

	public void setTrajectoySaver(TrajectorySaver trajectoySaver) {
		this.trajectoySaver = trajectoySaver;
	}

	public TrajectoryLoader getTrajectoryLoader() {
		return trajectoryLoader;
	}

	public void setTrajectoryLoader(TrajectoryLoader trajectoryLoader) {
		this.trajectoryLoader = trajectoryLoader;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}
