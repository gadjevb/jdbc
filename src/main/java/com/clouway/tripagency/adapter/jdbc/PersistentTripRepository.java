package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.Destination;
import com.clouway.tripagency.core.TripRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by borislav on 24.08.16.
 */
public class PersistentTripRepository implements TripRepository {

    private List<Destination> destinationList;
    private List<String> citiesList;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public PersistentTripRepository(ConnectionProvider provider, String database, String username, String password) {
        connection = provider.getConnection(database,username,password);
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTrip(Long egn, String arrival, String department, String city) {
        try {
            statement.executeUpdate("INSERT INTO TRIP VALUES(" + egn + ",'" + arrival + "','" + department + "','" + city + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTrip(Long egn, String arrival, String department, String city) {
        try {
            statement.executeUpdate("UPDATE TRIP SET Arrival = '" + arrival + "', Department = '" + department + "', City = '" + city + "' WHERE EGN = " + egn + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getTripContent() {
        destinationList = new ArrayList();
        try {
            resultSet = statement.executeQuery("SELECT * FROM TRIP;");
            while (resultSet.next()) {
                Destination destination = new Destination(resultSet.getLong(1), resultSet.getDate(2), resultSet.getDate(3), resultSet.getString(4));
                destinationList.add(destination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getMostVisitedCities() {
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
    }

    public void deleteTableContent() {
        try {
            statement.executeUpdate("DELETE FROM TRIP;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        try {
            statement.executeUpdate("DROP TABLE TRIP;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws Exception {
        if(connection != null){
            connection.close();
        }
        if(statement != null){
            statement.close();
        }
        if(resultSet != null){
            resultSet.close();
        }
    }

    public List<Destination> getDestinationList() {
        return destinationList;
    }

    public List<String> getCitiesList() {
        return citiesList;
    }
}
