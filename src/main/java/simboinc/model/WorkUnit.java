package simboinc.model;

import core.Time;
import core.Time.Unit;

public class WorkUnit {
	public static final WorkUnit FAKE_WORKUNIT = new WorkUnit(new Time(0L, Unit.MICROSECONDS), 
												new Time(0L, Unit.MICROSECONDS), 0L);
	
	private final Time timeOfExecution;
	private final Time timeToDownload;
	private final long workUnitId;
	
	public WorkUnit(Time timeOfExecution, Time timeToDownload, long workUnitId) {
		this.timeOfExecution = timeOfExecution;
		this.timeToDownload = timeToDownload;
		this.workUnitId = workUnitId;
	}

	public Time timeOfExecution() {
		return timeOfExecution;
	}
	
	public Time timeToDownload() {
		return this.timeToDownload;
	}
	
	public long id() {
		return workUnitId;
	}
}
