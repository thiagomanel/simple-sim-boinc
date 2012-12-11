package simboinc.event.machine;

import java.util.Random;

import simboinc.ResultsLogger;
import simboinc.event.SimEvent;
import simboinc.event.StartFetchEvent;
import simboinc.model.BoincMachine;
import simboinc.model.BoincMachine.State;
import simboinc.model.WorkUnit;
import core.EventScheduler;
import core.Time;
import core.Time.Unit;

public class StateChangeEvent extends SimEvent {
	private final static Time TIME_OF_STATE_CHANGE_EVENT = new Time(3600L, Unit.SECONDS);
	
	public StateChangeEvent(BoincMachine machine, Time scheduledTime, ResultsLogger logger) {
		super(scheduledTime, logger, machine, WorkUnit.FAKE_WORKUNIT);
	}

	@Override
	public void process() {
		if(machine().hasNextTask()) {
			executeStateChoice();
			EventScheduler.schedule(new StateChangeEvent(machine(), nextStateChangeEventTime(), logger()));
		}
	}
	
	private void executeStateChoice() {
		Random random = new Random();
		if(random.nextDouble() < 0.5) {
			toUserIdle(machine());
		} else {
			toUserActivity(machine());
		}
	}
	
	private void toUserActivity(BoincMachine machine) {
		if(machine.isIdle()) {
			machine.setState(State.ACTIVE);
			log(String.format("type=change-state, state=%s, hostname=%s, time=%s", machine.state(), machine.hostname(), getScheduledTime()));
		}
	}
	
	private void toUserIdle(BoincMachine machine) {
		if(!machine.isIdle()) {
			machine.setState(State.IDLE);
			log(String.format("type=change-state, state=%s, hostname=%s, time=%s", machine.state(), machine.hostname(), getScheduledTime()));
		}
		if(machine.isWaiting()) {
			machine.setIsWaiting(false);
			machine.addNextEvent(new StartFetchEvent(machine, getScheduledTime(), logger(), machine.nextTask()));
		}
	}
	
	private Time nextStateChangeEventTime() {
		return TIME_OF_STATE_CHANGE_EVENT.plus(getScheduledTime());
	}

}
