package com.clouway.tripagency.core;

/**
 * Created by borislav on 24.08.16.
 */
public interface TripRepository {

    void insertTrip(Long egn, String arrival, String department, String city);
    void updateTrip(Long egn, String arrival, String department, String city);
    void getTripContent();
    void getMostVisitedCities();
    void deleteTableContent();
    void deleteTable();

}
