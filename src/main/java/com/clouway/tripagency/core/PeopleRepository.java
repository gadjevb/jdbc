package com.clouway.tripagency.core;

/**
 * Created by borislav on 24.08.16.
 */
public interface PeopleRepository {

    void insertPerson(String name, Long egn, Integer age, String email);
    void updatePerson(String name, Long egn, Integer age, String email);
    void getPeopleContent();
    void getPeopleByFirstLetters(String letters);
    void getPeopleInTheSameCity(String date, String city);
    void deleteTableContent();
    void deleteTable();

}
