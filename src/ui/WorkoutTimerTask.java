package ui;

import java.util.TimerTask;
import data.ActiveWorkout;

public class WorkoutTimerTask extends TimerTask {
	private int timeLeft;
	private int timerType;
	
	public WorkoutTimerTask(int theTimeLeft, int theTimerType) {
		timeLeft = theTimeLeft;
		timerType = theTimerType;
	}

    public void run() {
    	if (timerType == 0)
    		countDownTotalTime();
    	else if (timerType == 1)
    		countDownSetTime();
    }
    
    public void countDownSetTime() {
    	timeLeft--;
    	Drill_Sergeant.setTxtSetTimeLeft(Integer.toString(timeLeft));
    	if (timeLeft < 0) {
    		Drill_Sergeant.setTxtSetTimeLeft("DONE!");
    		this.cancel();
    	}
    }
    public void countDownTotalTime() {
    	timeLeft--;
    	Drill_Sergeant.setTxtTotalTimeLeft(Integer.toString(timeLeft));
    	if (timeLeft < 0) {
    		Drill_Sergeant.setTxtTotalTimeLeft("DONE!");
    		this.cancel();
    	}
    }
    public int getTimeLeft() {
    	return timeLeft;
    }
}
