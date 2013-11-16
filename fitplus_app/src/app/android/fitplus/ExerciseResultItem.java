package app.android.fitplus;

public class ExerciseResultItem {
	private String exerciseName;
	private int exerciseCounter;
	
	public ExerciseResultItem(String exerciseName, int exerciseCounter) {
		this.exerciseName = exerciseName;
		this.exerciseCounter = exerciseCounter;
	}
	public String getExerciseName() {
		return exerciseName;
	}
	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}
	public int getExerciseCounter() {
		return exerciseCounter;
	}
	public void setExerciseCounter(int exerciseCounter) {
		this.exerciseCounter = exerciseCounter;
	}
}
