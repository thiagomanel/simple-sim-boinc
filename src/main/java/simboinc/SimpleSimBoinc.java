package simboinc;

import core.Context;
import core.EventScheduler;
import core.Initializer;
import core.Summarizer;
import core.Time;
import core.Time.Unit;

public class SimpleSimBoinc {
	public static void main(String[] args) {
		Initializer initializer = new BoincInitializer();
		Summarizer summarizer = new BoincSummarizer();
		
		Context context = initializer.initialize(null);
		// TODO Patrick's code has constants for this.
		Time emulationStart = new Time(0L, Unit.SECONDS);
		Time emulationEnd = new Time(800000L, Unit.SECONDS);
		
		EventScheduler.setup(emulationStart, emulationEnd, context.getEventSourceMultiplexer());
		try {
			EventScheduler.start();
		} catch (Throwable t) {
			throw new RuntimeException("Simulation failed.", t);
		}

		System.out.println(summarizer.summarize(context));
	}

}
