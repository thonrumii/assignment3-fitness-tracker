package controller;

import exception.*;
import model.*;
import service.ExerciseService;
import service.WorkoutService;
import java.util.List;
import java.util.Scanner;
import utils.ReflectionUtils;
import repository.ExerciseRepository;
import repository.WorkoutRepository;
import service.IWorkoutService;
import service.IExerciseService;

public class Main {

    private static final WorkoutRepository workoutRepo = new WorkoutRepository();
    private static final ExerciseRepository exerciseRepo = new ExerciseRepository();

    private static final IWorkoutService workoutService = new WorkoutService(workoutRepo, workoutRepo, workoutRepo);
    private static final IExerciseService exerciseService = new ExerciseService(exerciseRepo, workoutRepo, exerciseRepo);

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            try {
                System.out.println("\n===== FITNESS TRACKER MENU =====");
                System.out.println("1. Create Cardio Workout");
                System.out.println("2. Create Strength Workout");
                System.out.println("3. View All Workouts");
                System.out.println("4. View Workout By ID");
                System.out.println("5. Update Workout");
                System.out.println("6. Delete Workout");
                System.out.println("7. Add Exercise to Workout");
                System.out.println("8. View Exercises of Workout");
                System.out.println("9. Delete Exercise");
                System.out.println("10. Update Exercise");
                System.out.println("11. View Exercise By ID");
                System.out.println("12. Demo: Reflection + Interfaces default/static");
                System.out.println("13. Shortest Cardio and Strength workouts");
                System.out.println("14. View All Workouts sorted by duration");
                System.out.println("0. Exit");

                System.out.print("Choose option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> createWorkout(true);
                    case 2 -> createWorkout(false);
                    case 3 -> viewAllWorkouts();
                    case 4 -> viewWorkoutById();
                    case 5 -> updateWorkout();
                    case 6 -> deleteWorkout();
                    case 7 -> addExercise();
                    case 8 -> viewExercises();
                    case 9 -> deleteExercise();
                    case 10 -> updateExercise();
                    case 11 -> viewExerciseById();
                    case 12 -> demoOopRequirements();
                    case 13 -> viewShortestAll();
                    case 14 -> viewAllWorkoutsSorted();

                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option!");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // CREATE WORKOUT
    private static void createWorkout(boolean isCardio)
            throws InvalidInputException, DatabaseOperationException {

        System.out.print("Enter workout name: ");
        String name = scanner.nextLine();

        System.out.print("Enter duration (minutes): ");
        int duration = Integer.parseInt(scanner.nextLine());

        Workout workout;

        if (isCardio) {
            workout = new CardioWorkout(name, duration);
        } else {
            workout = new StrengthWorkout(name, duration);
        }

        workoutService.createWorkout(workout);
        System.out.println("Workout created successfully!");
    }

    // VIEW ALL WORKOUTS
    private static void viewAllWorkouts() throws DatabaseOperationException {
        List<Workout> workouts = workoutService.getAllWorkouts();
        for (Workout w : workouts) {
            System.out.println(
                    w.getId() + " | " +
                            w.getName() + " | " +
                            w.getWorkoutType() + " | " +
                            w.getDuration() + " min"
            );
        }
    }

    // VIEW ALL WORKOUTS SORTED
    private static void viewAllWorkoutsSorted() throws DatabaseOperationException {
        List<Workout> workouts = workoutService.getAllWorkoutsSortedByDuration();
        for (Workout w : workouts) {
            System.out.println(
                    w.getId() + " | " +
                            w.getName() + " | " +
                            w.getWorkoutType() + " | " +
                            w.getDuration() + " min"
            );
        }
    }

    private static void viewShortestAll() throws DatabaseOperationException {
        Workout w1 = workoutService.getShortestS();
        Workout w2 = workoutService.getShortestC();
        System.out.println(
                w1.getId() + " | " +
                        w1.getName() + " | " +
                        w1.getWorkoutType() + " | " +
                        w1.getDuration() + " min"
        );
        System.out.println(
                w2.getId() + " | " +
                        w2.getName() + " | " +
                        w2.getWorkoutType() + " | " +
                        w2.getDuration() + " min"
        );

    }

    // VIEW BY WORKOUT ID
    private static void viewWorkoutById()
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        System.out.print("Enter workout id: ");
        int id = Integer.parseInt(scanner.nextLine());

        Workout w = workoutService.getWorkoutById(id);

        System.out.println("ID: " + w.getId());
        System.out.println("Name: " + w.getName());
        System.out.println("Type: " + w.getWorkoutType());
        System.out.println("Duration: " + w.getDuration());
        System.out.println("Calories: " + w.calculateCalories());
    }

    //VIEW BY EXERCISE ID
    private static void viewExerciseById()
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        System.out.print("Enter exercise id: ");
        int id = Integer.parseInt(scanner.nextLine());

        Exercise e = exerciseService.getExerciseById(id);

        System.out.println("ID: " + e.getId());
        System.out.println("Workout ID: " + e.getWorkoutId());
        System.out.println("Name: " + e.getName());
        System.out.println("Sets: " + e.getSets());
        System.out.println("Reps: " + e.getReps());
    }


    // UPDATE WORKOUT
    private static void updateWorkout()
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        System.out.print("Enter workout id to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("New name: ");
        String name = scanner.nextLine();

        System.out.print("New duration: ");
        int duration = Integer.parseInt(scanner.nextLine());

        Workout existing = workoutService.getWorkoutById(id);

        existing.setName(name);
        existing.setDuration(duration);

        workoutService.updateWorkout(id, existing);

        System.out.println("Workout updated!");
    }

    //UPDATE EXERCISE
    private static void updateExercise()
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        System.out.print("Enter exercise id to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("New exercise name: ");
        String name = scanner.nextLine();

        System.out.print("New sets: ");
        int sets = Integer.parseInt(scanner.nextLine());

        System.out.print("New reps: ");
        int reps = Integer.parseInt(scanner.nextLine());

        Exercise existing = exerciseService.getExerciseById(id);

        Exercise updated = new Exercise(existing.getWorkoutId(), name, sets, reps);
        exerciseService.updateExercise(id, updated);

        System.out.println("Exercise updated!");
    }

    // DELETE WORKOUT
    private static void deleteWorkout()
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        System.out.print("Enter workout id to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        workoutService.deleteWorkout(id);

        System.out.println("Workout deleted!");
    }

    // ADD EXERCISE
    private static void addExercise()
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        System.out.print("Enter workout id: ");
        int workoutId = Integer.parseInt(scanner.nextLine());

        System.out.print("Exercise name: ");
        String name = scanner.nextLine();

        System.out.print("Sets: ");
        int sets = Integer.parseInt(scanner.nextLine());

        System.out.print("Reps: ");
        int reps = Integer.parseInt(scanner.nextLine());

        Exercise exercise = new Exercise(workoutId, name, sets, reps);

        exerciseService.addExercise(workoutId, exercise);

        System.out.println("Exercise added!");
    }

    // VIEW EXERCISES
    private static void viewExercises()
            throws InvalidInputException, DatabaseOperationException {

        System.out.print("Enter workout id: ");
        int workoutId = Integer.parseInt(scanner.nextLine());

        List<Exercise> exercises =
                exerciseService.getExercisesByWorkout(workoutId);

        if (exercises.isEmpty()) {
            System.out.println("No exercises found for this workout.");
            return;
        }

        for (Exercise e : exercises) {
            System.out.println(
                    e.getId() + " | " +
                            e.getName() + " | " +
                            e.getSets() + "x" +
                            e.getReps()
            );
        }
    }

    // DELETE EXERCISE
    private static void deleteExercise()
            throws InvalidInputException, ResourceNotFoundException, DatabaseOperationException {

        System.out.print("Enter exercise id to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        exerciseService.deleteExercise(id);

        System.out.println("Exercise deleted!");
    }

    //REFLECTION
    private static void demoOopRequirements() {
        Workout w1 = new CardioWorkout("Demo Cardio", 20);

        ITrackable.printTrackingHeader();   // static method
        System.out.println(w1.getTrackingInfoPretty()); // default method
        System.out.println("\n--- Reflection Demo ---");
        ReflectionUtils.printClassInfo(w1);
    }
}
