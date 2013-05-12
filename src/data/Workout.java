package data;

import java.util.*; //For ArrayList

public class Workout {
	private String name;
	private ArrayList<Exercise> exercises = new ArrayList<Exercise>();
	private int currentIndex = 0;
	
	
	
	public Workout(String theName) {
		name = theName;
	}
	
	public String getName() {
		return name;
	}
	
	public void loadExercise() {
		//THIS METHOD WILL BE USED BY THE GUI TO POPULATE THE 'EDIT WORKOUT' SCREEN
	}
	
	public void addExercise(Exercise theExercise) {
		if (currentIndex < 20) {
			exercises.add(theExercise);
			//exercises[currentIndex] = theExercise;
			//printExercise(currentIndex);
			currentIndex++;
		}
	}
	
	public void deleteExercise(int index) {
	
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
	
	public void run() {
		
		//TO DO:  THIS METHOD WILL CONTAIN THE CODE THAT ACTIVATES A WORKOUT AND RUNS THROUGH IT.
	
	}
}
