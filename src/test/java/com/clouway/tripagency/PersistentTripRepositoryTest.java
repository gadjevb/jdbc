package com.clouway.tripagency;

import com.clouway.tripagency.adapter.jdbc.ConnectionProvider;
import com.clouway.tripagency.adapter.jdbc.PersistentTripRepository;
import com.clouway.tripagency.core.Destination;
import com.clouway.tripagency.core.Person;
import com.clouway.tripagency.core.UID;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by borislav on 27.08.16.
 */
public class PersistentTripRepositoryTest {

    private ConnectionProvider provider = new ConnectionProvider("task2","postgres","123");
    private PersistentTripRepository tripRepository = new PersistentTripRepository(provider);
    FakeDatabaseOperator database = new FakeDatabaseOperator();

    private class FakeDatabaseOperator {

        private Connection connection;
        private Statement statement;
        private ResultSet set;

        private FakeDatabaseOperator() {
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

        Destination get(Long id){
            Destination destination = null;
            try {
                set = statement.executeQuery("SELECT * FROM TRIP WHERE EGN = " + id + ";");
                if(set.next()) {
                    destination = new Destination(new UID(set.getLong(1)), set.getDate(2), set.getDate(3), set.getString(4));
                }else{
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return destination;
        }

        public Long register(Person person) {
            try {
                statement.executeUpdate("INSERT INTO PEOPLE VALUES('" + person.name + "'," + person.egn.id + "," + person.age + ",'" + person.email + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return person.egn.id;
        }
    }

    @Test
    public void happyPath(){
        database.truncate();
        database.register(new Person("Jon",new UID(9308128484l),23,"jon@gmail.com"));
        tripRepository.register(new Destination(new UID(9308128484l), new Date(2016,8,15), new Date(2016,8,20), "Burgas"));
        Destination destination = database.get(new UID(9308128484l).id);
        tripRepository.update(new UID(9308128484l) ,new Destination(new UID(9308128484l), new Date(2016,8,15), new Date(2016,8,20), "Turnovo"));
        Destination updatedDestination = database.get(new UID(9308128484l).id);

        assertTrue(destination.equals(new Destination(new UID(9308128484l), new Date(2016,8,15), new Date(2016,8,20), "Burgas")));
        assertTrue(updatedDestination.equals(new Destination(new UID(9308128484l), new Date(2016,8,15), new Date(2016,8,20), "Turnovo")));
    }
}
