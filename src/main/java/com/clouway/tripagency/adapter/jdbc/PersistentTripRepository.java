package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.Destination;
import com.clouway.tripagency.core.TripRepository;
import com.clouway.tripagency.core.UID;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentTripRepository implements TripRepository {


    private Connection connection;
    private Statement statement;

    public PersistentTripRepository(ConnectionProvider provider) {
        connection = provider.get();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long register(Destination destination) {
        try {
            statement.executeUpdate("INSERT INTO TRIP VALUES(" + destination.egn.id + ",'" + destination.arrival + "','" + destination.departing + "','" + destination.cityName + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destination.egn.id;
    }

    public void update(UID egn, Destination destination) {
        try {
            statement.executeUpdate("UPDATE TRIP SET Arrival = '" + destination.arrival + "', Department = '" + destination.departing + "', City = '" + destination.cityName + "' WHERE EGN = " + egn.id + ";");
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
