package simboinc.model;

import simboinc.ResultsLogger;
import simboinc.event.DownloadInputFileAndExecuteEvent;
import simboinc.event.DownloadInputFileEvent;
import simboinc.event.ExecuteWorkunitEvent;
import simboinc.event.SleepingEvent;
import simboinc.event.WaitEvent;
import core.Event;

public class MachinePlusEventSource extends Machine {
	private static long currentTask = 0;
	
	private int numberOfInputFiles = 0;
	private final ResultsLogger logger;
	
	public MachinePlusEventSource(String machineName, ResultsLogger logger, long limitOfTasks) {
		super(machineName, limitOfTasks);
		this.logger = logger;
	}
	
	private Event processWorkunit() {
		if(this.numberOfInputFiles <= 0) {
			return new DownloadInputFileAndExecuteEvent(this, logger, Long.toString(currentTask++));
		}

		return new ExecuteWorkunitEvent(this, logger, Long.toString(currentTask++));
	}
	
	private Event downloadInputFile() {
		if(this.numberOfInputFiles >= limitOfTasks()) {
			return new WaitEvent(this, logger);
		}

		this.numberOfInputFiles++;
		return new DownloadInputFileEvent(this, logger, Long.toString(currentTask));
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
