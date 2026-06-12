package com.alex.sustavzaupravljanjebolnice.repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic interface for CRUD operations.
 *
 * @param <T>  The entity type (e.g., Doctor, Patient)
 * @param <ID> The primary key type (e.g., Long, String)
 */
public interface Repository<T, ID> {
    T getById(ID id) throws SQLException;

    List<T> getAll() throws SQLException;

    void save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void deleteById(ID id) throws SQLException;
}