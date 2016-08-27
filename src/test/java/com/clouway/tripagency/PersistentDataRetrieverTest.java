package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentDataRetriever;
import com.clouway.tripagency.core.Destination;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;
import com.google.common.collect.Lists;
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
 * Created by borislav on 27.08.16.
 */
public class PersistentDataRetrieverTest {

    private ConnectionProvider provider = new ConnectionProvider("task2","postgres","123");
    private PersistentDataRetriever retriever = new PersistentDataRetriever(provider);
    private FakePeopleRepository peopleRepository = new FakePeopleRepository();
    private FakeTripRepository tripRepository = new FakeTripRepository();


    private void truncate(){
        try {
            Connection connection = provider.get();
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE PEOPLE, TRIP;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class FakePeopleRepository{

        private Connection connection;
        private Statement statement;

        private FakePeopleRepository() {
            connection = provider.get();
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Long register(Person person) {
            try {
                statement.executeUpdate("INSERT INTO PEOPLE VALUES('" + person.name + "'," + person.egn.id + "," + person.age + ",'" + person.email + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return person.egn.id;
        }
    }

    private class FakeTripRepository{

        private Connection connection;
        private Statement statement;

        private FakeTripRepository() {
            connection = provider.get();
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Long register(Destination destination) {
            try {
                statement.executeUpdate("INSERT INTO TRIP VALUES(" + destination.egn.id + ",'" + destination.arrival + "','" + destination.departing + "','" + destination.cityName + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return destination.egn.id;
        }
    }

    @Test
    public void peopleAndTripData(){
        truncate();
        peopleRepository.register(new Person("Jon",new UID(9712128833l),19,"jon@gmail.com"));
        peopleRepository.register(new Person("Bob",new UID(9807236424l),18,"bob@gmail.com"));
        tripRepository.register(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Ruse"));
        tripRepository.register(new Destination(new UID(9807236424l), new Date(2016,3,18), new Date(2016,5,22),"Vidin"));
        List<Person> people = retriever.getPeopleData();
        List<Destination> trips = retriever.getTripData();

        assertThat(people, is(equalTo(Lists.newArrayList(new Person("Jon",new UID(9712128833l),19,"jon@gmail.com"), new Person("Bob",new UID(9807236424l),18,"bob@gmail.com")))));
        assertThat(trips, is(equalTo(Lists.newArrayList(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Ruse"), new Destination(new UID(9807236424l), new Date(2016,3,18), new Date(2016,5,22),"Vidin")))));
    }

    @Test
    public void byGivenLetters(){
        truncate();
        peopleRepository.register(new Person("Jon",new UID(9712128833l),19,"jon@gmail.com"));
        peopleRepository.register(new Person("Bob",new UID(9807236424l),18,"bob@gmail.com"));
        peopleRepository.register(new Person("Sam",new UID(9612128833l),20,"sam@gmail.com"));
        peopleRepository.register(new Person("Ben",new UID(9507236424l),21,"ben@gmail.com"));
        List<Person> people = retriever.getPeopleByFirstLetters("B");

        assertThat(people, is(equalTo(Lists.newArrayList(new Person("Bob",new UID(9807236424l),18,"bob@gmail.com"), new Person("Ben",new UID(9507236424l),21,"ben@gmail.com")))));
    }

    @Test
    public void mostVisitedCities(){
        truncate();
        peopleRepository.register(new Person("Jon",new UID(9712128833l),19,"jon@gmail.com"));
        tripRepository.register(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Vidin"));
        tripRepository.register(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Varna"));
        tripRepository.register(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Varna"));
        tripRepository.register(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Turnovo"));
        tripRepository.register(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Turnovo"));
        tripRepository.register(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Turnovo"));
        List<String> cities = retriever.getMostVisitedCities();

        assertThat(cities, is(equalTo(Lists.newArrayList("Turnovo","Varna","Vidin"))));
    }

    @Test
    public void inSameCity(){
        truncate();
        peopleRepository.register(new Person("Jon",new UID(9712128833l),19,"jon@gmail.com"));
        peopleRepository.register(new Person("Bob",new UID(9807236424l),18,"bob@gmail.com"));
        peopleRepository.register(new Person("Sam",new UID(9612128833l),20,"sam@gmail.com"));
        tripRepository.register(new Destination(new UID(9712128833l), new Date(2016,6,6), new Date(2016,6,16),"Turnovo"));
        tripRepository.register(new Destination(new UID(9807236424l), new Date(2016,6,3), new Date(2016,6,10),"Turnovo"));
        tripRepository.register(new Destination(new UID(9612128833l), new Date(2016,6,28), new Date(2016,7,8),"Turnovo"));
        List<Person> people = retriever.getPeopleInTheSameCity(new Date(2016,6,9),"Turnovo");

        assertThat(people, is(equalTo(Lists.newArrayList( new Person("Bob",new UID(9807236424l),18,"bob@gmail.com"), new Person("Jon",new UID(9712128833l),19,"jon@gmail.com")))));
    }
}
