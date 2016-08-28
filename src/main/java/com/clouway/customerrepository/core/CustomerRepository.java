package com.clouway.customerrepository.core;

import java.util.List;

/**
 * This {@code CustomerRepository} interface provides the methods
 * to be implemented for work with the Customer and
 * Customer_history tables in the Customer_Repository database.
 *
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface CustomerRepository {

    /**
     * Registers a single record in the table
     *
     * @param customer contains the information for a single record
     */
    void register(Customer customer);

    /**
     * Updates a single record in the table
     *
     * @param id index to be matched in the query
     * @param customer contains the information for the update
     */
    void update(Integer id, Customer customer);

    /**
     * Returns the number of records in the Customer table
     *
     * @return Integer
     */
    Integer getNumberOfRecords();

    /**
     * Puts each record in customer object and adds it to the list
     *
     * @param page the number of the page
     * @return List of customer object
     */
    List getHistoricalRecords(Integer page);

    /**
     * Truncates all tables
     */
    void truncate();

    /**
     * Closes the connections to the database
     */
    void close();

}
