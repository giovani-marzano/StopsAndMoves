package tcc.stopsAndMoves;

import java.util.ArrayList;
import java.util.List;

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

	public void processTrajectory(Trajectory trj,
			Application app) {
		
		List<Path> pathList = new ArrayList<>();
		
		Path path = new Path();

		SpatialFeature regionCandidate = null;

		int atual = 0;
		while (atual < trj.size()) {
			regionCandidate = app.findIntersectingRegion(trj.get(atual));
			if (regionCandidate != null) {
				// O ponto intercepta uma região, aqui estamos no início de um
				// possível STOP. Criamos um novo path atual, guardando o
				// anterior na lista.
				pathList.add(path);
				path = new Path();
				
				// Adicionando o ponto ao path atual
				path.add(trj.get(atual));
				atual++;
				
				while (regionCandidate.contains(trj.get(atual))) {
					// Equanto estiver na mesma regiao adiciona o ponto no path
					// atual
					path.add(trj.get(atual));
					atual++;
				}

				if (path.getDeltaTime() >= regionCandidate.getMinimunTime()) {
					// Neste ponto sabemos que todos os pontos do path sao um
					// STOP
					path.setType(PathType.STOP);
					
					// Acrescenta o path à lista de paths
					pathList.add(path);
					
					// Inicia um novo path
					path = new Path();
				}
			}
			// Aqui sabemos que o item atual não faz parte de um STOP pois
			// não intercepta nenhuma região de interesse.
			// Adicionamos ele ao path atual e incrementamos o índice
			path.add(trj.get(atual));
			atual++;
			
			// Este trecho é uma otimização para não ficar procurando região de
			// interesse para cada ponto da trajetória (custoso). Aqui o
			// algoritmo busca uma sequencia de pontos que não poderia ser um
			// STOP por ter seu selta T menor que o menor tempo mínimo de todas
			// as regiões.
			int j = 1;			
			while (atual+j < trj.size()) {
				double t1 = trj.get(atual).getTime();
				double t2 = trj.get(atual+j).getTime();
				if (t2 - t1 < app.getMinTime() ) {
					j++;
				}
				else {
					// A sequencia de pontos [atual, atual+j-1) com certeza não
					// constitui um STOP pois não tem a duração mínima para tal
					break;
				}
			}
			// Para ter certeza que nenhum ponto de [atual, atual+j-1) participa
			// de um STOP, analisa-se se o ultimo ponto (atual+j-1) intercepta
			// alguma região de interesse.
			regionCandidate = app.findIntersectingRegion(trj.get(atual+j-1));
			if (regionCandidate == null) {
				// Aqui pode-se afirmar que nenhum ponto de atual até atual+j-1
				// pertence a um STOP. Assim adiciona-se estes pontos ao path
				// atual e seta-se atual com indice do primeiro ponto após este
				// intervalo
				path.addAll(trj.subList(atual, atual+j-1));
				atual = atual + j;
			}
		}
		
		// Por fim adiciona-se o ultimo path à lista de paths
		pathList.add(path);
	}
}
