package simboinc.model;

import simboinc.event.DownloadInputFileAndExecuteEvent;
import simboinc.event.SleepingEvent;
import simboinc.event.WaitEvent;
import core.Event;

public class MachineEventSource extends Machine {
	protected static long currentTask = 0;
	
	public MachineEventSource(String machineName, long limitOfTasks) {
		super(machineName, limitOfTasks);
	}
	
	private Event processWorkunit() {
		return new DownloadInputFileAndExecuteEvent(this, Long.toString(currentTask++));
	}
	
	private Event waitIdleness() {
		return new WaitEvent(this);
	}
	
	private Event sleep() {
		return new SleepingEvent(this);
	}
	
	@Override
	public Event getNextEvent() {
		if(!thereIsMoreTasks()) {
			return null;
		}

		switch(currentMachineState()) {
			case IDLE:
				return processWorkunit();
			case ACTIVE:
				return waitIdleness();
			case SLEEP:
				return sleep();
			default:
				throw new IllegalStateException("Invalid state");
		}
	}

	@Override
	protected long currentTask() {
		return currentTask;
	}
}
