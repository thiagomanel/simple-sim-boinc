package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.BoincMachine;
import simboinc.model.WorkUnit;
import core.Time;

public class StartFetchEvent extends SimEvent {

	public StartFetchEvent(BoincMachine machine, Time scheduledTime, ResultsLogger logger, WorkUnit workUnit) {
		super(scheduledTime, logger, machine, workUnit);
	}

	@Override
	public void process() {
		log(String.format("type=start-fetch, state=%s, hostname=%s, task=%d, time=%s", 
				machine().state(), machine().hostname(), task().id(), getScheduledTime()));
		machine().addNextEvent(new EndFetchEvent(machine(), scheduleTimeToEnd(), logger(), task()));
	}

	private Time scheduleTimeToEnd() {
		return task().timeToDownload().plus(getScheduledTime());
	}
}
