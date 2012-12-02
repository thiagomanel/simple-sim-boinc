package simboinc.model;

import simboinc.event.DownloadInputFileEvent;
import simboinc.event.ExecuteWorkunitEvent;
import simboinc.event.SleepingEvent;
import core.Event;

public class MachinePlusEventSource extends Machine {
	protected static long currentTask = 0;
	private int numberOfInputFiles = 0;
	
	public MachinePlusEventSource(String machineName, long limitOfTasks) {
		super(machineName, limitOfTasks);
	}
	
	private Event processWorkunit() {
		if(this.numberOfInputFiles <= 0) {
			downloadInputFile().process();
		}
		this.numberOfInputFiles--;
		return new ExecuteWorkunitEvent(this, Long.toString(currentTask++));
	}
	
	private Event downloadInputFile() {
		this.numberOfInputFiles++;
		return new DownloadInputFileEvent(this, Long.toString(currentTask));
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
				return downloadInputFile();
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
