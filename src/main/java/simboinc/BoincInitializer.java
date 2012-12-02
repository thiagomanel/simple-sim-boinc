package simboinc;

import java.util.Properties;

import simboinc.model.MachineEventSource;
import simboinc.model.MachinePlusEventSource;

import core.Context;
import core.EventSource;
import core.EventSourceMultiplexer;
import core.Initializer;

public class BoincInitializer implements Initializer {

	@Override
	public Context initialize(Properties config) {
		EventSource[] eventSources = new EventSource[]{new MachineEventSource("teste1", 20), new MachinePlusEventSource("teste2", 20)};
		EventSourceMultiplexer eventSourceMultiplexer = new EventSourceMultiplexer(eventSources);
		
		Context context = new Context(eventSourceMultiplexer);
		
		return context;
	}

}
