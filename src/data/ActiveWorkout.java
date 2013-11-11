package data;
import java.util.Timer;
import java.util.TimerTask;

public class ActiveWorkout extends Workout {
	
	//***NOTE*** - All times are in milliseconds.
	
	private long currentTime;
	private int currentExerciseIndex = 0;
	private Exercise currentExercise;
	private String currentExerciseName;
	private String nextExerciseName;
	private int set;
	private int setTotal;
	private int reps;
	private int setTimeLeft;
	private int workoutTimeLeft;
	private int workoutProgressPercent;
	private int workoutStatus; //"RUNNING", "PAUSED", "STOPPED", or "COMPLETED"
	
	public ActiveWorkout() {
		super("Active Workout");
	}
}
