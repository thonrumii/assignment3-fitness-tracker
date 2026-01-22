package service;

import exception.DatabaseOperationException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Exercise;
import repository.ExerciseRepository;
import repository.WorkoutRepository;

import java.util.List;

public class ExerciseService {

    private final ExerciseRepository exerciseRepository = new ExerciseRepository();
    private final WorkoutRepository workoutRepository = new WorkoutRepository();

    // CREATE
    public void addExercise(int workoutId, Exercise exercise)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        if (workoutId <= 0) {
            throw new InvalidInputException("Invalid workout id");
        }

        if (exercise == null) {
            throw new InvalidInputException("Exercise cannot be null");
        }

        if (exercise.getName() == null || exercise.getName().isBlank()) {
            throw new InvalidInputException("Exercise name cannot be empty");
        }

        if (exercise.getSets() <= 0 || exercise.getReps() <= 0) {
            throw new InvalidInputException("Sets and reps must be greater than 0");
        }

        if (workoutRepository.getById(workoutId) == null) {
            throw new ResourceNotFoundException("Workout not found");
        }

        exerciseRepository.create(workoutId, exercise);
    }

    // UPDATE
    public void updateExercise(int id, Exercise exercise)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        if (id <= 0) {
            throw new InvalidInputException("Invalid exercise id");
        }

        if (exercise == null) {
            throw new InvalidInputException("Exercise cannot be null");
        }

        if (exercise.getName() == null || exercise.getName().isBlank()) {
            throw new InvalidInputException("Exercise name cannot be empty");
        }

        if (exercise.getSets() <= 0 || exercise.getReps() <= 0) {
            throw new InvalidInputException("Sets and reps must be greater than 0");
        }
        Exercise existing = exerciseRepository.getById(id);

        if (existing == null) {
            throw new ResourceNotFoundException("Exercise not found");
        }

        exerciseRepository.update(id, exercise);
    }


    // GET BY ID
    public Exercise getExerciseById(int id)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        if (id <= 0) {
            throw new InvalidInputException("Invalid exercise id");
        }

        Exercise exercise = exerciseRepository.getById(id);

        if (exercise == null) {
            throw new ResourceNotFoundException("Exercise not found");
        }

        return exercise;
    }

    // GET BY WORKOUT
    public List<Exercise> getExercisesByWorkout(int workoutId)
            throws InvalidInputException, DatabaseOperationException {

        if (workoutId <= 0) {
            throw new InvalidInputException("Invalid workout id");
        }

        return exerciseRepository.getByWorkoutId(workoutId);
    }

    // DELETE
    public void deleteExercise(int id)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        if (id <= 0) {
            throw new InvalidInputException("Invalid exercise id");
        }

        if (exerciseRepository.getById(id) == null) {
            throw new ResourceNotFoundException("Exercise not found");
        }

        exerciseRepository.delete(id);
    }
}

