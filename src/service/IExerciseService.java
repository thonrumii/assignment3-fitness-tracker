package service;

import exception.DatabaseOperationException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Exercise;

import java.util.List;

public interface IExerciseService {

    void addExercise(int workoutId, Exercise exercise)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;

    void updateExercise(int id, Exercise exercise)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;

    Exercise getExerciseById(int id)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;

    List<Exercise> getExercisesByWorkout(int workoutId)
            throws InvalidInputException, DatabaseOperationException;

    void deleteExercise(int id)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException;
}
