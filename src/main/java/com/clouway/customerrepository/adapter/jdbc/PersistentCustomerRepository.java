package com.clouway.customerrepository.adapter.jdbc;

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

    private Provider<Connection> provider;

    public PersistentCustomerRepository(Provider<Connection> provider) {
        this.provider = provider;
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
                customerList.add(new Customer(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    //todo this is just fot the 4-th task to show how to insert large amount of records
    public void registerLargeNumberOfRecords(Customer customer){
        Integer numberOfRecords = 100;
        String register = append(customer);

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

    private String append(Customer customer){
        String name = customer.name;
        Integer age = customer.age;
        Integer append = 1;
        String register = "INSERT INTO CUSTOMER(Name, Age) VALUES('" + name + "'," + age + "),";

        while (append < 9999){
            register = register + "('" + name + "'," + age + "),";
            append++;
        }
        register = register + "('" + name + "'," + age + ");";
        register = register + register;

        return register;
    }
}