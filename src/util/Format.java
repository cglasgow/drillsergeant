package util;

public class Format {
	private Format () {
	}
	
	/************************************************************
 	 toHHMMSS
	 
	************************************************************/
    public static String toHHMMSS (int totalTimeInSecs) {
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

	/************************************************************
	 toMSS
	 
	************************************************************/
    public static String toMSS (int totalTimeInSecs) {
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
