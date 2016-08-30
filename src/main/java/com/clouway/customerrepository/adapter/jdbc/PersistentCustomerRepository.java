package com.clouway.customerrepository.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.customerrepository.core.Customer;
import com.clouway.customerrepository.core.CustomerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentCustomerRepository implements CustomerRepository {

    private ConnectionProvider provider;

    public PersistentCustomerRepository(Provider provider) {
        this.provider = (ConnectionProvider) provider;
    }

    public void register(Customer customer){
        String register = "INSERT INTO CUSTOMER(Name, Age) VALUES('" + customer.name + "'," + customer.age + ");";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(register)){
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerLargeNumberOfRecords(Customer customer, Integer numberOfRecords){
        String name = customer.name;
        Byte age = customer.age;
        String register = "INSERT INTO CUSTOMER(Name, Age) VALUES('" + name + "'," + age + ");";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(register)){
            while (numberOfRecords > 0){
                statement.executeUpdate();
                numberOfRecords--;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, Customer customer){
        String update = "UPDATE CUSTOMER SET Name = '" + customer.name + "', Age = " + customer.age + " WHERE ID = " + id + ";";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(update)){
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getNumberOfRecords(){
        String numberOfRecords = "SELECT COUNT(*) FROM CUSTOMER;";;
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(numberOfRecords);
             ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getHistoricalRecords(Integer page){
        List<Customer> customerList = new ArrayList();
        int limit = getNumberOfRecords();
        int offset = (page - 1) * getNumberOfRecords();
        String select = "SELECT * FROM CUSTOMER_HISTORY LIMIT " + limit + " OFFSET " + offset + ";";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(select)) {
             ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                customerList.add(new Customer(resultSet.getInt(1),resultSet.getString(2),resultSet.getByte(3)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public void truncate(){
        String sequence = "ALTER SEQUENCE customer_id RESTART WITH 1;";
        String truncate = "TRUNCATE TABLE CUSTOMER, CUSTOMER_HISTORY;";
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sequence);
            statement.executeUpdate(truncate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}