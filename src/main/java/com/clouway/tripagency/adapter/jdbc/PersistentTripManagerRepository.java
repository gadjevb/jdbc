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

    private ConnectionProvider provider;

    public PersistentTripManagerRepository(Provider provider) {
        this.provider = (ConnectionProvider) provider;
    }

    public List getPeopleInTheSameCity(Date date, String city) {
        List<Person> personList = new ArrayList();
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT * FROM PEOPLE INNER JOIN TRIP ON PEOPLE.EGN = TRIP.EGN " +
                    "WHERE TRIP.Arrival <= '" + date + "' and TRIP.Department >= '" + date + "' and TRIP.City = '" + city + "';");
            while (resultSet.next()) {
                UID uid = new UID(resultSet.getLong(2));
                Person person = new Person(resultSet.getString(1), uid, resultSet.getByte(3), resultSet.getString(4));
                personList.add(person);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }
}