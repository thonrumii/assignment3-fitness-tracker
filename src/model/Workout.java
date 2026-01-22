package model;
import java.util.ArrayList;
import java.util.List;
public abstract class Workout implements IValidatable, ITrackable {
    private int id;
    private String name;
    private int duration;
    private List<Exercise> exercises;

    public Workout(String name, int duration) {
        setName(name);
        setDuration(duration);
        this.exercises = new ArrayList<>();
    }

//abstract methods
    public abstract double calculateCalories();
    public abstract String getWorkoutType();

//concrete methods
    public void WorkoutInfo(){
        System.out.println("Workout name: " + name+" | Duration: " + duration+" minutes.");
    }
    public void addExercise(Exercise exercise){
        this.exercises.add(exercise);
    }

//getters and setters


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {return name;}
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name;
    }
    public int getDuration() {return duration;}
    public void setDuration(int duration) {
        if (duration <= 0) throw new IllegalArgumentException("Duration must be > 0");
        this.duration = duration;
    }
    public List<Exercise> getExercises() { return exercises; }
    public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }

    @Override
    public void validate() {
        setName(name);
        setDuration(duration);
    }

    @Override
    public String getTrackingInfo() {
        return getWorkoutType() + ": " + calculateCalories() + " calories";
    }


}
