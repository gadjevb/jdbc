package com.clouway.tripagency.core;

/**
 * basic CRUD ops
 * Created by borislav on 24.08.16.
 */
public interface PeopleRepository {
    /**
     *
     * @param person
     */
    Integer register (Person person);

    void update(UID id, Person newPerson);






    void insertPerson(String name, Long egn, Integer age, String email);
    void updatePerson(String name, Long egn, Integer age, String email);
    void getPeopleContent();
    void getPeopleByFirstLetters(String letters);

    void getPeopleInTheSameCity(String date, String city);

//    void deleteTableContent();

}
