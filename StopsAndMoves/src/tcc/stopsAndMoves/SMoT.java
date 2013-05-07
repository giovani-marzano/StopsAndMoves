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
	private List<Stop> stopList;
	private List<Move> moveList;

	private IdGenerator stopIdGen;
	private IdGenerator moveIdGen;

	private Stop createStop(Trajectory t, double enterTime, double leaveTime) {
		long id = stopIdGen.generateId(t.getId());
		Stop s = new Stop(t, id);
		s.setEnterTime(enterTime);
		s.setLeaveTime(leaveTime);
		return s;
	}

	private Move createMove(Trajectory t) {
		long id = moveIdGen.generateId(t.getId());
		return new Move(t, id);
	}

	private void addStop(Stop s) {
		if (stopList == null) {
			stopList = new ArrayList<>();
		}

		stopList.add(s);
	}

	private void addMove(Move m) {
		if (moveList == null) {
			moveList = new ArrayList<>();
		}

		moveList.add(m);
	}

	// TODO: Associar pontos ao respectivo Move
	// TODO: Associar Stops de origem e destino para os moves
	public void processTrajectory(Trajectory trj,
			Application app) {
		List<SamplePoint> pointList = trj.getPointList();
		Stop stop = null;
		Move move = null;
		Stop previousStop = null;

		// Indice do ponto que entrou na regiao
		int enterInd = 0;
		double enterTime = 0;
		// Indice do ponto em que a trajetoria saiu da regiao (último
		// ponto na regiao de interesse)
		int leaveInd = 0;
		double leaveTime = 0;
		SpatialFeature regionCandidate = null;

		int i = 0;
		while (i < pointList.size()) {
			regionCandidate = app.findIntersectingRegion(pointList.get(i));
			if (regionCandidate != null) {
				enterInd = i;
				i++;

				while (regionCandidate.contains(pointList.get(i))) {
					i++;
				}
				leaveInd = i - 1;

				leaveTime = pointList.get(leaveInd).getInstant();
				enterTime = pointList.get(enterInd).getInstant();
				if (leaveTime - enterTime >= regionCandidate.getMinimunTime()) {
					stop = createStop(trj, enterTime, leaveTime);
					addStop(stop);

					move = createMove(trj);
					addMove(move);

					previousStop = stop;
				}
			}
			i++;
			int j = 1;
			
			while (j+1 < pointList.size()) {
				double t1 = pointList.get(i).getInstant();
				double t2 = pointList.get(i+j).getInstant();
				if (t2 - t1 < app.getMinTime() ) {
					j++;
				}
				else {
					break;
				}
			}
			
			regionCandidate = app.findIntersectingRegion(pointList.get(i+j-1));
			if (regionCandidate == null) {
				i = i + j;
			}
		}
		/* TODO:
		if ( T [i -1] not ∈ previousStop )
		// T do not end with a stop
		move =( T , previousStop , null ,
		previousStop . leaveTime , time ( T [i -1]))
		M . add ( move );
		endif
		*/
	}
}
