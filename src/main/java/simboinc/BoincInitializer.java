package simboinc;

import java.util.Properties;

import simboinc.model.MachineEventSource;
import simboinc.model.MachinePlusEventSource;

import core.Context;
import core.EventSource;
import core.EventSourceMultiplexer;
import core.Initializer;

public class BoincInitializer implements Initializer {
	private final ResultsLogger loggerNormalMachine;
	private final ResultsLogger loggerModifiedMachine;
	
	public BoincInitializer() {
		loggerNormalMachine = new ResultsLogger("/tmp/normal-machine-results.log", false, false);
		loggerModifiedMachine = new ResultsLogger("/tmp/modified-machine-results.log", false, false);
	}

	@Override
	public Context initialize(Properties config) {
		MachineEventSource machineEventSource = new MachineEventSource("teste1", loggerNormalMachine, 20);
		MachinePlusEventSource machinePlusEventSource = new MachinePlusEventSource("teste2", loggerModifiedMachine, 20);
		
		EventSource[] eventSources = new EventSource[]{machineEventSource, machinePlusEventSource};
		EventSourceMultiplexer eventSourceMultiplexer = new EventSourceMultiplexer(eventSources);
		
		Context context = new Context(eventSourceMultiplexer);
		context.add("machine1", machineEventSource);
		context.add("machine2", machinePlusEventSource);
		
		return context;
	}

}
