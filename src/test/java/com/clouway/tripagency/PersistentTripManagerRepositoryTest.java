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
        peopleRepository.register(new Person("Jon",new UID(9712128833l),(byte)19,"jon@gmail.com"));
        peopleRepository.register(new Person("Bob",new UID(9807236424l),(byte)18,"bob@gmail.com"));
        peopleRepository.register(new Person("Sam",new UID(9612128833l),(byte)20,"sam@gmail.com"));
        tripRepository.register(new Trip(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Turnovo"));
        tripRepository.register(new Trip(new UID(9807236424l), new Date(2016,6,3), new Date(2016,6,10),"Turnovo"));
        tripRepository.register(new Trip(new UID(9612128833l), new Date(2016,6,28), new Date(2016,7,8),"Turnovo"));
        List<Person> people = tripManager.getPeopleInTheSameCity(new Date(2016,6,9),"Turnovo");

        assertThat(people, is(equalTo(Lists.newArrayList(new Person("Bob",new UID(9807236424l),(byte)18,"bob@gmail.com"), new Person("Jon",new UID(9712128833l),(byte)19,"jon@gmail.com")))));
    }
}
