package repository;

import exception.DatabaseOperationException;
import model.CardioWorkout;
import model.StrengthWorkout;
import model.Workout;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRepository implements CrudRepository<Workout>, WorkoutQueries, WorkoutLookup {

    // CREATE
    @Override
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
    @Override
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

    @Override
    public Workout getShortestC() throws DatabaseOperationException {
        String sql = "SELECT * FROM workout WHERE type='CARDIO' ORDER BY duration_minutes ASC LIMIT 1";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToWorkout(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch shortest workout", e);
        }
        return null;
    }

    @Override
    public Workout getShortestS() throws DatabaseOperationException {
        String sql = "SELECT * FROM workout WHERE type='STRENGTH' ORDER BY duration_minutes ASC LIMIT 1";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToWorkout(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch shortest workout", e);
        }
        return null;
    }
    // GET BY ID
    @Override
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
    @Override
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
    @Override
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

        if ("CARDIO".equalsIgnoreCase(type)) return new CardioWorkout(id, name, duration);
        if ("STRENGTH".equalsIgnoreCase(type)) return new StrengthWorkout(id, name, duration);
        throw new SQLException("Unknown workout type: " + type);
    }

    //DUPLICATES EXCEPTION
    @Override
    public boolean existsByName(String name) throws DatabaseOperationException {
        String sql = "SELECT 1 FROM workout WHERE name = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to check workout name", e);
        }
    }

    @Override
    public boolean existsByNameExceptId(String name, int excludeId) throws DatabaseOperationException {
        String sql = "SELECT 1 FROM workout WHERE name = ? AND id <> ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to check workout name (excluding id)", e);
        }
    }

}
