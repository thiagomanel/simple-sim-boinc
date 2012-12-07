package simboinc.event;

import core.Event;
import core.Time;
import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;

public abstract class SimEvent extends Event {
	private final ResultsLogger logger;
	private final MachineEventSource machine;
	private long task;
	
	protected SimEvent(Time scheduledTime, ResultsLogger logger, MachineEventSource machine, long task) {
		super(scheduledTime);
		this.logger = logger;
		this.machine = machine;
		this.task = task;
	}
	
	protected long task() {
		return task;
	}
	
	protected long newTask() {
		return ++task;
	}
	
	protected void log(String content) {
		logger.log(content);
	}
	
	protected ResultsLogger logger() {
		return logger;
	}
	
	protected MachineEventSource machine() {
		return machine;
	}

}
