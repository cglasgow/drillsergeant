package data;
import java.util.Timer;
import java.util.TimerTask;

public class ActiveWorkout extends Workout {
	
	//***NOTE*** - All times are in milliseconds.
	
	private long currentTime;
	private int currentExerciseIndex = -1;
	private Exercise currentExercise;
	private String currentExerciseName;
	private String nextExerciseName;
	private int currentSet = 1;
	private boolean isResting = false;
	private int setTotal;
	private int reps;
	private int setTimeLeft;
	private int workoutTimeLeft;
	private int workoutProgressPercent;
	private int workoutStatus; //"RUNNING", "PAUSED", "STOPPED", or "COMPLETED"
	
	public ActiveWorkout(Workout base) {
		super("Active Workout");
		dateCreated = base.dateCreated;
		exercises = base.exercises;
		id = base.id;
		lastModified = base.lastModified;
		lengthInSecs = base.lengthInSecs;
		name = base.name;
	}
	
	public void decrementTimeLeft() {
		workoutTimeLeft--;
	}
	
	public void setCurrentExerciseIndex(int index) {
		currentExerciseIndex = index;
	}
	
	public void setCurrentSet(int setNum) {
		currentSet = setNum;
	}
	
	public void setIsResting(boolean val) {
		isResting = val;
	}
	
	public int getTimeLeft() {
		return workoutTimeLeft;
	}
	
	public int getCurrentExerciseIndex() {
		return currentExerciseIndex;
	}
	
	public int getCurrentSet() {
		return currentSet;
	}
	
	public boolean getIsResting() {
		return isResting;
	}
}
