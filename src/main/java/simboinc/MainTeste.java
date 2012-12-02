package simboinc;

import core.Context;
import core.EventScheduler;
import core.Initializer;
import core.Summarizer;
import core.Time;
import core.Time.Unit;

public class MainTeste {
	public static void main(String[] args) {
		Initializer initializer = new BoincInitializer();
		Summarizer summarizer = new BoincSummarizer();
		
		Context context = initializer.initialize(null);
		
		Time simulationStart = new Time(0L, Unit.SECONDS);
		Time simulationEnd = new Time(60L, Unit.HOURS);
		
		EventScheduler.setup(simulationStart, simulationEnd, context.getEventSourceMultiplexer());
		try {
			System.out.println("========= 1");
			EventScheduler.start();
			System.out.println("========= 2");
		} catch (Throwable t) {
			throw new RuntimeException("Simulation failed at time: " + EventScheduler.now(), t);
		}
		System.out.println("----------- 1");
		System.out.println(summarizer.summarize(context));
		System.out.println("----------- 2");
	}

}
