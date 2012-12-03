package simboinc.model;

import simboinc.ResultsLogger;
import simboinc.event.DownloadInputFileAndExecuteEvent;
import simboinc.event.SleepingEvent;
import simboinc.event.WaitEvent;
import core.Event;

public class MachineEventSource extends Machine {
	private final ResultsLogger logger;
	
	protected static long currentTask = 0;
	
	public MachineEventSource(String machineName, ResultsLogger logger, long limitOfTasks) {
		super(machineName, limitOfTasks);
		this.logger = logger;
	}
	
	private Event processWorkunit() {
		return new DownloadInputFileAndExecuteEvent(this, logger, Long.toString(currentTask++));
	}
	
	private Event waitIdleness() {
		return new WaitEvent(this, logger);
	}
	
	private Event sleep() {
		return new SleepingEvent(this, logger);
	}
	
	@Override
	public Event getNextEvent() {
		if(!thereIsMoreTasks()) {
			logger.close();
			return null;
		}

		generateMachineState();

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
