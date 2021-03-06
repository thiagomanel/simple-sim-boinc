package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.BoincMachine;
import simboinc.model.WorkUnit;
import core.Time;

public class WaitingEvent extends SimEvent {
	public WaitingEvent(BoincMachine machine, Time scheduledTime, ResultsLogger logger, WorkUnit workUnit) {
		super(scheduledTime, logger, machine, workUnit);
	}

	@Override
	public void process() {
		if(machine().isIdle()) {
			machine().addNextEvent(new StartFetchEvent(machine(), getScheduledTime(), logger(), machine().nextTask()));
		} else {
			machine().setIsWaiting(true);
		}
	}
}
