package data;

public class Exercise {
	private String name;
	private String graphicURL;
	private int position;			//The position of the exercise relative to the other exercises in the workout.
	private int sets;
	private int reps;
	private int restBetweenSets;	//in seconds
	private int restAfterExercise;	//in seconds
	
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
	
	public void setRestBetween(String minutes, String seconds) {
		int intMinutes = Integer.parseInt(minutes);
		int intSeconds = Integer.parseInt(seconds);
		restBetweenSets = (intMinutes * 60) + intSeconds;
	}
	
	public void setRestAfter(String minutes, String seconds) {
		int intMinutes = Integer.parseInt(minutes);
		int intSeconds = Integer.parseInt(seconds);
		restAfterExercise = (intMinutes * 60) + intSeconds;
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
	
	public int getRestBetween() {
		return restBetweenSets;
	}
	
	public int getRestAfter() {
		return restAfterExercise;
	}
}
