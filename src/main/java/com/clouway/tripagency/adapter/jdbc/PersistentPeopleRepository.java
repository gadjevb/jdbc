package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.PeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentPeopleRepository implements PeopleRepository {

    private Connection connection;
    private Statement statement;

    public PersistentPeopleRepository(ConnectionProvider provider) {
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

    public void update(UID egn, Person person) {
        try {
            statement.executeUpdate("UPDATE PEOPLE SET Name = '" + person.name + "', Age = " + person.age + ", Email = '" + person.email + "' WHERE EGN = " + egn.id + ";");
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
