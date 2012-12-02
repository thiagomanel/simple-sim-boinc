package simboinc.event;

import simboinc.model.Machine;
import core.Event;
import core.Time;
import core.Time.Unit;

public class WaitEvent extends Event {
	private final static Time scheduledTime = new Time(10L, Unit.MINUTES);
	private final Machine machine;

	public WaitEvent(Machine machine) {
		super(scheduledTime);
		this.machine = machine;
	}

	@Override
	public void process() {
		System.out.println(String.format("[WAIT] machine=%s, state=%s, duration=%s", 
				this.machine.machineName(), this.machine.currentMachineState(), 
				super.getScheduledTime()));
	}
}

