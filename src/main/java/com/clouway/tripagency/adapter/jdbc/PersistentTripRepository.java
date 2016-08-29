package com.clouway.tripagency.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.tripagency.core.City;
import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.TripRepository;
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
public class PersistentTripRepository implements TripRepository {

    private ConnectionProvider provider;
    private String select = "SELECT * FROM TRIP;";
    private String mostVisitedCities = "SELECT City FROM TRIP GROUP BY TRIP.City ORDER BY COUNT(City) DESC;";

    public PersistentTripRepository(Provider provider) {
        this.provider = (ConnectionProvider) provider;
    }

    public Long register(Trip trip) {
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO TRIP VALUES(" + trip.egn.id + ",'" + trip.arrival + "','" + trip.departing + "','" + trip.cityName + "');");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trip.egn.id;
    }

    public void update(UID egn, Trip trip) {
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE TRIP SET Arrival = '" + trip.arrival + "', Department = '" + trip.departing + "', City = '" + trip.cityName + "' WHERE EGN = " + egn.id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List getTripData() {
        List<Trip> tripList = new ArrayList();
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(select);
            while (resultSet.next()) {
                UID uid = new UID(resultSet.getLong(1));
                Trip trip = new Trip(uid, resultSet.getDate(2), resultSet.getDate(3), resultSet.getString(4));
                tripList.add(trip);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tripList;
    }

    public List getMostVisitedCities() {
        List<City> cityList = new ArrayList();
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(mostVisitedCities);
            while (resultSet.next()) {
                City city = new City(resultSet.getString(1));
                cityList.add(city);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cityList;
    }
}
