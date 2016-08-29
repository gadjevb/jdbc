package com.clouway.tripagency;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentPeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentPeopleRepositoryTest {

    private ConnectionProvider provider = new ConnectionProvider("task2","postgres","123");
    private PersistentPeopleRepository peopleRepository = new PersistentPeopleRepository(provider);

    void truncate(){
        try {
            Connection connection = provider.get();
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE PEOPLE, TRIP;");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void happyPath(){
        truncate();
        peopleRepository.register(new Person("Jon", new UID(9203174579l), (byte)24, "jon@gmail.com"));
        List<Person> person = peopleRepository.getPeopleData();
        peopleRepository.update(new UID(9203174579l), new Person("Jon Doe", new UID(9203174579l), (byte)24, "jon.doe@gmail.com"));
        List<Person> updatedPerson = peopleRepository.getPeopleData();

        assertTrue(person.get(0).equals(new Person("Jon", new UID(9203174579l), (byte)24, "jon@gmail.com")));
        assertTrue(updatedPerson.get(0).equals(new Person("Jon Doe", new UID(9203174579l), (byte)24, "jon.doe@gmail.com")));
    }

    @Test
    public void peopleAndTripData(){
        truncate();
        peopleRepository.register(new Person("Jon",new UID(9712128833l),(byte)19,"jon@gmail.com"));
        peopleRepository.register(new Person("Bob",new UID(9807236424l),(byte)18,"bob@gmail.com"));
        List<Person> people = peopleRepository.getPeopleData();

        assertThat(people, is(equalTo(Lists.newArrayList(new Person("Jon",new UID(9712128833l),(byte)19,"jon@gmail.com"), new Person("Bob",new UID(9807236424l),(byte)18,"bob@gmail.com")))));
    }

    @Test
    public void byGivenLetters(){
        truncate();
        peopleRepository.register(new Person("Jon",new UID(9712128833l),(byte)19,"jon@gmail.com"));
        peopleRepository.register(new Person("Bob",new UID(9807236424l),(byte)18,"bob@gmail.com"));
        peopleRepository.register(new Person("Sam",new UID(9612128833l),(byte)20,"sam@gmail.com"));
        peopleRepository.register(new Person("Ben",new UID(9507236424l),(byte)21,"ben@gmail.com"));
        List<Person> people = peopleRepository.getPeopleByFirstLetters("B");

        assertThat(people, is(equalTo(Lists.newArrayList(new Person("Bob",new UID(9807236424l),(byte)18,"bob@gmail.com"), new Person("Ben",new UID(9507236424l),(byte)21,"ben@gmail.com")))));
    }
}
