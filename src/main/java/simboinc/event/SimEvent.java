package simboinc.event;

import core.Event;
import core.Time;
import simboinc.ResultsLogger;
import simboinc.model.BoincMachine;
import simboinc.model.WorkUnit;

public abstract class SimEvent extends Event {
	private final ResultsLogger logger;
	private final BoincMachine machine;
	private final WorkUnit workUnit;
	
	protected SimEvent(Time scheduledTime, ResultsLogger logger, BoincMachine machine, WorkUnit workUnit) {
		super(scheduledTime);
		this.logger = logger;
		this.machine = machine;
		this.workUnit = workUnit;
	}
	
	protected WorkUnit task() {
		return workUnit;
	}
	
	protected void log(String content) {
		logger.log(content);
	}
	
	protected ResultsLogger logger() {
		return logger;
	}
	
	protected BoincMachine machine() {
		return machine;
	}

}
