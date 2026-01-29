package utils;

import model.Workout;
import java.util.Comparator;
import java.util.List;

public class SortUtils {

    public static void sortWorkoutsByDuration(List<Workout> workouts) {
        workouts.sort(Comparator.comparingInt(Workout::getDuration));
    }

    public static void sortWorkoutsByName(List<Workout> workouts) {
        workouts.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
    }
}
