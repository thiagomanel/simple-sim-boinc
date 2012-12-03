package simboinc;

import core.Context;
import core.EventScheduler;
import core.Initializer;
import core.Summarizer;

public class SimpleSimBoinc {
	public static void main(String[] args) {
		Initializer initializer = new BoincInitializer();
		Summarizer summarizer = new BoincSummarizer();
		
		Context context = initializer.initialize(null);
		
		EventScheduler.setup(context.getEventSourceMultiplexer());
		try {
			EventScheduler.start();
		} catch (Throwable t) {
			throw new RuntimeException("Simulation failed.", t);
		}

		System.out.println(summarizer.summarize(context));
	}

}
