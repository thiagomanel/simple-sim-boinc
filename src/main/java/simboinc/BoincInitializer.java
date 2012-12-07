package simboinc;

import java.util.Properties;

import simboinc.model.MachineEventSource;
import simboinc.model.MachineEventSource.State;

import core.Context;
import core.EventSource;
import core.EventSourceMultiplexer;
import core.Initializer;

public class BoincInitializer implements Initializer {
	private final ResultsLogger loggerNormalMachine;
	
	public BoincInitializer() {
		loggerNormalMachine = new ResultsLogger("/tmp/normal-machine-results.log", true, false);
	}

	@Override
	public Context initialize(Properties config) {
		MachineEventSource machineEventSource = new MachineEventSource(State.IDLE, "teste1", loggerNormalMachine);
		
		EventSource[] eventSources = new EventSource[]{machineEventSource};
		EventSourceMultiplexer eventSourceMultiplexer = new EventSourceMultiplexer(eventSources);
		
		Context context = new Context(eventSourceMultiplexer);
		context.add("machine1", machineEventSource);
		
		return context;
	}

}
