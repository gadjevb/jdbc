package com.clouway.tripagency.core;

import java.sql.Date;
import java.util.List;

/**
 * This {@code TripManagerRepository} interface provides a methods
 * to be implemented for work with the People and Trip tables in the
 * Trip_Agency database.
 *
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface TripManagerRepository {

    /**
     * Returns a list of {@code Person} person objects from the records that
     * are said to be in the same city at the same time
     *
     * @param date used by the query to match the records
     * @param city used by the query to match the records
     * @return List of {@code Person} person objects
     */
    List getPeopleInTheSameCity(Date date, String city);
}
