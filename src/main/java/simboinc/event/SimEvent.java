package simboinc.event;

import core.Event;
import core.Time;
import simboinc.ResultsLogger;

public abstract class SimEvent extends Event {
	private final ResultsLogger logger;
	
	protected SimEvent(Time scheduledTime, ResultsLogger logger) {
		super(scheduledTime);
		this.logger = logger;
	}
	
	protected void log(String content) {
		logger.log(content);
	}

}
