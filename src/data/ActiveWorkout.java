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
	private int setTotal;
	private int reps;
	private int setTimeLeft;
	private int workoutTimeLeft;
	private int workoutProgressPercent;
	private int workoutStatus; //"RUNNING", "PAUSED", "STOPPED", or "COMPLETED"
	
	public ActiveWorkout(Workout base) {
		super("Active Workout");
		this.dateCreated = base.dateCreated;
		this.exercises = base.exercises;
		this.id = base.id;
		this.lastModified = base.lastModified;
		this.lengthInSecs = base.lengthInSecs;
		this.name = base.name;
	}
	
	public void decrementTimeLeft() {
		workoutTimeLeft--;
	}
	
	public void setCurrentExerciseIndex(int index) {
		this.currentExerciseIndex = index;
	}
	
	public void setCurrentSet(int setNum) {
		this.currentSet = setNum;
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
}
