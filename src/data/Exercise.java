package data;

public class Exercise {
	private String name;
	private String graphicURL;
	private int position;			//The position of the exercise relative to the other exercises in the workout.
	private int sets;
	private int reps;
	private int timeBetweenSets;	//in seconds
	private int restAfterExercise;	//in seconds
	private int totalTime;				//in seconds
	
	//---------------------------------------
	//SET Functions
	//---------------------------------------
	public void setName(String theName) {
		name = theName;
	}
	
	public void setGraphicURL(String url) {
		graphicURL = url;
	}
	
	public void setPosition(int pos) {
		position = pos;
	}
	
	public void setSets(String numSets) {
		sets = Integer.parseInt(numSets);
	}
	
	public void setReps(String numReps) {
		reps = Integer.parseInt(numReps);
	}
	
	public void setTimeBetween(String minutes, String seconds) {
		int intMinutes = Integer.parseInt(minutes);
		int intSeconds = Integer.parseInt(seconds);
		timeBetweenSets = (intMinutes * 60) + intSeconds;
	}
	
	public void setRestAfter(String minutes, String seconds) {
		int intMinutes = Integer.parseInt(minutes);
		int intSeconds = Integer.parseInt(seconds);
		restAfterExercise = (intMinutes * 60) + intSeconds;
	}
	
	public void setTotalTime() {
		totalTime = (timeBetweenSets * sets) + restAfterExercise;
	}
	
	//---------------------------------------
	//GET Functions
	//---------------------------------------
	public String getName() {
		return name;
	}
	
	public String getGraphicURL() {
		return graphicURL;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int getSets() {
		return sets;
	}
	
	public int getReps() {
		return reps;
	}
	
	public int getTimeBetween() {
		return timeBetweenSets;
	}
	
	public int getRestAfter() {
		return restAfterExercise;
	}
	
	public int getTotalTime() {
		return totalTime;
	}
}
