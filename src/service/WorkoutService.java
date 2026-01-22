package service;

import exception.DatabaseOperationException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Workout;
import repository.WorkoutRepository;

import java.util.List;
public class WorkoutService {

    private final WorkoutRepository workoutRepository = new WorkoutRepository();

    // CREATE
    public void createWorkout(Workout workout)
            throws InvalidInputException, DatabaseOperationException {

        if (workout == null) {
            throw new InvalidInputException("Workout cannot be null");
        }

        if (workout.getName() == null || workout.getName().isBlank()) {
            throw new InvalidInputException("Workout name cannot be empty");
        }

        if (workout.getDuration() <= 0) {
            throw new InvalidInputException("Duration must be greater than 0");
        }

        workoutRepository.create(workout);
    }

    // GET ALL
    public List<Workout> getAllWorkouts() throws DatabaseOperationException {
        return workoutRepository.getAll();
    }

    // GET BY ID
    public Workout getWorkoutById(int id)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        if (id <= 0) {
            throw new InvalidInputException("Invalid workout id");
        }

        Workout workout = workoutRepository.getById(id);

        if (workout == null) {
            throw new ResourceNotFoundException("Workout with id " + id + " not found");
        }

        return workout;
    }

    // UPDATE
    public void updateWorkout(int id, Workout workout)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        if (id <= 0) {
            throw new InvalidInputException("Invalid workout id");
        }

        if (workout == null) {
            throw new InvalidInputException("Workout cannot be null");
        }

        if (workout.getName() == null || workout.getName().isBlank()) {
            throw new InvalidInputException("Workout name cannot be empty");
        }

        if (workout.getDuration() <= 0) {
            throw new InvalidInputException("Duration must be greater than 0");
        }

        if (workoutRepository.getById(id) == null) {
            throw new ResourceNotFoundException("Workout not found");
        }

        workoutRepository.update(id, workout);
    }


    // DELETE
    public void deleteWorkout(int id)
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        if (id <= 0) {
            throw new InvalidInputException("Invalid workout id");
        }

        if (workoutRepository.getById(id) == null) {
            throw new ResourceNotFoundException("Workout not found");
        }

        workoutRepository.delete(id);
    }
}
