package simboinc;

import java.util.Properties;

import simboinc.model.BoincMachine;
import simboinc.model.BoincMachine.State;
import simboinc.model.WorkUnitQueue;

import core.Context;
import core.EventSource;
import core.EventSourceMultiplexer;
import core.Initializer;

public class BoincInitializer implements Initializer {
	private final ResultsLogger loggerNormalMachine;
	private final WorkUnitQueue queue;
	
	public BoincInitializer() {
		loggerNormalMachine = new ResultsLogger("/tmp/normal-machine-results.log", true, false);
		queue = new WorkUnitQueue(18L);
	}

	@Override
	public Context initialize(Properties config) {
		BoincMachine machineEventSource = new BoincMachine(State.IDLE, "teste1", loggerNormalMachine, queue);
		
		EventSource[] eventSources = new EventSource[]{machineEventSource};
		EventSourceMultiplexer eventSourceMultiplexer = new EventSourceMultiplexer(eventSources);
		
		Context context = new Context(eventSourceMultiplexer);
		context.add("machine1", machineEventSource);
		
		return context;
	}

}
