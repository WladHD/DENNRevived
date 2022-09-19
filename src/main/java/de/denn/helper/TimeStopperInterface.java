package de.denn.helper;

import java.util.concurrent.TimeUnit;

public interface TimeStopperInterface {
	
	void startTimeMeasure();
	
	void stopTimeMeasure();
	
	long getStoppedTime(TimeUnit tu);
	
}
