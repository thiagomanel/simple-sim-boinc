package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.BoincMachine;
import simboinc.model.WorkUnit;
import core.Time;

public class FinishedWorkUnitEvent extends SimEvent {

	public FinishedWorkUnitEvent(BoincMachine machine, Time scheduledTime, ResultsLogger logger, WorkUnit workUnit) {
		super(scheduledTime, logger, machine, workUnit);
	}

	@Override
	public void process() {
		if(!machine().hasNextTask()) {
			machine().addNextEvent(null);
		} else if(machine().isIdle()) {
			machine().addNextEvent(new StartFetchEvent(machine(), getScheduledTime(), logger(), machine().nextTask()));
		} else {
			machine().addNextEvent(new WaitingEvent(machine(), getScheduledTime(), logger(), task()));
		}
	}
}
