package de.denn.helper;

import java.util.concurrent.TimeUnit;

public class TimeStopper implements TimeStopperInterface {
	
	private long timeStopperStartNano;
	private long timeStopperEndNano;
	private long timeStoppedDurationNano;
	
	public void startTimeMeasure() {
		timeStopperStartNano = System.nanoTime();
	}
	
	public void stopTimeMeasure() {
		timeStopperEndNano = System.nanoTime();
		timeStoppedDurationNano = timeStopperEndNano - timeStopperStartNano;
	}
	
	public long getStoppedTime(TimeUnit tu) {
		return tu.convert(timeStoppedDurationNano, TimeUnit.NANOSECONDS);
	}
}
