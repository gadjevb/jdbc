package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.TripAgencyRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.Trip;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TripAgencyRepositoryTest {

    private List<Person> persons;
    private List<Trip> trips;
    private List<String> cities;
    private ConnectionProvider provider = new ConnectionProvider();
    private TripAgencyRepository agency = new TripAgencyRepository(provider,"task2","postgres","123");

    @Test
    public void insert() throws Exception {
        agency.deleteTableContent("trip");
        agency.deleteTableContent("people");
        agency.insertPerson("Jon",8505124562l,31,"jon@gmail.com");
        agency.insertTrip(8505124562l,"2016-05-12","2016-05-21","Burgas");
        agency.getPeopleContent();
        persons = agency.getPersonList();
        agency.getTripContent();
        trips = agency.getTripList();

        assertTrue(persons.get(0).toString().equals("Jon {age=31, email='jon@gmail.com'}"));
        assertTrue(trips.get(0).toString().equals("Trip{egn=8505124562, arrival=2016-05-12, departing=2016-05-21, cityName='Burgas'}"));
    }

    @Test
    public void update() throws Exception {
        agency.deleteTableContent("trip");
        agency.deleteTableContent("people");
        agency.insertPerson("Jon",8505124562l,31,"jon@gmail.com");
        agency.insertTrip(8505124562l,"2016-05-12","2016-05-21","Burgas");
        agency.updatePerson("Jon",8505124562l,25,"jon@gmail.com");
        agency.updateTrip(8505124562l,"2016-05-12","2016-05-21","Gabrovo");
        agency.getPeopleContent();
        persons = agency.getPersonList();
        agency.getTripContent();
        trips = agency.getTripList();

        assertTrue(persons.get(0).toString().equals("Jon {age=25, email='jon@gmail.com'}"));
        assertTrue(trips.get(0).toString().equals("Trip{egn=8505124562, arrival=2016-05-12, departing=2016-05-21, cityName='Gabrovo'}"));
    }

    @Test
    public void getPersonsByLetter() throws Exception {
        agency.deleteTableContent("trip");
        agency.deleteTableContent("people");
        agency.insertPerson("Jon",8503124562l,31,"jon@gmail.com");
        agency.insertPerson("Tom",8701234789l,29,"tom@gmail.com");
        agency.insertPerson("Ann",8509156721l,30,"ann@gmail.com");
        agency.getPeopleByFirstLetters("A");
        persons = agency.getPersonList();
        assertTrue(persons.get(0).toString().equals("Ann {age=30, email='ann@gmail.com'}"));
    }

    @Test
    public void getPeopleInTheSameCity() throws Exception {
        agency.deleteTableContent("trip");
        agency.deleteTableContent("people");
        agency.insertPerson("Jon",8503124562l,31,"jon@gmail.com");
        agency.insertPerson("Tom",8701234789l,29,"tom@gmail.com");
        agency.insertTrip(8503124562l,"2016-05-12","2016-05-21","Burgas");
        agency.insertTrip(8701234789l,"2016-05-15","2016-06-05","Burgas");
        agency.getPeopleInTheSameCity("2016-05-19", "Burgas");
        persons = agency.getPersonList();
        assertTrue(persons.get(1).toString().equals("Jon {age=31, email='jon@gmail.com'}"));
        assertTrue(persons.get(0).toString().equals("Tom {age=29, email='tom@gmail.com'}"));
    }

    @Test
    public void mostVisitedCities() throws Exception {
        agency.deleteTableContent("trip");
        agency.deleteTableContent("people");
        agency.insertPerson("Jon",8503124562l,31,"jon@gmail.com");
        agency.insertTrip(8503124562l,"2016-05-12","2016-05-21","Burgas");
        agency.insertTrip(8503124562l,"2016-05-12","2016-05-21","Burgas");
        agency.insertTrip(8503124562l,"2016-05-12","2016-05-21","Burgas");
        agency.insertTrip(8503124562l,"2016-05-12","2016-05-21","Varna");
        agency.insertTrip(8503124562l,"2016-05-12","2016-05-21","Turnovo");
        agency.insertTrip(8503124562l,"2016-05-12","2016-05-21","Turnovo");
        agency.getMostVisitedCities();
        cities = agency.getCitiesList();
        assertTrue(cities.get(0).equals("Burgas"));
        assertTrue(cities.get(1).equals("Turnovo"));
        assertTrue(cities.get(2).equals("Varna"));
    }
}
