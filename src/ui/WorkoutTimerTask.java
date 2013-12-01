package ui;

import java.awt.Color;
import java.util.TimerTask;

import util.Format;

public class WorkoutTimerTask extends TimerTask {
	private int timeLeft;
	private String stringTimeLeft;
	private String timerType;
	Drill_Sergeant dsRef;
	
	public WorkoutTimerTask(Drill_Sergeant ds, int theTimeLeft, String theTimerType) {
		timeLeft = theTimeLeft;
		timerType = theTimerType;
		dsRef = ds;
	}
	
	public void run() {
    	if (timerType == "MASTER") {
    		countDownTotalTime();
    	} else if (timerType == "SET" || timerType == "REST") {
    		countDownSetTime();
    	}
    }
	
    public void countDownSetTime() {
    	stringTimeLeft = Format.toMSS(timeLeft);
    	dsRef.setTxtSetTimeLeft(stringTimeLeft);
    	timeLeft--;
    	if (timeLeft < 0) {
    		this.cancel();
    		dsRef.loadNextSet();
    	}
    }
    
    public void countDownTotalTime() {
    	stringTimeLeft = Format.toHHMMSS(timeLeft);
    	dsRef.setTxtTotalTimeLeft(stringTimeLeft);
    	dsRef.updateProgressBar(timeLeft);
    	timeLeft--;
    	if (timeLeft < 0) {
    		this.cancel();
    	}
    }
    
    public int getTimeLeft() {
    	return timeLeft;
    }
}
