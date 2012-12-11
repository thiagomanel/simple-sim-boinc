package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.BoincMachine;
import simboinc.model.WorkUnit;
import core.Time;

public class EndFetchEvent extends SimEvent {
	
	public EndFetchEvent(BoincMachine machine, Time scheduledTime, ResultsLogger logger, WorkUnit workUnit) {
		super(scheduledTime, logger, machine, workUnit);
	}

	@Override
	public void process() {
		log(String.format("type=end-fetch, state=%s, hostname=%s, task=%d, time=%s", 
				machine().state(), machine().hostname(), task().id(), getScheduledTime()));
		machine().addNextEvent(new StartExecutionEvent(machine(), getScheduledTime(), logger(), task()));
	}

}
