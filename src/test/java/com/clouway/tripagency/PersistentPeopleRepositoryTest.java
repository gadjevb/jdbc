package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentPeopleRepository;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentPeopleRepositoryTest {

    private ConnectionProvider provider = new ConnectionProvider("task2","postgres","123");
    private PersistentPeopleRepository peopleRepository = new PersistentPeopleRepository(provider);
    FakeDatabaseOperator database = new FakeDatabaseOperator();

    private class FakeDatabaseOperator {

        private Connection connection;
        private Statement statement;
        private ResultSet set;

        public FakeDatabaseOperator() {
            connection = provider.get();
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        void truncate(){
            try {
                statement.executeUpdate("TRUNCATE TABLE PEOPLE, TRIP;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Person get(Long id){
            Person person = null;
            try {
                set = statement.executeQuery("SELECT * FROM PEOPLE WHERE EGN = " + id + ";");
                if(set.next()) {
                    person = new Person(set.getString(1), new UID(set.getLong(2)), set.getByte(3), set.getString(4));
                }else{
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return person;
        }
    }

    @Test
    public void happyPath(){
        database.truncate();
        peopleRepository.register(new Person("Jon", new UID(9203174579l), (byte)24, "jon@gmail.com"));
        Person person = database.get(new UID(9203174579l).id);
        peopleRepository.update(new UID(9203174579l), new Person("Jon Doe", new UID(9203174579l), (byte)24, "jon.doe@gmail.com"));
        Person updatedPerson = database.get(new UID(9203174579l).id);

        assertTrue(person.equals(new Person("Jon", new UID(9203174579l), (byte)24, "jon@gmail.com")));
        assertTrue(updatedPerson.equals(new Person("Jon Doe", new UID(9203174579l), (byte)24, "jon.doe@gmail.com")));
    }
}
