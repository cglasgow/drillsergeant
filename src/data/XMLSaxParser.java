package data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ui.Drill_Sergeant;



public class XMLSaxParser extends DefaultHandler {
	private Workout workout;
	public Settings settings;
    private ArrayList<Workout> workoutList = new ArrayList<Workout>();
    private String tempString;
    private ArrayList<String> listItems = new ArrayList<String>();

    //************************************************************
  	// characters
    //		When the parser encounters plain text (not XML elements),
    //		it calls this method, which accumulates them in a string buffer.
    //************************************************************
    public void characters(char[] buffer, int start, int length) {
           tempString = new String(buffer, start, length);
    }
   

    //************************************************************
    // startElement
    //		Every time the parser encounters the beginning of a new element,
    //		it calls this method, which resets the string buffer and parses
    //		through the element's attributes.
	//************************************************************
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
           tempString = "";
           
           if (qName.equalsIgnoreCase("Workout")) {
        	   workout = new Workout("nameNotYetKnown");
        	   workout.setID(attributes.getValue("id"));
        	   workout.setName(attributes.getValue("name"));
        	   workout.setDateCreated(attributes.getValue("dateCreated"));
        	   workout.setLastModified(attributes.getValue("lastModified"));
        	  // workout.setLengthInSecs(attributes.getValue("lengthInSecs"));
           } else if (qName.equalsIgnoreCase("Exercise")) {
        	   Exercise exercise = new Exercise();
        	   exercise.setName(attributes.getValue("name"));
        	   exercise.setPosition(Integer.parseInt(attributes.getValue("pos")));
        	   exercise.setReps(attributes.getValue("reps"));
        	   exercise.setSets(attributes.getValue("sets"));
        	   exercise.setTimeBetween(attributes.getValue("timeBetweenMin"), attributes.getValue("timeBetweenSec"));
        	   exercise.setRestAfter(attributes.getValue("restAfterMin"), attributes.getValue("restAfterSec"));
        	   exercise.setTotalTime();
        	   workout.addExercise(exercise);
           } else if (qName.equalsIgnoreCase("Settings")) {
        	   settings = new Settings();
           } else if (qName.equalsIgnoreCase("Sound")) {
        	   settings.setSound(attributes.getValue("value"));
           }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
                  throws SAXException {

    	if (qName.equalsIgnoreCase("Workout")) {
    		//Calculate the total time of the workout.
    		int totalWorkoutTime = 0;
    		int numExercises = workout.getExerciseListSize();
    		for (int i=0; i<numExercises; i++) {
    			int numSets = workout.getExercise(i).getSets();
    			int timePerSet = workout.getExercise(i).getTimeBetween();
    			int restTimeAfter = workout.getExercise(i).getRestAfter();
    			int exerciseTime = (numSets * timePerSet) + restTimeAfter;
    			totalWorkoutTime += exerciseTime;
    		}
    		System.out.println(totalWorkoutTime);
    		workout.setLengthInSecs(Integer.toString(totalWorkoutTime));
    		//Add it to the list
    		workoutList.add(workout);
    	}
    }

    public void readList() {
    		listItems.clear();
           	System.out.println("Number of workouts in file: '" + workoutList.size()  + "'.");
           	Iterator<Workout> it = workoutList.iterator();
           	while (it.hasNext()) {
           		String listItem = it.next().toString();
           		listItems.add(listItem);
           		System.out.println(listItem);
           	}
	}
    
    public ArrayList<String> getListItems() {
    	return listItems;
    }
    
    public ArrayList<Workout> getWorkoutList() {
        return workoutList;
    }
    
    public void addToWorkoutList(Workout theWorkout, int saveIndex) {
    	if (saveIndex == -1) {
    		//Add new workout to the list.
    		workoutList.add(theWorkout);
    	} else {
    		//Overwrite existing workout.
    		workoutList.set(saveIndex, theWorkout);
    	}
    }
    
    public void removeFromWorkoutList(int index) {
    	System.out.println("workoutList size: " + workoutList.size());
    	workoutList.remove(index);
    	System.out.println("workoutList size: " + workoutList.size());
    }
    
    public void clearWorkoutList() {
    	System.out.println("workoutList size: " + workoutList.size());
    	workoutList.clear();
    	System.out.println("workoutList size: " + workoutList.size());
    }
    
    public Workout getWorkout(int arrayIndex) {
        return workoutList.get(arrayIndex);
    }
    
    public int getWorkoutListSize() {
        return workoutList.size();
    }
    
    public Settings getSettings() {
    	return settings;
    }
}
