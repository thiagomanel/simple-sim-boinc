package simboinc.event;

import simboinc.model.Machine;
import core.Event;
import core.Time;
import core.Time.Unit;

public class DownloadInputFileEvent extends Event {
	private final static Time scheduledTime = new Time(15L, Unit.MINUTES);
	private final Machine machine;
	private final String taskName;

	public DownloadInputFileEvent(Machine machine, String taskName) {
		super(scheduledTime);
		this.machine = machine;
		this.taskName = taskName; 
	}

	@Override
	public void process() {
		System.out.println(String.format("[DOWNLOAD] machine=%s, state=%s, task=%s, duration=%s", 
				this.machine.machineName(), this.machine.currentMachineState(), this.taskName, 
				super.getScheduledTime()));
	}
}

