package service;

import exception.DatabaseOperationException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Workout;

import java.util.List;

public interface IWorkoutService {

    void createWorkout(Workout workout)
            throws InvalidInputException, DatabaseOperationException;

    List<Workout> getAllWorkouts() throws DatabaseOperationException;

    List<Workout> getAllWorkoutsSortedByDuration()
            throws DatabaseOperationException;

    Workout getWorkoutById(int id)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;

    Workout getShortestC() throws DatabaseOperationException;

    Workout getShortestS() throws DatabaseOperationException;

    void updateWorkout(int id, Workout workout)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;

    void deleteWorkout(int id)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;
}
