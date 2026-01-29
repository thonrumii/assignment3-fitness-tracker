package repository;

import exception.DatabaseOperationException;
import java.util.List;

public interface CrudRepository<T> {
    void create(T entity) throws DatabaseOperationException;
    List<T> getAll() throws DatabaseOperationException;
    T getById(int id) throws DatabaseOperationException;
    void update(int id, T entity) throws DatabaseOperationException;
    void delete(int id) throws DatabaseOperationException;
}