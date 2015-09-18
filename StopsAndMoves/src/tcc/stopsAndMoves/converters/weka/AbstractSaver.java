package tcc.stopsAndMoves.converters.weka;

import weka.core.Instances;
import weka.core.converters.Saver;

public abstract class AbstractSaver {

	public static final String DFLT_DATE_FRMT = "yyyy-MM-dd HH:mm:ss";

	protected Saver saver = null;
	protected String dateFormat = DFLT_DATE_FRMT;

	public AbstractSaver() {
		super();
	}

	public abstract Instances getStructure();
	public abstract void resetStructure();
	
	public Saver getSaver() {
		return saver;
	}

	public void setSaver(Saver saver) {
		this.saver = saver;
		saver.setRetrieval(Saver.INCREMENTAL);
		saver.setInstances(getStructure());
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		resetStructure();
	}

}