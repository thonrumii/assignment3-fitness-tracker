package repository;

import exception.DatabaseOperationException;
import model.Workout;

public interface WorkoutQueries {
    Workout getShortestC() throws DatabaseOperationException;
    Workout getShortestS() throws DatabaseOperationException;
}
