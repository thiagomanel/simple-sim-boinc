package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;
import core.Time;
import core.Time.Unit;

public class StartExecutionEvent extends SimEvent {
	private final static Time COST = new Time(600L, Unit.SECONDS);
	
	public StartExecutionEvent(MachineEventSource machine, Time scheduledTime, ResultsLogger logger, long task) {
		super(scheduledTime, logger, machine, task);
	}

	@Override
	public void process() {
		machine().addNextEvent(new EndExecutionEvent(machine(), COST.plus(getScheduledTime()), logger(), task()));
		log(String.format("[START-EXECUTION] hostname=%s, task=%d, time=%s", machine().hostname(), task(), getScheduledTime()));
	}

}
