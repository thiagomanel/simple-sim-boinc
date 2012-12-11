package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.BoincMachine;
import simboinc.model.WorkUnit;
import core.Time;

public class EndExecutionEvent extends SimEvent {
	public EndExecutionEvent(BoincMachine machine, Time scheduledTime, ResultsLogger logger, WorkUnit workUnit) {
		super(scheduledTime, logger, machine, workUnit);
	}

	@Override
	public void process() {
		log(String.format("type=end-execution, state=%s, hostname=%s, task=%d, time=%s", 
				machine().state(), machine().hostname(), task().id(), getScheduledTime()));
		machine().addNextEvent(new FinishedWorkUnitEvent(machine(), getScheduledTime(), logger(), task()));
	}

}
