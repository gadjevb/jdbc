package com.clouway.tripagency.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.TripManagerRepository;
import com.clouway.tripagency.core.UID;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentTripManagerRepository implements TripManagerRepository {

    private Provider<Connection> provider;

    public PersistentTripManagerRepository(Provider<Connection> provider) {
        this.provider = provider;
    }

    public List<Person> getPeopleInTheSameCity(Date date, String city) {
        List<Person> personList = new ArrayList();
        String peopleInTheSameCity = "SELECT DISTINCT * FROM PEOPLE INNER JOIN TRIP ON PEOPLE.EGN = TRIP.EGN " +
                "WHERE TRIP.Arrival <= '" + date + "' and TRIP.Department >= '" + date + "' and TRIP.City = '" + city + "';";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(peopleInTheSameCity)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UID uid = new UID(resultSet.getLong(2));
                Person person = new Person(resultSet.getString(1), uid, resultSet.getInt(3), resultSet.getString(4));
                personList.add(person);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }
}
