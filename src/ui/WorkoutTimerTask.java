package ui;

import java.util.TimerTask;

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
    	if (timerType == "MASTER")
    		countDownTotalTime();
    	else if (timerType == "SET")
    		countDownSetTime();
    }
	
    public void countDownSetTime() {
    	stringTimeLeft = this.toMSS(timeLeft);
    	dsRef.setTxtSetTimeLeft(stringTimeLeft);
    	timeLeft--;
    	if (timeLeft < 0) {
    		this.cancel();
    		dsRef.loadNextSet();
    	}
    }
    
    public void countDownTotalTime() {
    	stringTimeLeft = this.toHHMMSS(timeLeft);
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
    
    public String toHHMMSS (int totalTimeInSecs) {
    	int totalMinutes = totalTimeInSecs/60;
    	
    	int secs	= totalTimeInSecs % 60;
    	int minutes = totalMinutes % 60;
    	int hours	= totalMinutes/60;
    	
    	//System.out.println("hours = " + hours + " minutes = " + minutes + " secs = " + secs);
    	String HH 	= Integer.toString(hours);
    	String MM	= Integer.toString(minutes);
    	String SS	= Integer.toString(secs);

    	if (HH.length() == 1) {
    		HH = "0" + HH; 
    	}
    	if (MM.length() == 1) {
    		MM = "0" + MM; 
    	}
    	if (SS.length() == 1) {
    		SS = "0" + SS; 
    	}
    	System.out.println(HH + ":" + MM + ":" + SS);
    	return HH + ":" + MM + ":" + SS;
    }
    
    public String toMSS (int totalTimeInSecs) {
    	int minutes = totalTimeInSecs/60;
    	int secs	= totalTimeInSecs % 60;

    	String M	= Integer.toString(minutes);
    	String SS	= Integer.toString(secs);

    	if (SS.length() == 1) {
    		SS = "0" + SS; 
    	}
    	System.out.println(M + ":" + SS);
    	return M + ":" + SS;
    }
}
