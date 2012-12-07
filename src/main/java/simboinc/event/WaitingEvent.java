package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.MachineEventSource;
import core.Time;
import core.Time.Unit;

public class WaitingEvent extends SimEvent {
	private final static Time COST = new Time(600L, Unit.SECONDS);
	
	public WaitingEvent(MachineEventSource machine, Time scheduledTime, ResultsLogger logger, long task) {
		super(scheduledTime, logger, machine, task);
	}

	@Override
	public void process() {
		if(machine().isIdle()) {
			machine().addNextEvent(new StartFetchEvent(machine(), getScheduledTime(), logger(), newTask()));
		} else {
			machine().addNextEvent(new WaitingEvent(machine(), COST.plus(getScheduledTime()), logger(), task()));
		}
	}

}
