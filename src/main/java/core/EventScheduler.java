package core;

/**
 * @author Patrick Maia - patrickjem@lsd.ufcg.edu.br
 */
public final class EventScheduler {

	private static EventSourceMultiplexer eventSourceMultiplexer = null;
	private static long processCount = 0;

	private EventScheduler() { }
	
	public static void reset() {
		eventSourceMultiplexer = null;
		processCount = 0;
	}
	
	public static void setup(EventSourceMultiplexer eventSource) {
		reset();
		EventScheduler.eventSourceMultiplexer = eventSource;
	}
	
	private static boolean isConfigured() {
		return (eventSourceMultiplexer != null);
	}
	
	public static void start() {
		
		if(!isConfigured()) {
			throw new IllegalStateException("EventScheduler is not configured. " +
					"Are you sure you called EventScheduler.setup()?");
		}

		Event nextEvent;
		
		while ((nextEvent = eventSourceMultiplexer.getNextEvent()) != null) {
			processEvent(nextEvent);
			processCount++;
		}

	}

	private static void processEvent(Event nextEvent) {
		nextEvent.process();
	}

	/**
	 * 
	 * @return the number of {@link Event}s processed.
	 */
	public static long processCount() {
		return processCount;
	}

}