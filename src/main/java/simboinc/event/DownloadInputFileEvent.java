package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.Machine;
import core.Time;
import core.Time.Unit;

public class DownloadInputFileEvent extends SimEvent {
	private final static Time scheduledTime = new Time(15L, Unit.MINUTES);
	private final Machine machine;
	private final String taskName;

	public DownloadInputFileEvent(Machine machine, ResultsLogger logger, String taskName) {
		super(scheduledTime, logger);
		this.machine = machine;
		this.taskName = taskName; 
	}

	@Override
	public void process() {
		machine.plusDownloadTime(scheduledTime);
		log(String.format("[DOWNLOAD] machine=%s, state=%s, task=%s, duration=%s", 
				this.machine.machineName(), this.machine.currentMachineState(), this.taskName, 
				super.getScheduledTime()));
	}
}

