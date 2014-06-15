package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import util.Format;



public class Workout {
	protected String name;
	protected String id;
	protected String dateCreated;
	protected String lastModified;
	protected String lengthInSecs;
	protected ArrayList<Exercise> exercises = new ArrayList<Exercise>(); //The list of exercises contained within the workout.
	protected int currentExerciseIndex = 0;
	
	
	
	public Workout(String theName) {
		name = theName;
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		setDateCreated(dateString);
	}

	//------------------------------
	//Workout-Related Methods
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
	
	public void setDateCreated(String date) {
		this.dateCreated = date;
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
	
	public int calculateLengthInSecs() {
		int totalLength = 0;
		for (int i=0; i <= this.exercises.size(); i++) {
			totalLength += this.getExercise(i).getTotalTime();
		}
		return totalLength;
	}
	
	public void save(XMLSaxParser theHandler, int saveIndex) {
		//Update the "Last Modified" date.
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		setLastModified(dateString);
		
		//Save
		theHandler.addToWorkoutList(this, saveIndex);
		
		//Create a new XML file with the updated data.
		XMLWriter theXMLWriter = new XMLWriter();
		theXMLWriter.writeXML(theHandler);
	}
	
	public void load() {} {
		
	}
	
	public void run() {
		
		//TO DO:  THIS METHOD WILL CONTAIN THE CODE THAT ACTIVATES A WORKOUT AND RUNS THROUGH IT.
	
	}
	
	//------------------------------
	//Exercise-Reated Methods
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
	
	public int getExerciseListSize() {
		return exercises.size();
	}
	
	public Exercise getExercise(int index) {
		return exercises.get(index);
	}
	
	//------------------------------
	//Misc.
	//------------------------------
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName() + "  -  ");
		sb.append("Created: " + getDateCreated());
		sb.append("   ");
		sb.append("Last Modified: " + getLastModified());
		sb.append("   ");
		sb.append("Length: " + Format.toHHMMSS(Integer.parseInt(getLengthInSecs())));
		return sb.toString();
	}
}
