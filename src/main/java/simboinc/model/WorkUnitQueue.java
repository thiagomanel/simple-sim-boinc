package simboinc.model;

import core.Time;
import core.Time.Unit;

public class WorkUnitQueue {
	private final long numberOfWorkUnit;
	
	private long currentWorkUnit = 1;
	private long currentDownloadUnit = 1;
	
	public WorkUnitQueue(long numberOfWorkUnit) {
		this.numberOfWorkUnit = numberOfWorkUnit;
	}
	
	public boolean hasNextDownload() {
		return currentDownloadUnit <= numberOfWorkUnit;
	}
	
	public boolean hasNext() {
		return currentWorkUnit <= numberOfWorkUnit;
	}
	
	public WorkUnit nextWorkUnit() {
		currentWorkUnit++;
		return new WorkUnit(new Time(1200, Unit.SECONDS), new Time(1800, Unit.SECONDS), currentWorkUnit);
	}

}
