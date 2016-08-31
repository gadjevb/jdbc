package com.clouway.tripagency;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentPeopleRepository;
import com.clouway.tripagency.adapter.jdbc.PersistentTripManagerRepository;
import com.clouway.tripagency.adapter.jdbc.PersistentTripRepository;
import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentTripManagerRepositoryTest {

    private ConnectionProvider provider = new ConnectionProvider("task2","postgres","123");
    private PersistentTripManagerRepository tripManager = new PersistentTripManagerRepository(provider);
    private PersistentPeopleRepository peopleRepository = new PersistentPeopleRepository(provider);
    private PersistentTripRepository tripRepository = new PersistentTripRepository(provider);

    @Before
    public void setUp() throws Exception {
        try {
            Connection connection = provider.get();
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE PEOPLE, TRIP;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void peopleInSameCityAtTheSameTime(){
        Person person1 = new Person("Jon", new UID(9712128833l), 19, "jon@gmail.com");
        Person person2 = new Person("Bob", new UID(9807236424l), 18, "bob@gmail.com");
        Person person3 = new Person("Sam", new UID(9612128833l), 20, "sam@gmail.com");
        Trip trip1 = new Trip(new UID(9712128833l), getDate(2016,6,6), getDate(2016,6,16),"Turnovo");
        Trip trip2 = new Trip(new UID(9807236424l), getDate(2016,6,3), getDate(2016,6,10),"Turnovo");
        Trip trip3 = new Trip(new UID(9612128833l), getDate(2016,6,28), getDate(2016,7,8),"Turnovo");

        peopleRepository.register(person1);
        peopleRepository.register(person2);
        peopleRepository.register(person3);
        tripRepository.register(trip1);
        tripRepository.register(trip2);
        tripRepository.register(trip3);
        List<Person> people = tripManager.getPeopleInTheSameCity(getDate(2016,6,9),"Turnovo");

        assertThat(people, is(equalTo(Lists.newArrayList(person2, person1))));
    }

    private Date getDate(Integer year, Integer month, Integer day){
        return new Date(year,month,day);
    }
}
