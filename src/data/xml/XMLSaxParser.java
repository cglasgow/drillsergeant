package data.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLSaxParser extends DefaultHandler {

    private Workout workout;
    private Exercise exercise;
    private String temp;
    private ArrayList<Workout> workoutList = new ArrayList<Workout>();
   
//    public void callParser(String uri) throws Exception {
//
//    	//Create a "parser factory" for creating SAX parsers
//    	SAXParserFactory spfac = SAXParserFactory.newInstance();
//	
//    	//Now use the parser factory to create a SAXParser object
//    	SAXParser sp = spfac.newSAXParser();
//	
//    	//Create an instance of this class; it defines all the handler methods
//    	XMLSaxParser handler = new XMLSaxParser();
//	
//    	//Finally, tell the parser to parse the input and notify the handler
//    	sp.parse(uri, handler);
//	
//    	handler.readList();
//	}    

//    /** The main method sets things up for parsing */
//    public static void main(String[] args) throws IOException, SAXException,
//                  ParserConfigurationException {
//          
//           //Create a "parser factory" for creating SAX parsers
//           SAXParserFactory spfac = SAXParserFactory.newInstance();
//
//           //Now use the parser factory to create a SAXParser object
//           SAXParser sp = spfac.newSAXParser();
//
//           //Create an instance of this class; it defines all the handler methods
//           XMLSaxParser handler = new XMLSaxParser();
//
//           //Finally, tell the parser to parse the input and notify the handler
//           sp.parse("/config/myworkouts.xml", handler);  //***MOVED***
//          
//           handler.readList();
//
//    }
    

    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
           temp = new String(buffer, start, length);
    }
   

    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */ 
    public void startElement(String uri, String localName,
                  String qName, Attributes attributes) throws SAXException {
           temp = "";
           if (qName.equalsIgnoreCase("Workout")) {
        	   workout = new Workout();
        	   workout.setID(attributes.getValue("id"));
        	   workout.setName(attributes.getValue("name"));
        	   workout.setDateCreated(attributes.getValue("dateCreated"));
        	   workout.setLastModified(attributes.getValue("lastModified"));
        	   workout.setLengthInSecs(attributes.getValue("lengthInSecs"));
           } else if (qName.equalsIgnoreCase("Exercise")) {
        	   exercise = new Exercise();
        	   exercise.setPos(attributes.getValue("pos"));
        	   exercise.setName(attributes.getValue("name"));
        	   exercise.setSets(attributes.getValue("sets"));
        	   exercise.setReps(attributes.getValue("reps"));
        	   exercise.setRestBetween(attributes.getValue("restBetween"));
        	   exercise.setRestAfter(attributes.getValue("restAfter"));
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
                  System.out.println(it.next().toString());
           }
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
