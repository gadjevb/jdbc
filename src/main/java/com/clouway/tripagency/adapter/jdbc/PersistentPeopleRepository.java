package com.clouway.tripagency.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.tripagency.core.PeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentPeopleRepository implements PeopleRepository {

    private ConnectionProvider provider;
    private Person person;
    private UID egn;
    String select = "SELECT * FROM PEOPLE;";

    public PersistentPeopleRepository(Provider provider) {
        this.provider = (ConnectionProvider) provider;
    }

    public Long register(Person person) {
        this.person = person;
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO PEOPLE VALUES('" + person.name + "'," + person.egn.id + "," + person.age + ",'" + person.email + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person.egn.id;
    }

    public void update(UID egn, Person person) {
        this.person = person;
        this.egn = egn;
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE PEOPLE SET Name = '" + person.name + "', Age = " + person.age + ", Email = '" + person.email + "' WHERE EGN = " + egn.id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List getPeopleData() {
        List<Person> personList = new ArrayList();
        ResultSet resultSet;
        UID uid;
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(select);
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
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery("SELECT * FROM PEOPLE WHERE Name::text LIKE '" + letters + "%';");
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
