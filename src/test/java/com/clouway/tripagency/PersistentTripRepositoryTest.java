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
    private Person jon = new Person("Jon",new UID(9308128484l), (byte)23,"jon@gmail.com");

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
        peopleRepository.register(jon);
        tripRepository.register(new Trip(new UID(9308128484l), new Date(2016,8,15), new Date(2016,8,20), "Burgas"));
        List<Trip> trip = tripRepository.getTripData();
        tripRepository.update(new UID(9308128484l) ,new Trip(new UID(9308128484l), new Date(2016,8,15), new Date(2016,8,20), "Turnovo"));
        List<Trip> updatedTrip = tripRepository.getTripData();

        assertTrue(trip.get(0).equals(new Trip(new UID(9308128484l), new Date(2016,8,15), new Date(2016,8,20), "Burgas")));
        assertTrue(updatedTrip.get(0).equals(new Trip(new UID(9308128484l), new Date(2016,8,15), new Date(2016,8,20), "Turnovo")));
    }

    @Test
    public void mostVisitedCities(){
        peopleRepository.register(jon);
        tripRepository.register(new Trip(new UID(9308128484l), new Date(2016,6,6), new Date(2016,6,16),"Vidin"));
        tripRepository.register(new Trip(new UID(9308128484l), new Date(2016,6,6), new Date(2016,6,16),"Varna"));
        tripRepository.register(new Trip(new UID(9308128484l), new Date(2016,6,6), new Date(2016,6,16),"Varna"));
        tripRepository.register(new Trip(new UID(9308128484l), new Date(2016,6,6), new Date(2016,6,16),"Turnovo"));
        tripRepository.register(new Trip(new UID(9308128484l), new Date(2016,6,6), new Date(2016,6,16),"Turnovo"));
        tripRepository.register(new Trip(new UID(9308128484l), new Date(2016,6,6), new Date(2016,6,16),"Turnovo"));
        List<City> cities = tripRepository.getMostVisited();

        assertThat(cities, is(equalTo(Lists.newArrayList(new City("Turnovo"), new City("Varna"), new City("Vidin")))));
    }
}
