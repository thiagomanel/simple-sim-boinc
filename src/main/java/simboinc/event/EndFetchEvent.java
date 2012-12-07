package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;
import core.Time;

public class EndFetchEvent extends SimEvent {
	
	public EndFetchEvent(MachineEventSource machine, Time scheduledTime, ResultsLogger logger, long task) {
		super(scheduledTime, logger, machine, task);
	}

	@Override
	public void process() {
		machine().addNextEvent(new StartExecutionEvent(machine(), getScheduledTime(), logger(), task()));
		log(String.format("[END-FETCH] hostname=%s, task=%d, time=%s", machine().hostname(), task(), getScheduledTime()));
	}

}
