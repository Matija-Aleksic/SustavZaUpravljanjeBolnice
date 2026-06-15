package com.alex.sustavzaupravljanjebolnice.repository;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Repository.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 */
public interface Repository<T, I> {
    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     * @throws SQLException the sql exception
     */
    T getById(I id) throws SQLException;

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
    void deleteById(I id) throws SQLException;
}