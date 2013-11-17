package ui;

import java.util.TimerTask;

public class WorkoutTimerTask extends TimerTask {
	private int timeLeft;
	private String timerType;
	Drill_Sergeant dsRef;
	
	public WorkoutTimerTask(Drill_Sergeant ds, int theTimeLeft, String theTimerType) {
		timeLeft = theTimeLeft;
		timerType = theTimerType;
		dsRef = ds;
	}
	
	public void run() {
    	if (timerType == "MASTER")
    		countDownTotalTime();
    	else if (timerType == "SET")
    		countDownSetTime();
    }
	
    public void countDownSetTime() {
    	dsRef.setTxtSetTimeLeft(Integer.toString(timeLeft));
    	timeLeft--;
    	if (timeLeft < 0) {
    		this.cancel();
    		dsRef.loadNextSet();
    	}
    }
    
    public void countDownTotalTime() {
    	dsRef.setTxtTotalTimeLeft(Integer.toString(timeLeft));
    	timeLeft--;
    	if (timeLeft < 0) {
    		this.cancel();
    	}
    }
    public int getTimeLeft() {
    	return timeLeft;
    }

}
