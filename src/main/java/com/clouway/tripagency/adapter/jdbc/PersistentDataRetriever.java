package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.Destination;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentDataRetriever {

    private List<Destination> destinationList;
    private List<String> citiesList;
    private List<Person> personList;
    private Connection connection;
    private UID uid;
    private Statement statement;
    private ResultSet resultSet;

    public PersistentDataRetriever(ConnectionProvider provider) {
        connection = provider.get();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List getPeopleData() {
        personList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT * FROM PEOPLE;");
            while (resultSet.next()) {
                uid = new UID(resultSet.getLong(2));
                Person person = new Person(resultSet.getString(1), uid, resultSet.getByte(3), resultSet.getString(4));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public List getPeopleByFirstLetters(String letters) {
        personList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT * FROM PEOPLE WHERE Name::text LIKE '" + letters + "%';");
            while (resultSet.next()) {
                uid = new UID(resultSet.getLong(2));
                Person person = new Person(resultSet.getString(1), uid, resultSet.getByte(3), resultSet.getString(4));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public List getPeopleInTheSameCity(Date date, String city) {
        personList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT DISTINCT * FROM PEOPLE INNER JOIN TRIP ON PEOPLE.EGN = TRIP.EGN " +
                    "WHERE TRIP.Arrival <= '" + date + "' and TRIP.Department >= '" + date + "' and TRIP.City = '" + city + "';");
            while (resultSet.next()) {
                uid = new UID(resultSet.getLong(2));
                Person person = new Person(resultSet.getString(1), uid, resultSet.getByte(3), resultSet.getString(4));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public List getTripData() {
        destinationList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT * FROM TRIP;");
            while (resultSet.next()) {
                uid = new UID(resultSet.getLong(1));
                Destination destination = new Destination(uid, resultSet.getDate(2), resultSet.getDate(3), resultSet.getString(4));
                destinationList.add(destination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinationList;
    }

    public List getMostVisitedCities() {
        citiesList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT City FROM TRIP GROUP BY TRIP.City ORDER BY COUNT(City) DESC;");
            while (resultSet.next()) {
                String city = resultSet.getString(1);
                citiesList.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citiesList;
    }
}
