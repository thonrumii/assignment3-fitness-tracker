
package repository;

import exception.DatabaseOperationException;
import model.Exercise;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseRepository {

    // CREATE
    public void create(int workoutId, Exercise exercise) throws DatabaseOperationException {
        String sql = "INSERT INTO exercises (workout_id, name, sets, reps) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, workoutId);
            ps.setString(2, exercise.getName());
            ps.setInt(3, exercise.getSets());
            ps.setInt(4, exercise.getReps());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                exercise.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating exercise", e);
        }
    }

    // GET ALL
    public List<Exercise> getAll() throws DatabaseOperationException {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM exercises";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {

                Exercise e = new Exercise(
                        rs.getInt("workout_id"),
                        rs.getString("name"),
                        rs.getInt("sets"),
                        rs.getInt("reps")
                );

                e.setId(rs.getInt("id"));

                exercises.add(e);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch all exercises", e);
        }

        return exercises;
    }

    // GET BY ID
    public Exercise getById(int id) throws DatabaseOperationException {
        String sql = "SELECT * FROM exercises WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Exercise e = new Exercise(
                        rs.getInt("workout_id"),
                        rs.getString("name"),
                        rs.getInt("sets"),
                        rs.getInt("reps")
                );

                e.setId(rs.getInt("id"));

                return e;
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch exercise with id " + id, e);
        }

        return null;
    }

    // GET BY WORKOUT ID
    public List<Exercise> getByWorkoutId(int workoutId) throws DatabaseOperationException {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM exercises WHERE workout_id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, workoutId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Exercise e = new Exercise(
                        rs.getInt("workout_id"),
                        rs.getString("name"),
                        rs.getInt("sets"),
                        rs.getInt("reps")
                );

                e.setId(rs.getInt("id"));

                exercises.add(e);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch exercises", e);
        }

        return exercises;
    }

    // UPDATE
    public void update(int id, Exercise exercise) throws DatabaseOperationException {
        String sql = "UPDATE exercises SET name = ?, sets = ?, reps = ? WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, exercise.getName());
            ps.setInt(2, exercise.getSets());
            ps.setInt(3, exercise.getReps());
            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update exercise", e);
        }
    }

    // DELETE
    public void delete(int id) throws DatabaseOperationException {
        String sql = "DELETE FROM exercises WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete exercise", e);
        }
    }
}
