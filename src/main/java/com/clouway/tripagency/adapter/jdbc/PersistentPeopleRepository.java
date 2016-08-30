package com.clouway.tripagency.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.tripagency.core.PeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentPeopleRepository implements PeopleRepository {

    private ConnectionProvider provider;

    public PersistentPeopleRepository(Provider provider) {
        this.provider = (ConnectionProvider) provider;
    }

    public Long register(Person person) {
        String register = "INSERT INTO PEOPLE VALUES('" + person.name + "'," + person.egn.id + "," + person.age + ",'" + person.email + "');";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(register)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person.egn.id;
    }

    public void update(UID egn, Person person) {
        String update = "UPDATE PEOPLE SET Name = '" + person.name + "', Age = " + person.age + ", Email = '" + person.email + "' WHERE EGN = " + egn.id + ";";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List getAll() {
        List<Person> personList = new ArrayList();
        ResultSet resultSet;
        UID uid;
        String selectAll = "SELECT * FROM PEOPLE;";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(selectAll)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                uid = new UID(resultSet.getLong(2));
                Person person = new Person(resultSet.getString(1), uid, resultSet.getByte(3), resultSet.getString(4));
                personList.add(person);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public List getPeopleByFirstLetters(String letters) {
        List<Person> personList = new ArrayList();
        ResultSet resultSet;
        UID uid;
        String selectByLetter = "SELECT * FROM PEOPLE WHERE Name::text LIKE '" + letters + "%';";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(selectByLetter)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                uid = new UID(resultSet.getLong(2));
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
