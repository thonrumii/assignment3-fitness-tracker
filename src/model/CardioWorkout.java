package model;
public class CardioWorkout extends Workout{

    public CardioWorkout(String name, int duration) {
        super(name, duration);
    }
    public CardioWorkout(int id, String name, int duration) {
        super(name, duration);
        setId(id);
    }
    @Override
    public double calculateCalories(){
        return getDuration()*6.5;
    }
    @Override
    public String getWorkoutType(){
        return "CARDIO";
    }

}
