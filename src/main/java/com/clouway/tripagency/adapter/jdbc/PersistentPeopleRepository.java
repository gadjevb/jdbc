package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.PeopleRepository;
import com.clouway.tripagency.core.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by borislav on 24.08.16.
 */
public class PersistentPeopleRepository implements PeopleRepository {

    private List<Person> personList;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public PersistentPeopleRepository(ConnectionProvider provider, String database, String username, String password) {
        connection = provider.getConnection(database,username,password);
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPerson(String name, Long egn, Integer age, String email) {
        try {
            statement.executeUpdate("INSERT INTO PEOPLE VALUES('" + name + "'," + egn + "," + age + ",'" + email + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePerson(String name, Long egn, Integer age, String email) {
        try {
            statement.executeUpdate("UPDATE PEOPLE SET Name = '" + name + "', Age = " + age + ", Email = '" + email + "' WHERE EGN = " + egn + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPeopleContent() {
        personList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT * FROM PEOPLE;");
            while (resultSet.next()) {
                Person person = new Person(resultSet.getString(1), resultSet.getLong(2), resultSet.getInt(3), resultSet.getString(4));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPeopleByFirstLetters(String letters) {
        personList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT * FROM PEOPLE WHERE Name::text LIKE '" + letters + "%';");
            while (resultSet.next()) {
                Person person = new Person(resultSet.getString(1), resultSet.getLong(2), resultSet.getInt(3), resultSet.getString(4));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPeopleInTheSameCity(String date, String city) {
        personList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT DISTINCT * FROM PEOPLE INNER JOIN TRIP ON PEOPLE.EGN = TRIP.EGN " +
                    "WHERE TRIP.Arrival <= '" + date + "' and TRIP.Department >= '" + date + "' and TRIP.City = '" + city + "';");
            while (resultSet.next()) {
                Person person = new Person(resultSet.getString(1), resultSet.getLong(2), resultSet.getInt(3), resultSet.getString(4));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTableContent() {
        try {
            statement.executeUpdate("DELETE FROM PEOPLE;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        try {
            statement.executeUpdate("DROP TABLE PEOPLE;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPersonList() {
        return personList;
    }
}
