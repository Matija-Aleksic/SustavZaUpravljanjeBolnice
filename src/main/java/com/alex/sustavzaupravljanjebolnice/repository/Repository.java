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
    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws SQLException the sql exception
     */
    T getById(ID id) throws SQLException;

    /**
     * Gets all.
     *
     * @return the all
     * @throws SQLException the sql exception
     */
    List<T> getAll() throws SQLException;

    /**
     * Save.
     *
     * @param entity the entity
     * @throws SQLException the sql exception
     */
    void save(T entity) throws SQLException;

    /**
     * Update.
     *
     * @param entity the entity
     * @throws SQLException the sql exception
     */
    void update(T entity) throws SQLException;

    /**
     * Delete by id.
     *
     * @param id the id
     * @throws SQLException the sql exception
     */
    void deleteById(ID id) throws SQLException;
}