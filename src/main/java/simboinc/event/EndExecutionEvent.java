package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;
import core.Time;
import core.Time.Unit;

public class EndExecutionEvent extends SimEvent {
	private final static Time COST = new Time(1L, Unit.SECONDS);
	
	public EndExecutionEvent(MachineEventSource machine, Time scheduledTime, ResultsLogger logger, long task) {
		super(scheduledTime, logger, machine, task);
	}

	@Override
	public void process() {
		machine().addNextEvent(new Done(machine(), COST.plus(getScheduledTime()), logger(), task()));
		log(String.format("[END-EXECUTION] hostname=%s, task=%d, time=%s", machine().hostname(), task(), getScheduledTime()));
	}

}
