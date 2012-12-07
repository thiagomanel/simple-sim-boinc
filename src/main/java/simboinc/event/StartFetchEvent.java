package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;
import core.Time;
import core.Time.Unit;

public class StartFetchEvent extends SimEvent {
	private final static Time COST = new Time(1200L, Unit.SECONDS);
	
	public StartFetchEvent(MachineEventSource machine, Time scheduledTime, ResultsLogger logger, long task) {
		super(scheduledTime, logger, machine, task);
	}

	@Override
	public void process() {
		machine().addNextEvent(new EndFetchEvent(machine(), COST.plus(getScheduledTime()), logger(), task()));
		log(String.format("[START-FETCH] hostname=%s, task=%d, time=%s", machine().hostname(), task(), getScheduledTime()));
	}

}
