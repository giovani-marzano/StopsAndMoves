package tcc.stopsAndMoves;

import java.util.HashMap;
import java.util.Map;

public class IdGenerator {
	private Map<Long, Long> idMap;
	
	public IdGenerator() {
		this(new HashMap<Long, Long>());
	}

	public IdGenerator(Map<Long, Long> idMap) {
		super();
		this.idMap = idMap;
	}
	
	public long generateId(long masterId) {
		long id;
		
		if ( idMap.containsKey(masterId) ) {
			id = idMap.get(masterId);
			id++;
		}
		else {
			id = 1;
		}
		
		idMap.put(masterId, id);
		
		return id;
	}

	public Map<Long, Long> getIdMap() {
		return idMap;
	}

	public void setIdMap(Map<Long, Long> idMap) {
		this.idMap = idMap;
	}
}
