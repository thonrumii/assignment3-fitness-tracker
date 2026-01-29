**A. Project Overview (SOLID principles documentation)**

The Fitness Tracker API is a console-based Java application that allows users to manage workouts and exercises.
It demonstrates advanced Object-Oriented Programming (OOP) principles combined with JDBC database interaction using MySQL.

This project follows the SOLID design principles to ensure maintainability, extensibility, and clean architecture. Each principle is applied as follows:

- Single Responsibility Principle
  - Model layer (represent domain entities only)
  - Repository layer (handle database access only (CRUD, SQL, JDBC))
  - Service layer (contain business logic and validation)
  - Controller layer (handles user input and delegates action to services)
  - Utility classes (provide reusable helper functionality)
- Open-Closed Principle
  - The system is open for extension but closed for modification.
  - `Workout` is an abstract base class
  - New workout types can be added by : extending `Workout`; overriding abstract methods
  - No existing service, repository, or controller code needs to be changed
- Liskov Substitution Principle
  - All subclasses of `Workout` can be used interchangeably via the base type.
  - Subclasses respect the contracts of the base class
  - This guarantees correct behavior when objects are accessed through base class references
- Interface Segregation Principle
  - The project uses small, focused interfaces instead of large ones:
    - IValidatable - validation behavior only
    - ITrackable - tracking behavior only
    - CrudRepository<T> - basic CRUD operations 
    - WorkoutQueries, WorkoutLookup, ExerciseQueries - specialized queries

    - IWorkoutService, IExerciseService - service contracts

  - Classes implement only the interfaces they need, avoiding unnecessary dependencies.
- Dependency Inversion Principle
  - High-level modules depend on abstractions, not concrete implementations
  - Dependencies are injected via constructors, making the system: easier to test; easier to extend; loosely coupled

**B. Advanced OOP Features**

**Generics**:

* Generics are used in the repository layer through the interface:
* `CrudRepository<T>` defines generic CRUD methods (create, getAll, getById, update, delete) that can work with any entity type.
* Implementations:
  - WorkoutRepository implements `CrudRepository<Workout>`
  - ExerciseRepository implements `CrudRepository<Exercise>`
* This removes duplication and makes repositories consistent for different entities.

**Lambdas**:
- Lambdas are used for sorting logic
- This demonstrates functional style and cleaner comparator logic
- The controller shows sorted output through the service method:
  - `getAllWorkoutsSortedByDuration()` - returns sorted list.

**Reflection**:
- Reflection is implemented in: `ReflectionUtils.printClassInfo(Object obj)`
- It demonstrates runtime inspection: prints class name; lists declared fields; lists declared methods
- This is demonstrated through the menu option: “Demo: Polymorphism + Interfaces” (Reflection output).

**Interface default/static methods**:
- The interface `ITrackable` includes:
  - `default String getTrackingInfoPretty()` (default method)
  - `static void printTrackingHeader()` (static method)
- Demonstration in controller:
  - `ITrackable.printTrackingHeader()`
  - `w1.getTrackingInfoPretty()`

**C. OOP Documentation**

**Abstract class + subclasses**:

Abstract class - `Workout`.

Common fields: id, name, duration, List<Exercise> exercises

Abstract methods:
- `calculateCalories()`
- `getWorkoutType()`

Concrete methods:
- `addExercise()`
- `WorkoutInfo()`

Subclasses:
- CardioWorkout extends Workout
- StrengthWorkout extends Workout

They override the abstract methods and provide type-specific calorie calculations.

**Composition relationship**:

The project uses composition/aggregation:
- Workout -> Exercise
- In OOP: Workout contains List<Exercise>
- In DB: exercises.workout_id is a foreign key referencing workout.id

**Polymorphism examples**:

Polymorphism is demonstrated by using base references:
```
Workout w1 = new CardioWorkout("Demo", 20);

Workout w2 = new StrengthWorkout("Demo", 30);

System.out.println(w1.calculateCalories());

System.out.println(w2.calculateCalories());
```
The correct overridden method executes depending on the runtime object type.

**UML Diagram**: can be accessed at `docs/uml1.png` and `docs/uml2.png` .

**D. Database Section**

**Schema**:

Database: fitness_tracker

Tables:
- workout(id, name, type, duration_minutes)
- exercises(id, workout_id, name, sets, reps)

**Constraints**:
- workout.name is UNIQUE NOT NULL
- exercises.workout_id is a foreign key referencing workout(id)
- ON DELETE CASCADE ensures deleting a workout deletes its exercises automatically

**Sample Inserts**:
```
INSERT INTO workout (name, type, duration_minutes)
VALUES ('Morning Run', 'CARDIO', 30);

INSERT INTO workout (name, type, duration_minutes)
VALUES ('Upper Body Power', 'STRENGTH', 45);

INSERT INTO exercises (workout_id, name, sets, reps)
VALUES (2, 'Bench Press', 3, 10);

INSERT INTO exercises (workout_id, name, sets, reps)
VALUES (2, 'Bicep Curls', 3, 12);
```

**E. Architecture Explanation**

This project uses strict layered architecture:

**Controller layer (Main)**:
- Handles menu + user input
- No validation logic
- Delegates all actions to service interfaces:
  - IWorkoutService
  - IExerciseService

**Service layer**:
- Contains validation + business rules
- Throws custom exceptions:
  - InvalidInputException
  - DuplicateResourceException 
  - ResourceNotFoundException 
  - DatabaseOperationException
- Depends only on repository interfaces (DIP)

**Repository layer**:
- Implements JDBC operations (SQL, PreparedStatement)
- Implements generic CRUD (CrudRepository<T>)
- Contains no business rules, only data access

**Example request/response flow**:

Example: Create workout
1. Controller reads input → creates Workout object
2. Calls workoutService.createWorkout(workout)
3. Service validates input + checks duplicates
4. Repository inserts into DB
5. Controller prints success/error message

**F. Execution Instruction**

**Requirements**:
- Java: JDK 17 (or whichever your course requires)
- MySQL: MySQL Server 8+
- MySQL JDBC driver added to project dependencies (IntelliJ)

**Database setup**:
- Open MySQL Workbench (or CLI)
- Run schema.sql
- Ensure DB name: fitness_tracker

**Configure DB connection**:
- In DatabaseConnection.java, update:
- URL
- USER
- PASSWORD

**Run**:

In IntelliJ:
- Run controller.Main

**G. Screenshots**

Screenshots of program's functionality can be seen here: `docs/screenshots`.

Screenshots demonstrate interface, successful CRUD operations, and validation error.

H. Reflection Section

I've learned a lot about database connection and implementing my knowledge of abstraction, inheritance, and overloading concepts in real project. This project gave me an opportunity to learn how to structure Java projects using layered architecture, applying SOLID principles.

This experience helped me deepen my understanding of JDBC, CRUD operations, handling errors using custom exception hierarchy. I also learned about how interfaces improve maintainability and reduce coupling.

The main challenge I've faced during this project was the volume of work that had to be done: I would often get confused during managing validation and business rules across the layers. 
Moreover, I had some issues with configuring JDBC drivers and debugging database connection issues due to lack of experience with working with databases.
When refactoring my project for assignment 4, I faced challenges during refactoring from direct repository usage to interface-based DIP. 

The value of SOLID principles - they improve readability, extensibility, and maintainability.

Nevertheless, this small project helped me identify benefits of JDBC and multi-layer design. First of all, it helped with logical division/separation of concerns. Secondly, it provided improved maintainability and scalability.
