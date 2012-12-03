package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.Machine;
import core.Time;
import core.Time.Unit;

public class WaitEvent extends SimEvent {
	private final static Time scheduledTime = new Time(5L, Unit.MINUTES);
	private final Machine machine;

	public WaitEvent(Machine machine, ResultsLogger logger) {
		super(scheduledTime, logger);
		this.machine = machine;
	}

	@Override
	public void process() {
		machine.addStateTime(scheduledTime);
		log(String.format("[WAIT] machine=%s, state=%s, duration=%s", 
				this.machine.machineName(), this.machine.currentMachineState(), 
				super.getScheduledTime()));
	}
}

