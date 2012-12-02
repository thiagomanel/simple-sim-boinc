package simboinc.event;

import simboinc.model.Machine;
import core.Event;
import core.Time;
import core.Time.Unit;

public class SleepingEvent extends Event {
	private final static Time scheduledTime = new Time(6L, Unit.HOURS);
	private final Machine machine;

	public SleepingEvent(Machine machine) {
		super(scheduledTime);
		this.machine = machine;
	}

	@Override
	public void process() {
		System.out.println(String.format("[SLEEP] machine=%s, state=%s, duration=%s", 
				this.machine.machineName(), this.machine.currentMachineState(), 
				super.getScheduledTime()));
	}
}

