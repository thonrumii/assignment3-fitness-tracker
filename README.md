A. Project Overview
The Fitness Tracker API is a console-based Java application that allows users to manage workouts and exercises.
It demonstrates advanced Object-Oriented Programming (OOP) principles combined with JDBC database interaction using MySQL.

The system allows users to:

- Create different types of workouts (Cardio, Strength)

- Add exercises to workouts

- View, update, and delete workouts and exercises

- Calculate calories using polymorphism

- Validate input and handle errors using custom exceptions

Main Entities

- Workout (abstract)

  - CardioWorkout

  - StrengthWorkout

- Exercise

Relationships:

A Workout can have many Exercises (one-to-many composition)

Exercises reference workouts using a foreign key

B. OOP Design Documentation

Abstract class - `Workout`.

Common fields: id, name, duration

Abstract methods:

- calculateCalories()

- getWorkoutType()

Concrete methods:

- addExercise()

- validate()

- getTrackingInfo()

Inheritance: CardioWorkout extends Workout; StrengthWorkout extends Workout

Each subclass provides its own implementation of:

- calculateCalories()

- getWorkoutType()

Interfaces:

- IValidatable. Method: validate()

- ITrackable. Method: getTrackingInfo()

Both interfaces are implemented by Workout, ensuring validation and tracking logic are enforced consistently.

Composition / Aggregation:

Workout contains a list of Exercise objects

Implemented both in code and database (foreign key relationship)

Polymorphism:

Workouts are handled using the base Workout reference

Runtime method binding is demonstrated via:

- calculateCalories()

- getWorkoutType()

UML Diagram: can be accessed in `docs` folder.

C. Database Description

MySQL database named `fitness_tracker`. Schema and sample data can be viewed at resources/schema.sql.


D. Controller 

The controller layer (Main) exposes CRUD operations via a command-line interface.

Supported Operations
- Create workout (Cardio / Strength)
- View all workouts
- View workout by ID
- Update workout
- Delete workout
- Add exercise to the workout
- View exercises by workout
- Update exercise
- Delete exercise

E. Instructions to Compile and Run

Requirements for program to run:

- Java 17+
- MySQL server
- Intellij IDEA
- MySQL Connector

Steps to Run:

- Create the database using schema.sql
- Update credentials in DatabaseConnection.java
- Run the application:
- Run controller.Main

F. Screenshots

Screenshots of program's functionality can be seen here: `docs/screenshots`.

Screenshots demonstrate interface, successful CRUD operations, and validation error.

G. Reflection Section

I've learned a lot about database connection and implementing my knowledge of abstraction, inheritance, and overloading concepts in real project.

This experience helped me deepen my understanding of JDBC, CRUD operations, handling errors using custom exception hierarchy.

The main challenge I've faced during this project was the volume of work that had to be done: I would often get confused during managing validation and business rules across the layers. 
Moreover, I had some issues with configuring JDBC drivers and debugging database connection issues due to lack of experience with working with databases.

Nevertheless, this small project helped me identify benefits of JDBC and multi-layer design. First of all, it helped with logical division/separation of concerns. Secondly, it provided improved maintainability and scalability.
