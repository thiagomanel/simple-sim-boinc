package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;
import core.Time;

public class WaitingEvent extends SimEvent {
	public WaitingEvent(MachineEventSource machine, Time scheduledTime, ResultsLogger logger, long task) {
		super(scheduledTime, logger, machine, task);
	}

	@Override
	public void process() {
		if(machine().isIdle()) {
			machine().addNextEvent(new StartFetchEvent(machine(), getScheduledTime(), logger(), newTask()));
		} else {
			machine().setIsWaiting(true);			
		}
	}
}
