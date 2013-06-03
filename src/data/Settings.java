package data;

import java.io.*;

public class Settings {
	private boolean isSoundEnabled = true;
	
	public void setSound(String status) {
		if (status == "on") {
			isSoundEnabled = true;
		} else {
			isSoundEnabled = false;
		}
	}
	
	public boolean getSound() {
		return isSoundEnabled;
	}
}
