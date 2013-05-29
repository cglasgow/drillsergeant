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
	public Workout workout;
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
        	   exercise.setPosition(Integer.parseInt(attributes.getValue("pos")));
        	   exercise.setName(attributes.getValue("name"));
        	   exercise.setSets(attributes.getValue("sets"));
        	   exercise.setRestBetween(attributes.getValue("restBetweenMin"), attributes.getValue("restBetweenSec"));
        	   exercise.setRestAfter(attributes.getValue("restAfterMin"), attributes.getValue("restAfterSec"));
        	   workout.addExercise(exercise);
           }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName)
                  throws SAXException {

           if (qName.equalsIgnoreCase("Workout")) {
                  // add it to the list
                  workoutList.add(workout);
           }
    }

    public void readList() {
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
    
    public Workout getWorkout(int arrayIndex) {
        return workoutList.get(arrayIndex);
    }
    
    public int getWorkoutListSize() {
        return workoutList.size();
    }
}
