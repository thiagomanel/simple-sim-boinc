package simboinc.model;

import java.util.PriorityQueue;

import simboinc.ResultsLogger;
import simboinc.event.SimEvent;
import simboinc.event.WaitingEvent;
import simboinc.event.machine.StateChangeEvent;
import core.Event;
import core.EventSource;
import core.Time;
import core.Time.Unit;

public class MachineEventSource implements EventSource {
	private final String hostname;
	private final long numberOfTask;
	
	private State state;
	private boolean isWating;
	private long taskId = 0;
	// TODO Will be not needed to be a PriorityQueue, can be a SimEvent variable.
	private PriorityQueue<SimEvent> nextEvents;
	
	public enum State {
		IDLE,
		ACTIVE
	}
	
	public MachineEventSource(State state, String hostname, ResultsLogger logger, long numberOfTask) {
		this.state = state;
		this.hostname = hostname;
		this.numberOfTask = numberOfTask;
		
		nextEvents = new PriorityQueue<SimEvent>();
		nextEvents.add(new StateChangeEvent(this, new Time(0L, Unit.MICROSECONDS), logger));
		nextEvents.add(new WaitingEvent(this, new Time(1L, Unit.MICROSECONDS), logger, 0L));
	}
	
	public boolean thereIsTasks() {
		return taskId <= numberOfTask;
	}
	
	public long nextTask() {
		return ++taskId;
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
		nextEvents.add(nextEvent);
	}
	
	public String hostname() {
		return hostname;
	}
	
	public boolean isIdle() {
		return State.IDLE == state;	
	}
	
	@Override
	public Event getNextEvent() {
		if(!thereIsTasks()) {
			return null;
		}
		return nextEvents.poll();
	}
}
