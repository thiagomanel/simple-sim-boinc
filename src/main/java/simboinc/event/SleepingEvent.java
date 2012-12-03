package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.Machine;
import core.Time;
import core.Time.Unit;

public class SleepingEvent extends SimEvent {
	private final static Time scheduledTime = new Time(6L, Unit.HOURS);
	private final Machine machine;

	public SleepingEvent(Machine machine, ResultsLogger logger) {
		super(scheduledTime, logger);
		this.machine = machine;
	}

	@Override
	public void process() {
		machine.addStateTime(scheduledTime);
		log(String.format("[SLEEP] machine=%s, state=%s, duration=%s", 
				this.machine.machineName(), this.machine.currentMachineState(), 
				super.getScheduledTime()));
	}
}

