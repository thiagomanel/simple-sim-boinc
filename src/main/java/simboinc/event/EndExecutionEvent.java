package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;
import core.Time;

public class EndExecutionEvent extends SimEvent {
	public EndExecutionEvent(MachineEventSource machine, Time scheduledTime, ResultsLogger logger, long task) {
		super(scheduledTime, logger, machine, task);
	}

	@Override
	public void process() {
		machine().addNextEvent(new Done(machine(), getScheduledTime(), logger(), task()));
		log(String.format("[END-EXECUTION] hostname=%s, task=%d, time=%s", machine().hostname(), task(), getScheduledTime()));
	}

}
