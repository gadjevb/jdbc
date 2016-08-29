package com.clouway.customerrepository.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.customerrepository.core.Customer;
import com.clouway.customerrepository.core.CustomerRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentCustomerRepository implements CustomerRepository {

    private ConnectionProvider provider;
    private String select = "SELECT COUNT(*) FROM CUSTOMER;";
    private String sequence = "ALTER SEQUENCE customer_id RESTART WITH 1;";
    private String truncate = "TRUNCATE TABLE CUSTOMER, CUSTOMER_HISTORY;";

    public PersistentCustomerRepository(Provider provider) {
        this.provider = (ConnectionProvider) provider;
    }

    public void register(Customer customer){
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()){
            statement.executeUpdate("INSERT INTO CUSTOMER(Name, Age) VALUES('" + customer.name + "'," + customer.age + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, Customer customer){
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()){
            statement.executeUpdate("UPDATE CUSTOMER SET Name = '" + customer.name + "', Age = " + customer.age + " WHERE ID = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getNumberOfRecords(){
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(select)) {
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
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery("SELECT * FROM CUSTOMER_HISTORY LIMIT " + limit + " OFFSET " + offset + ";");
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
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sequence);
            statement.executeUpdate(truncate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}