package simboinc.event;

import simboinc.model.Machine;
import core.Event;
import core.Time;
import core.Time.Unit;

public class ExecuteWorkunitEvent extends Event {
	private final static Time scheduledTime = new Time(10L, Unit.MINUTES);
	private final Machine machine;
	private final String taskName;

	public ExecuteWorkunitEvent(Machine machine, String taskName) {
		super(scheduledTime);
		this.machine = machine;
		this.taskName = taskName;
	}

	@Override
	public void process() {
		System.out.println(String.format("[EXECUTE] machine=%s, state=%s, task=%s, duration=%s", 
				this.machine.machineName(), this.machine.currentMachineState(), this.taskName, 
				super.getScheduledTime()));
	}
}

