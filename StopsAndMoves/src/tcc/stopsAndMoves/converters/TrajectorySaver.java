package tcc.stopsAndMoves.converters;

import java.io.IOException;

import tcc.stopsAndMoves.Move;
import tcc.stopsAndMoves.Stop;
import tcc.stopsAndMoves.Trajectory;
import weka.core.converters.Saver;

/**
 * Saver para gravar todos os moves e stops de trajetórias utilizando os Saver's
 * especificados.
 * 
 * @author giovani
 * 
 */
public class TrajectorySaver {
	private MoveSaver moveSaver = null;
	private StopSaver stopSaver = null;

	public void writeIncremental(Trajectory trj) throws IOException {
		if (moveSaver != null) {
			if (trj == null) {
				moveSaver.writeIncremental(null);
			} else {
				for (Move m : trj.getMoves()) {
					moveSaver.writeIncremental(m);
				}
			}
		}

		if (stopSaver != null) {
			if (trj == null) {
				stopSaver.writeIncremental(null);
			} else {
				for (Stop s : trj.getStops()) {
					stopSaver.writeIncremental(s);
				}
			}
		}
	}

	public MoveSaver getMoveSaver() {
		return moveSaver;
	}

	public void setMoveSaver(MoveSaver moveSaver) {
		this.moveSaver = moveSaver;
	}
	
	public void setMoveSaver(Saver saver) {
		if (moveSaver == null) {
			moveSaver = new MoveSaver();
		}
		
		moveSaver.setSaver(saver);
	}
	
	public void setMovePointsSaver(Saver saver) {
		if (moveSaver == null) {
			moveSaver = new MoveSaver();
		}
		
		moveSaver.setPointsSaver(saver);
	}

	public StopSaver getStopSaver() {
		return stopSaver;
	}

	public void setStopSaver(StopSaver stopSaver) {
		this.stopSaver = stopSaver;
	}
	
	public void setStopSaver(Saver saver) {
		if (stopSaver == null) {
			stopSaver = new StopSaver();
		}
		
		stopSaver.setSaver(saver);
	}
}
