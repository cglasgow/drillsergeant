package data;
import java.util.Timer;
import java.util.TimerTask;

public class ActiveWorkout extends Workout {
	
	//***NOTE*** - All times are in milliseconds.
	
	private static long currentTime;
	private static int currentExerciseIndex = 0;
	private static Exercise currentExercise;
	private static String currentExerciseName;
	private static String nextExerciseName;
	private static int set;
	private static int setTotal;
	private static int reps;
	private static int setTimeLeft;
	private static int workoutTimeLeft;
	private static int workoutProgressPercent;
	private static int workoutStatus; //"RUNNING", "PAUSED", "STOPPED", or "COMPLETED"
	
	public ActiveWorkout() {
		super("Active Workout");
	}
	
	public void decrementTimeLeft() {
		workoutTimeLeft--;
	}
	
	public int getTimeLeft() {
		return workoutTimeLeft;
	}
}
