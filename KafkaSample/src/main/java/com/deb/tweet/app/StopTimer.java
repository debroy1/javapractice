package com.deb.tweet.app;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

// Timer class to exit once delay period is over
public class StopTimer extends Thread {
	long delay;
	public StopTimer(Duration duration) {
		this.delay = duration.toMillis();
		this.setDaemon(true);
	}
	@Override
	public synchronized void start() {
	    TimerTask task = new TimerTask() {
	        public void run() {
	        	System.err.println("Delay period is over - shutting down!");
	        	System.exit(MAX_PRIORITY);
	        }
	    };
	    Timer timer = new Timer();
	    timer.schedule(task, delay);
	}
}
