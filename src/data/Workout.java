package data;

import java.util.*; //For ArrayList



public class Workout {
	private String name;
	private String id;
    private String dateCreated;
    private String lastModified;
    private String lengthInSecs;
	private ArrayList<Exercise> exercises = new ArrayList<Exercise>(); //The list of exercises contained within the workout.
	private int currentExerciseIndex = 0;
	
	
	
	public Workout(String theName) {
		name = theName;
	}
	
	//------------------------------
	//Workout Manipulation Methods
	//------------------------------
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getLastModified() {
			return lastModified;
	}
	
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	
	public String getLengthInSecs() {
		return lengthInSecs;
	}
	
	public void setLengthInSecs(String lengthInSecs) {
		this.lengthInSecs = lengthInSecs;
	}
	
	public void run() {
		
		//TO DO:  THIS METHOD WILL CONTAIN THE CODE THAT ACTIVATES A WORKOUT AND RUNS THROUGH IT.
	
	}
	
	//------------------------------
	//Exercise-Manipulation Methods
	//------------------------------
	public void addExercise(Exercise theExercise) {
		if (currentExerciseIndex < 20) {
			exercises.add(theExercise);
			currentExerciseIndex++;
		}
	}
	
	public void loadExercise() {
		//THIS METHOD WILL BE USED BY THE GUI TO POPULATE THE 'EDIT WORKOUT' SCREEN
	}
	
	public void deleteExercise(int index) {
		exercises.remove(index);
	}
	
	//------------------------------
	//Misc.
	//------------------------------
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name: " + getName());
		sb.append(" - ");
		sb.append("Total Length: " + (Integer.parseInt(getLengthInSecs()) / 60) + " minutes");
		sb.append(" - ");
		sb.append("DateCreated: " + getDateCreated());
		return sb.toString();
	}
	
	
//	public void printExercise(int index) {
//		System.out.print(exercises[index].getName());
//		System.out.print("\n");
//		System.out.print(exercises[index].getGraphicURL());
//		System.out.print("\n");
//		System.out.print(exercises[index].getSets());
//		System.out.print("\n");
//		System.out.print(exercises[index].getReps());
//		System.out.print("\n");
//		System.out.print(exercises[0].getRestBetween());
//		System.out.print("\n");
//		System.out.print(exercises[index].getRestAfter());
//		System.out.print("\n---------------------\n");
//	}
}
