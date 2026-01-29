package repository;
import model.Exercise;
import java.util.List;
import exception.DatabaseOperationException;

public interface ExerciseQueries {
    List<Exercise> getByWorkoutId(int workoutId) throws DatabaseOperationException;
}
