package com.clouway.tripagency.core;

/**
 * This {@code TripRepository} interface provides the methods
 * to be implemented for work with the Trip table in the
 * Trip_Agency database
 *
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface TripRepository {

    /**
     * Inserts a unit of data in the Trip table, uses an object of the {@code Destination} Destination class
     * and returns value of the {@code Long} Long class
     *
     * @param destination object contains the information needed for a unit of data in Trip table
     * @return the EGN(Foreign key) for the unit of data used to connect with People table
     */
    Long register(Destination destination);

    /**
     * Updates a single unit of data with the values contained in {@code Destination} destination object
     * whose primary key matches the {@code Long} value that is in the {@code UID} id object
     *
     * @param id contains the value used by the query for matching
     * @param destination contains the values used for the update of the data unit
     */
    void update(UID id, Destination destination);

    /**
     * Closes the connections used by the repository
     */
    void close();

}
