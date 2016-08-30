package com.clouway.tripagency.core;

import java.util.List;

/**
 * This {@code TripRepository} interface provides the methods
 * to be implemented for work with the Trip table in the
 * Trip_Agency database
 *
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface TripRepository {

    /**
     * Inserts a unit of data in the Trip table, uses an object of the {@code Trip} Trip class
     * and returns value of the {@code Long} Long class
     *
     * @param trip object contains the information needed for a unit of data in Trip table
     * @return the EGN(Foreign key) for the unit of data used to connect with People table
     */
    Long register(Trip trip);

    /**
     * Updates a single unit of data with the values contained in {@code Trip} trip object
     * whose primary key matches the {@code Long} value that is in the {@code UID} id object
     *
     * @param id contains the value used by the query for matching
     * @param trip contains the values used for the update of the data unit
     */
    void update(UID id, Trip trip);

    /**
     * Returns all records in the Trip table in the form of list of trip objects
     *
     * @return List of {@code Trip} trip objects
     */
    List<Trip> getAll();

    /**
     * Returns the most visited cities from the Trip table in descending order
     *
     * @return List of {@code City} city objects
     */
    List<Trip> getMostVisited();

}
