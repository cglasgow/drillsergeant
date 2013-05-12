package data;

import java.io.*;

public class Settings {
	private boolean soundEnabled = true;
	
	public void setSound(boolean status) {
		if (status == true) {
			status = false;
		} else {
			status = true;
		}
		//
		//TO DO: Add code for writing sound status to settings.ini
		//
	}
}
