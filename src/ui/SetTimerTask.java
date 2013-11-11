package ui;

import java.util.TimerTask;

public class SetTimerTask extends TimerTask {
	private int i;
	
	public SetTimerTask(int setTimeLeft) {
		i = setTimeLeft;
	}

    public void run() {
    	i--;
    	Drill_Sergeant.setText(Integer.toString(i));
    	if (i < 0) {
    		Drill_Sergeant.setText("DONE!");
    		this.cancel();
    	}
    }
}
