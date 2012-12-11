package simboinc.model;

import simboinc.ResultsLogger;
import simboinc.event.EndFetchEvent;
import simboinc.event.SimEvent;
import simboinc.event.StartExecutionEvent;
import simboinc.event.StartFetchEvent;
import simboinc.event.machine.StateChangeEvent;
import core.Event;
import core.EventSource;
import core.Time;
import core.Time.Unit;

public class BoincMachine implements EventSource {
	private final String hostname;
	private final WorkUnitQueue workUnitQueue;
	
	private State state;
	private boolean isWating;
	private SimEvent nextEvents;
	
	public enum State {
		IDLE,
		ACTIVE
	}
	
	public BoincMachine(State state, String hostname, ResultsLogger logger, WorkUnitQueue workUnitQueue) {
		this.state = state;
		this.hostname = hostname;
		this.workUnitQueue = workUnitQueue;
		this.isWating = true;
		
		nextEvents = new StateChangeEvent(this, new Time(0L, Unit.MICROSECONDS), logger);
	}
	
	public boolean hasNextTask() {
		return workUnitQueue.hasNext();
	}
	
	public WorkUnit nextTask() {
		return workUnitQueue.nextWorkUnit();
	}
	
	public void setIsWaiting(boolean isWating) {
		this.isWating = isWating;
	}
	
	public boolean isWaiting() {
		return isWating;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public void addNextEvent(SimEvent nextEvent) {
		this.nextEvents = nextEvent;
	}
	
	public String hostname() {
		return hostname;
	}
	
	public boolean isIdle() {
		return State.IDLE == state;	
	}
	
	public String state() {
		switch(state) {
		case IDLE:
			return "user-idle";
		case ACTIVE:
			return "user-activity";
		default:
			return "invalid-state";
		}
	}

	@Override
	public Event getNextEvent() {
		
		
		SimEvent event = nextEvents;
		nextEvents = null;
		if(!hasNextTask()) {
			return null;
		}

		return event;
	}
}
