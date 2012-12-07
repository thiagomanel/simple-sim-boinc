package simboinc.event.machine;

import java.util.Random;

import simboinc.ResultsLogger;
import simboinc.event.SimEvent;
import simboinc.model.MachineEventSource;
import simboinc.model.MachineEventSource.State;
import core.EventScheduler;
import core.Time;
import core.Time.Unit;

public class StateChangeEvent extends SimEvent {
	private final static Time COST = new Time(3600L, Unit.SECONDS);
	
	public StateChangeEvent(MachineEventSource machine, Time scheduledTime, ResultsLogger logger) {
		super(scheduledTime, logger, machine, 0L);
	}

	@Override
	public void process() {
		Random random = new Random();
		if(random.nextDouble() < 0.5) {
			log(String.format("[USER-IDLE] hostname=%s, time=%s", machine().hostname(), getScheduledTime()));
			machine().setState(State.IDLE);
		} else {
			log(String.format("[USER-ACTIVE] hostname=%s, time=%s", machine().hostname(), getScheduledTime()));
			machine().setState(State.ACTIVE);
		}
			
		EventScheduler.schedule(new StateChangeEvent(machine(), COST.plus(getScheduledTime()), logger()));
	}

}
