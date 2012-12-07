package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;
import core.Time;

public class Done extends SimEvent {
	
	public Done(MachineEventSource machine, Time scheduledTime, ResultsLogger logger, long task) {
		super(scheduledTime, logger, machine, task);
	}

	@Override
	public void process() {
		log(String.format("[DONE] hostname=%s, task=%d, time=%s", machine().hostname(), task(), getScheduledTime()));
		if(machine().isIdle()) {
			machine().addNextEvent(new StartFetchEvent(machine(), getScheduledTime(), logger(), newTask()));
		} else {
			machine().addNextEvent(new WaitingEvent(machine(), getScheduledTime(), logger(), task()));
		}
	}

}
