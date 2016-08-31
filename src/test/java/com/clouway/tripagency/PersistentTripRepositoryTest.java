package com.clouway.tripagency;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentPeopleRepository;
import com.clouway.tripagency.adapter.jdbc.PersistentTripRepository;
import com.clouway.tripagency.core.City;
import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;
import com.google.common.collect.Lists;
import org.junit.Before;
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
public class PersistentTripRepositoryTest {

    private ConnectionProvider provider = new ConnectionProvider("task2","postgres","123");
    private PersistentPeopleRepository peopleRepository = new PersistentPeopleRepository(provider);
    private PersistentTripRepository tripRepository = new PersistentTripRepository(provider);
    private Person person = new Person("Jon",new UID(9308128484l), 23,"person@gmail.com");

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
        peopleRepository.register(person);
        tripRepository.register(new Trip(new UID(9308128484l), getDate(2016,8,15), getDate(2016,8,20), "Burgas"));
        List<Trip> actual = tripRepository.getAll();
        Trip expected = new Trip(new UID(9308128484l), getDate(2016,8,15), getDate(2016,8,20), "Burgas");


        assertTrue(actual.get(0).equals(expected));
    }

    @Test
    public void update(){
        peopleRepository.register(person);
        tripRepository.register(new Trip(new UID(9308128484l), getDate(2016,8,15), getDate(2016,8,20), "Burgas"));
        List<Trip> trip = tripRepository.getAll();
        tripRepository.update(new UID(9308128484l) ,new Trip(new UID(9308128484l), getDate(2016,8,15), getDate(2016,8,20), "Turnovo"));
        List<Trip> updatedTrip = tripRepository.getAll();

        assertTrue(trip.get(0).equals(new Trip(new UID(9308128484l), getDate(2016,8,15), getDate(2016,8,20), "Burgas")));
        assertTrue(updatedTrip.get(0).equals(new Trip(new UID(9308128484l), getDate(2016,8,15), getDate(2016,8,20), "Turnovo")));
    }

    @Test
    public void mostVisitedCities(){
        peopleRepository.register(person);
        tripRepository.register(new Trip(new UID(9308128484l), getDate(2016,6,6), getDate(2016,6,16),"Varna"));
        tripRepository.register(new Trip(new UID(9308128484l), getDate(2016,6,6), getDate(2016,6,16),"Varna"));
        tripRepository.register(new Trip(new UID(9308128484l), getDate(2016,6,6), getDate(2016,6,16),"Turnovo"));
        tripRepository.register(new Trip(new UID(9308128484l), getDate(2016,6,6), getDate(2016,6,16),"Turnovo"));
        tripRepository.register(new Trip(new UID(9308128484l), getDate(2016,6,6), getDate(2016,6,16),"Turnovo"));
        tripRepository.register(new Trip(new UID(9308128484l), getDate(2016,6,6), getDate(2016,6,16),"Vidin"));
        List<City> actual = tripRepository.getMostVisited();
        List<City> expected = Lists.newArrayList(new City("Turnovo"), new City("Varna"), new City("Vidin"));

        assertThat(actual, is(equalTo(expected)));
    }

    private Date getDate(Integer year, Integer month, Integer day){
        return new Date(year,month,day);
    }
}
