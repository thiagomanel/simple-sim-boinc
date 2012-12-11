package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.BoincMachine;
import simboinc.model.WorkUnit;
import core.Time;

public class StartExecutionEvent extends SimEvent {
	
	public StartExecutionEvent(BoincMachine machine, Time scheduledTime, ResultsLogger logger, WorkUnit workUnit) {
		super(scheduledTime, logger, machine, workUnit);
	}

	@Override
	public void process() {
		log(String.format("type=start-execution, state=%s, hostname=%s, task=%d, time=%s", 
				machine().state(), machine().hostname(), task().id(), getScheduledTime()));
		machine().addNextEvent(new EndExecutionEvent(machine(), scheduleTimeToEnd(), logger(), task()));
	}

	private Time scheduleTimeToEnd() {
		return task().timeOfExecution().plus(getScheduledTime());
	}

}
