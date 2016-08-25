package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentPeopleRepository;
import com.clouway.tripagency.adapter.jdbc.PersistentTripRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.Destination;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class DestinationAgencyRepositoryTest {

    private List<Person> tourists;
    private List<Destination> destinations;
    private List<String> cities;
    private ConnectionProvider provider = new ConnectionProvider();
    private PersistentPeopleRepository people = new PersistentPeopleRepository(provider,"task2","postgres","123");
    private PersistentTripRepository trip = new PersistentTripRepository(provider,"task2","postgres","123");

    @Test
    public void insert() {
        trip.deleteTableContent();
        people.deleteTableContent();
        people.insertPerson("Jon",8505124562l,31,"jon@gmail.com");
        trip.insertTrip(8505124562l,"2016-05-12","2016-05-21","Burgas");
        people.getPeopleContent();
        tourists = people.getPersonList();
        trip.getTripContent();
        destinations = trip.getDestinationList();

        assertTrue(tourists.get(0).toString().equals("Jon {age=31, email='jon@gmail.com'}"));
        assertTrue(destinations.get(0).toString().equals("Destination{egn=8505124562, arrival=2016-05-12, departing=2016-05-21, cityName='Burgas'}"));
    }

    @Test
    public void update() {
        trip.deleteTableContent();
        people.deleteTableContent();
        people.insertPerson("Jon",8505124562l,31,"jon@gmail.com");
        trip.insertTrip(8505124562l,"2016-05-12","2016-05-21","Burgas");
        people.updatePerson("Jon",8505124562l,25,"jon@gmail.com");
        trip.updateTrip(8505124562l,"2016-05-12","2016-05-21","Gabrovo");
        people.getPeopleContent();
        tourists = people.getPersonList();
        trip.getTripContent();
        destinations = trip.getDestinationList();

        assertTrue(tourists.get(0).toString().equals("Jon {age=25, email='jon@gmail.com'}"));
        assertTrue(destinations.get(0).toString().equals("Destination{egn=8505124562, arrival=2016-05-12, departing=2016-05-21, cityName='Gabrovo'}"));
    }

    @Test
    public void personsByLetter() {
        trip.deleteTableContent();
        people.deleteTableContent();
        people.insertPerson("Jon",8503124562l,31,"jon@gmail.com");
        people.insertPerson("Tom",8701234789l,29,"tom@gmail.com");
        people.insertPerson("Ann",8509156721l,30,"ann@gmail.com");
        people.getPeopleByFirstLetters("A");
        tourists = people.getPersonList();
        assertTrue(tourists.get(0).toString().equals("Ann {age=30, email='ann@gmail.com'}"));
    }

    @Test
    public void peopleInTheSameCity() {
        trip.deleteTableContent();
        people.deleteTableContent();
        people.insertPerson("Jon",8503124562l,31,"jon@gmail.com");
        people.insertPerson("Tom",8701234789l,29,"tom@gmail.com");
        trip.insertTrip(8503124562l,"2016-05-12","2016-05-21","Burgas");
        trip.insertTrip(8701234789l,"2016-05-15","2016-06-05","Burgas");
        people.getPeopleInTheSameCity("2016-05-19", "Burgas");
        tourists = people.getPersonList();
        assertTrue(tourists.get(1).toString().equals("Jon {age=31, email='jon@gmail.com'}"));
        assertTrue(tourists.get(0).toString().equals("Tom {age=29, email='tom@gmail.com'}"));
    }

    @Test
    public void mostVisitedCities() {
        trip.deleteTableContent();
        people.deleteTableContent();
        people.insertPerson("Jon",8503124562l,31,"jon@gmail.com");
        trip.insertTrip(8503124562l,"2016-05-12","2016-05-21","Burgas");
        trip.insertTrip(8503124562l,"2016-05-12","2016-05-21","Burgas");
        trip.insertTrip(8503124562l,"2016-05-12","2016-05-21","Burgas");
        trip.insertTrip(8503124562l,"2016-05-12","2016-05-21","Varna");
        trip.insertTrip(8503124562l,"2016-05-12","2016-05-21","Turnovo");
        trip.insertTrip(8503124562l,"2016-05-12","2016-05-21","Turnovo");
        trip.getMostVisitedCities();
        cities = trip.getCitiesList();
        assertTrue(cities.get(0).equals("Burgas"));
        assertTrue(cities.get(1).equals("Turnovo"));
        assertTrue(cities.get(2).equals("Varna"));
    }
}
