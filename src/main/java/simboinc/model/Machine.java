package simboinc.model;

import java.util.Random;

import core.EventSource;
import core.Time;
import core.Time.Unit;

public abstract class Machine implements EventSource {
	private Time totalSimulatedTime = new Time(0, Unit.MICROSECONDS);
	
	private Time sleepingTime = new Time(0, Unit.MICROSECONDS);
	private Time idleTime = new Time(0, Unit.MICROSECONDS);
	private Time activeTime = new Time(0, Unit.MICROSECONDS);
	
	private Time totalProcessingTime = new Time(0, Unit.MICROSECONDS);
	private Time processingTime = new Time(0, Unit.MICROSECONDS);
	private Time downloadTime = new Time(0, Unit.MICROSECONDS);
	private Time downloadAndProcessingTime = new Time(0, Unit.MICROSECONDS);
	
	private final String machineName;
	private final long limitOfTasks;
	
	protected State state = State.IDLE;
	
	protected Machine(String machineName, long limitOfTasks) {
		this.machineName = machineName;
		this.limitOfTasks = limitOfTasks;
	}
	
	public enum State {
		IDLE,
		ACTIVE,
		SLEEP
	}
	
	public String machineName() {
		return machineName;
	}
	
	public State generateMachineState() {
		// FIXME Should calculate the state
		Random random = new Random();
		if(random.nextFloat() < 0.5) {
			return (state = State.IDLE);
		}
		return (state = State.ACTIVE);
	}
	
	public State currentMachineState() {
		return state;
	}
	
	private String formatTime(long elapsedTime) {
		String format = String.format("%%0%dd", 2);
		elapsedTime = elapsedTime / 1000;
		String seconds = String.format(format, elapsedTime % 60);
		String minutes = String.format(format, (elapsedTime % 3600) / 60);
		String hours = String.format(format, elapsedTime / 3600);
		String time = String.format("%sh:%sm:%ss", hours, minutes, seconds);
		return time;
	}
	
	private String aplyLayout(Object[] parameters) {
		return String.format("machine=%s\n" +
				"simulated=%s\n"+
				"sleep=%s\n"+
				"idle=%s\n"+
				"active=%s\n"+
				"total-processing=%s\n" + 
				"processing=%s\n" +
				"download=%s\n" +
				"downloading-processing=%s", parameters);
	}
	
	public String formatedResume() {
		Object[] parameters = new Object[]{machineName, 
				formatTime(totalSimulatedTime.asMicroseconds()),
				formatTime(sleepingTime.asMicroseconds()),
				formatTime(idleTime.asMicroseconds()), 
				formatTime(activeTime.asMicroseconds()),
				formatTime(totalProcessingTime.asMicroseconds()),
				formatTime(processingTime.asMicroseconds()), 
				formatTime(downloadTime.asMicroseconds()),
				formatTime(downloadAndProcessingTime.asMicroseconds())};
		return aplyLayout(parameters);
	}
	
	public void addStateTime(Time taskProcessingTime) {
		switch(state) {
			case IDLE:
				idleTime = idleTime.plus(taskProcessingTime);
				break;
			case ACTIVE:
				activeTime = activeTime.plus(taskProcessingTime);
				break;
			case SLEEP:
				sleepingTime = sleepingTime.plus(taskProcessingTime);
				break;
		}
		plusTotalConsumedTime(taskProcessingTime);
	}
	
	private void plusTotalProcessingTime(Time taskProcessingTime) {
		addStateTime(taskProcessingTime);
		totalProcessingTime = totalProcessingTime.plus(taskProcessingTime);
	}
	
	private void plusTotalConsumedTime(Time taskProcessingTime) {
		totalSimulatedTime = totalSimulatedTime.plus(taskProcessingTime);
	}
	
	public void plusProcessingTime(Time taskProcessingTime) {
		addStateTime(taskProcessingTime);
		plusTotalProcessingTime(taskProcessingTime);
		processingTime = processingTime.plus(taskProcessingTime);
	}
	
	public void plusDownloadTime(Time taskProcessingTime) {
		addStateTime(taskProcessingTime);
		downloadTime = downloadTime.plus(taskProcessingTime);
	}
	
	public void plusDownloadAndProcessingTime(Time taskProcessingTime) {
		addStateTime(taskProcessingTime);
		plusTotalProcessingTime(taskProcessingTime);
		downloadAndProcessingTime = downloadAndProcessingTime.plus(taskProcessingTime);
	}
	
	protected boolean thereIsMoreTasks() {
		return limitOfTasks >= currentTask();
	}
	
	protected long limitOfTasks() {
		return limitOfTasks;
	}
	
	abstract protected long currentTask();

}
