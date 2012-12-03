package simboinc.event;

import simboinc.ResultsLogger;
import simboinc.model.Machine;
import core.Time;
import core.Time.Unit;

public class DownloadInputFileAndExecuteEvent extends SimEvent {
	private final static Time scheduledTime = new Time(25L, Unit.MINUTES);
	private final Machine machine;
	private final String taskName;

	public DownloadInputFileAndExecuteEvent(Machine machine, ResultsLogger logger, String taskName) {
		super(scheduledTime, logger);
		this.machine = machine;
		this.taskName = taskName;
	}

	@Override
	public void process() {
		machine.plusDownloadAndProcessingTime(scheduledTime);
		log(String.format("[DOWNLOAD-EXECUTE] machine=%s, state=%s, task=%s, duration=%s", 
				this.machine.machineName(), this.machine.currentMachineState(), this.taskName, 
				super.getScheduledTime()));
	}
}

