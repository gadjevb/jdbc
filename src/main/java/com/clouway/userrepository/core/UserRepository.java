package com.clouway.userrepository.core;

import java.util.List;

/**
 * This {@code UserRepository} interface provides the methods
 * to be implemented for work with the Users, Contact
 * and Address table in the User_Repository database
 *
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface UserRepository {

    /**
     * Returns all records in the Users table
     *
     * @return List of {@code Users} objects
     */
    List getUsersContent();

    /**
     * Return all records in the Contact table
     *
     * @return List of {@code Contact} objects
     */
    List getContactContent();

    /**
     * Return all records in the Address table
     *
     * @return List of {@code Address} objects
     */
    List getAddressContent();
}
