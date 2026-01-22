CREATE DATABASE IF NOT EXISTS fitness_tracker;
USE fitness_tracker;

CREATE TABLE IF NOT EXISTS workout (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL,
    duration_minutes INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS exercises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    workout_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    sets INT DEFAULT 0,
    reps INT DEFAULT 0,
    FOREIGN KEY (workout_id) REFERENCES workout(id) ON DELETE CASCADE
    );

INSERT INTO workout (name, type, duration_minutes)
VALUES ('Morning Run', 'CARDIO', 30);

INSERT INTO workout (name, type, duration_minutes)
VALUES ('Upper Body Power', 'STRENGTH', 45);

INSERT INTO exercises (workout_id, name, sets, reps)
VALUES (2, 'Bench Press', 3, 10);

INSERT INTO exercises (workout_id, name, sets, reps)
VALUES (2, 'Bicep Curls', 3, 12);


