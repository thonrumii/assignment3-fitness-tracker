package repository;

import exception.DatabaseOperationException;
import model.CardioWorkout;
import model.StrengthWorkout;
import model.Workout;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRepository {

    // CREATE
    public void create(Workout workout) throws DatabaseOperationException {
        String sql = "INSERT INTO workout (name, type, duration_minutes) VALUES (?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, workout.getName());
            ps.setString(2, workout.getWorkoutType());
            ps.setInt(3, workout.getDuration());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                workout.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error creating workout", e);
        }
    }

    // GET ALL
    public List<Workout> getAll() throws DatabaseOperationException {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workout";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                workouts.add(mapRowToWorkout(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching workouts", e);
        }
        return workouts;
    }

    // GET BY ID
    public Workout getById(int id) throws DatabaseOperationException {
        String sql = "SELECT * FROM workout WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToWorkout(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch workout with id " + id, e);
        }
        return null;
    }

    // UPDATE
    public void update(int id, Workout workout) throws DatabaseOperationException {
        String sql = "UPDATE workout SET name = ?, duration_minutes = ? WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, workout.getName());
            ps.setInt(2, workout.getDuration());
            ps.setInt(3, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error updating workout", e);
        }
    }

    // DELETE
    public void delete(int id) throws DatabaseOperationException {
        String sql = "DELETE FROM workout WHERE id = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting workout", e);
        }
    }

    // Helper method
    private Workout mapRowToWorkout(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String type = rs.getString("type");
        int duration = rs.getInt("duration_minutes");

        if ("CARDIO".equalsIgnoreCase(type)) {
            return new CardioWorkout(id, name, duration);
        } else {
            return new StrengthWorkout(id, name, duration);
        }
    }
}
