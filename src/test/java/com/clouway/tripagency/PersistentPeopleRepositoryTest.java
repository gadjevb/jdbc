package com.clouway.tripagency;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentPeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
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
    private Person person1 = new Person("Jon", new UID(9203174579l), 24, "person1@gmail.com");
    private Person person2 = new Person("Bob",new UID(9807236424l),18,"person2@gmail.com");

    @Before
    public void setUp() throws Exception {
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
        peopleRepository.register(person1);
        List<Person> person = peopleRepository.getAll();

        assertTrue(person.get(0).equals(person1));
    }

    @Test
    public void update(){
        peopleRepository.register(person1);
        List<Person> person = peopleRepository.getAll();
        peopleRepository.update(new UID(9203174579l), new Person("Jon Doe", new UID(9203174579l), 24, "person1.doe@gmail.com"));
        List<Person> updatedPerson = peopleRepository.getAll();

        assertTrue(person.get(0).equals(person1));
        assertTrue(updatedPerson.get(0).equals(new Person("Jon Doe", new UID(9203174579l), 24, "person1.doe@gmail.com")));
    }

    @Test
    public void getPeopleData(){
        peopleRepository.register(person1);
        peopleRepository.register(person2);
        List<Person> actual = peopleRepository.getAll();

        ArrayList<Person> expected = Lists.newArrayList(person1, person2);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void getPeopleByGivenLetters(){
        peopleRepository.register(person1);
        peopleRepository.register(person2);

        peopleRepository.register(new Person("Sam",new UID(9612128833l), 20,"sam@gmail.com"));
        peopleRepository.register(new Person("Ben",new UID(9507236424l), 21,"ben@gmail.com"));
        List<Person> people = peopleRepository.getPeopleByFirstLetters("B");

        assertThat(people, is(equalTo(Lists.newArrayList(person2, new Person("Ben",new UID(9507236424l), 21,"ben@gmail.com")))));
    }
}
