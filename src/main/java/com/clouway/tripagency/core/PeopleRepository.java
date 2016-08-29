package com.clouway.tripagency.core;

import java.util.List;

/**
 * This {@code PeopleRepository} interface provides the methods
 * to be implemented for work with the People table in the
 * Trip_Agency database.
 *
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface PeopleRepository {
    /**
     * Inserts a unit of data in the People table, uses an object of the {@code Person} Person class
     * and returns value of the {@code Long} Long class
     *
     * @param person object contains the information needed for a unit of data in People table
     * @return the EGN(Primary key) for the unit of data
     */
    Long register(Person person);

    /**
     * Updates a single unit of data with the values contained in {@code Person} newPerson object
     * whose primary key matches the {@code Long} value that is in the {@code UID} id object
     *
     * @param id contains the value used by the query for matching
     * @param newPerson contains the values used for the update of the data unit
     */
    void update(UID id, Person newPerson);

    /**
     * Returns all records in the People table in the form of list of person objects
     *
     * @return List of {@code Person} person objects
     */
    List getPeopleData();

    /**
     * Returns all person records from the People table whose names start with the given letters
     *
     * @param letters used by the query to match the records
     * @return List of {@code Person} person objects
     */
    List getPeopleByFirstLetters(String letters);

}
