package simboinc.model;

import core.EventSource;

public abstract class Machine implements EventSource {
	private final String machineName;
	private final long limitOfTasks;
	
	protected State state = State.IDLE;
	
	protected Machine(String machineName, long limitOfTasks) {
		this.machineName = machineName;
		this.limitOfTasks = limitOfTasks;
	}
	
	public enum State {
		IDLE,
		ACTIVE,
		SLEEP
	}
	
	protected boolean thereIsMoreTasks() {
		return (limitOfTasks - currentTask()) > 0;
	}
	
	public String machineName() {
		return machineName;
	}
	
	public State generateMachineState() {
		// FIXME Should calculate the state
		return (state = State.IDLE);
	}
	
	public State currentMachineState() {
		return state;
	}
	
	abstract protected long currentTask();

}
