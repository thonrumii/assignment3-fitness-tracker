package model;

public class Exercise {
    private int id;
    private int workoutId;
    private String name;
    private int sets;
    private int reps;

    public Exercise(int workoutId, String name, int sets, int reps) {
        setWorkoutId(workoutId);
        setName(name);
        setSets(sets);
        setReps(reps);
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkoutId() {return workoutId;}
    public void setWorkoutId(int workoutId) {this.workoutId = workoutId;}

    public String getName() {return name;}
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Exercise name cannot be empty");
        this.name = name;}

    public int getSets() {return sets;}
    public void setSets(int sets) {
        if (sets <= 0) throw new IllegalArgumentException("Sets must be > 0");
        this.sets = sets;
    }

    public int getReps() {return reps;}
    public void setReps(int reps) {
        if (reps <= 0) throw new IllegalArgumentException("Reps must be > 0");
        this.reps = reps;
    }

}
