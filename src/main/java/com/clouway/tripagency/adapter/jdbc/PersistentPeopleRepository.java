package com.clouway.tripagency.adapter.jdbc;

import com.clouway.connectionprovider.core.Provider;
import com.clouway.tripagency.core.PeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentPeopleRepository implements PeopleRepository {
    private Provider<Connection> provider;

    public PersistentPeopleRepository(Provider<Connection> provider) {
        this.provider = provider;
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

    public List<Person> getAll() {
        String query = "SELECT * FROM PEOPLE;";
        List<Person> personList = Lists.newArrayList();
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            return adapt(resultSet, personList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public List getPeopleByFirstLetters(String letters) {
        List<Person> personList = Lists.newArrayList();
        String selectByLetter = "SELECT * FROM PEOPLE WHERE Name::text LIKE '" + letters + "%';";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(selectByLetter)) {
            ResultSet resultSet = statement.executeQuery();
            return adapt(resultSet, personList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    private List<Person> adapt(ResultSet resultSet, List<Person> personList) {
        try {
            while (resultSet.next()) {
                UID uid = new UID(resultSet.getLong(2));
                Person person = new Person(resultSet.getString(1), uid, resultSet.getInt(3), resultSet.getString(4));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return personList;
    }
}
