package data;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ui.Drill_Sergeant;
 
public class XMLWriter {

	public void writeXML(XMLSaxParser theHandler) {
 
		try {
			//Create the document.
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("UserConfig");
			doc.appendChild(root);
			
			//--------------------
			// <Workouts>
			//--------------------
			//Create Workouts section of xml document.
			Element elementWorkouts = doc.createElement("Workouts");
			root.appendChild(elementWorkouts);
			
			for (int i = 0; i < theHandler.getWorkoutListSize(); i++) {
				System.out.println(i);
				//Create <Workout> element.
				Element elementWorkout = doc.createElement("Workout");
				elementWorkouts.appendChild(elementWorkout);
		 
				//Add root attributes.
				elementWorkout.setAttribute("id", theHandler.getWorkout(i).getID());
				elementWorkout.setAttribute("name", theHandler.getWorkout(i).getName());
				elementWorkout.setAttribute("dateCreated", theHandler.getWorkout(i).getDateCreated());
				elementWorkout.setAttribute("lastModified", theHandler.getWorkout(i).getLastModified());
				
				for (int j=0; j<theHandler.getWorkout(i).getExerciseListSize(); j++) {
					//Create <Exercise> sub-element.
					Element elementExercise = doc.createElement("Exercise");
					elementWorkout.appendChild(elementExercise);
			
					String pos 				= Integer.toString(theHandler.getWorkout(i).getExercise(j).getPosition());
					String name 			= theHandler.getWorkout(i).getExercise(j).getName();
					String sets 			= Integer.toString(theHandler.getWorkout(i).getExercise(j).getSets());
					String reps				= Integer.toString(theHandler.getWorkout(i).getExercise(j).getPosition());
					String restBetweenMin;
					String restBetweenSec;
					String restAfterMin;
					String restAfterSec;
					
					int restBetweenTotalSec	= theHandler.getWorkout(i).getExercise(j).getRestBetween();
					if (restBetweenTotalSec != 0) {
						restBetweenMin	= Integer.toString(restBetweenTotalSec / 60);
						restBetweenSec	= Integer.toString(restBetweenTotalSec % 60);
					} else {
						restBetweenMin	= "0";
						restBetweenSec	= "0";
					}
					
					int restAfterTotalSec	= theHandler.getWorkout(i).getExercise(j).getRestAfter();
					if (restAfterTotalSec != 0) {
						restAfterMin	= Integer.toString(restAfterTotalSec / 60);
						restAfterSec	= Integer.toString(restAfterTotalSec % 60);
					} else {
						restAfterMin	= "0";
						restAfterSec	= "0";
					}
					
					elementExercise.setAttribute("pos", pos);
					elementExercise.setAttribute("name", name);
					elementExercise.setAttribute("sets", sets);
					elementExercise.setAttribute("reps", reps);
					elementExercise.setAttribute("restBetweenMin", restBetweenMin);
					elementExercise.setAttribute("restBetweenSec", restBetweenSec);
					elementExercise.setAttribute("restAfterMin", restAfterMin);
					elementExercise.setAttribute("restAfterSec", restAfterSec);
				}
			}
			//--------------------
			// <Settings>
			//--------------------
			//Create <Settings> element.
			Element elementSettings = doc.createElement("Settings");
			root.appendChild(elementSettings);
			
			//Create <Sound> sub-element.
			Element elementSound = doc.createElement("Sound");
			elementSettings.appendChild(elementSound);
			Boolean boolSoundVal = theHandler.getSettings().getSound();
			String stringSoundVal;
			if (boolSoundVal == true) {
				stringSoundVal = "on";
			} else {
				stringSoundVal = "off";
			}
			elementSound.setAttribute("value", stringSoundVal);
			
			
			//Write the content to an xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("config/user_config.xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
	 
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}