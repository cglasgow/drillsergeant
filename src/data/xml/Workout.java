package data.xml;

import java.util.ArrayList;

public class Workout {
	
       private String id;
       private String name;
       private String dateCreated;
       private String lastModified;
       private String lengthInSecs;
       private ArrayList<Exercise> exercises = new ArrayList<Exercise>();
       
       

       public Workout() {
       }

       public Workout(String id, String name, String dateCreated, String lastModified, String lengthInSecs) {
              this.id 				= id;
              this.name 			= name;
              this.dateCreated		= dateCreated;
              this.lastModified		= lastModified;
              this.lengthInSecs		= lengthInSecs;
       }

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
       
       
       public void addExercise(Exercise theExercise) {
    	   	exercises.add(theExercise);
   		}
       
       public String toString() {
              StringBuffer sb = new StringBuffer();
              sb.append("Name: " + getName());
              sb.append(", ");
              sb.append("Total Length: " + getLengthInSecs());
              sb.append(", ");
              sb.append("DateCreated: " + getDateCreated());

              return sb.toString();
       }
}

