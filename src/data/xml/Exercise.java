package data.xml;

public class Exercise {
   	
	private String pos, name, sets, reps, restBetween, restAfter;
   	
   	
   	public Exercise() {
    }
   	
   	public Exercise(String pos, String name, String sets, String reps, String restBetween, String restAfter) {
        this.pos			= pos;
        this.name 			= name;
        this.sets			= sets;
        this.reps			= reps;
        this.restBetween	= restBetween;
        this.restAfter		= restAfter;
	 }
   	
   	
   	public String getPos() {
   		return pos;
   	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	 
	 
   	public String getName() {
   		return name;
   	}
	public void setName(String name) {
		this.name = name;
	}
	 
	 
   	public String getSets() {
   		return sets;
   	}
	public void setSets(String sets) {
		this.sets = sets;
	}
	 
	 
   	public String getReps() {
   		return reps;
   	}
	public void setReps(String reps) {
		this.reps = reps;
	}
	 
	 
   	public String getRestBetween() {
   		return restBetween;
   	}
	public void setRestBetween(String restBetween) {
		this.restBetween = restBetween;
	}
	 
	 
   	public String getRestAfter() {
   		return restAfter;
   	}
	public void setRestAfter(String restAfter) {
		this.restAfter = restAfter;
	}
}