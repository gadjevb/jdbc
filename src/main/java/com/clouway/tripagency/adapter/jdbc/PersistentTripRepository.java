package com.clouway.tripagency.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.tripagency.core.City;
import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.TripRepository;
import com.clouway.tripagency.core.UID;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentTripRepository implements TripRepository {

    private ConnectionProvider provider;

    public PersistentTripRepository(Provider provider) {
        this.provider = (ConnectionProvider) provider;
    }

    public Long register(Trip trip) {
        String register = "INSERT INTO TRIP VALUES(" + trip.egn.id + ",'" + trip.arrival + "','" + trip.departing + "','" + trip.cityName + "');";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(register)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trip.egn.id;
    }

    public void update(UID egn, Trip trip) {
        String update = "UPDATE TRIP SET Arrival = '" + trip.arrival + "', Department = '" + trip.departing + "', City = '" + trip.cityName + "' WHERE EGN = " + egn.id + ";";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(update)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List getAll() {
        List<Trip> tripList = new ArrayList();
        String select = "SELECT * FROM TRIP;";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(select)) {
            ResultSet resultSet = statement.executeQuery();
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

    public List getMostVisited() {
        List<City> cityList = new ArrayList();
        String mostVisitedCities = "SELECT City FROM TRIP GROUP BY TRIP.City ORDER BY COUNT(City) DESC;";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(mostVisitedCities)) {
            ResultSet resultSet = statement.executeQuery();
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
