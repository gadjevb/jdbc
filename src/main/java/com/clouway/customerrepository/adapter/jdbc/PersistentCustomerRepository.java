package com.clouway.customerrepository.adapter.jdbc;

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

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private List<Customer> customerList;

    public PersistentCustomerRepository(ConnectionProvider provider) {
        connection = provider.get();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void register(Customer customer){
        try {
            statement.executeUpdate("INSERT INTO CUSTOMER(Name, Age) VALUES('" + customer.name + "'," + customer.age + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, Customer customer){
        try {
            statement.executeUpdate("UPDATE CUSTOMER SET Name = '" + customer.name + "', Age = " + customer.age + " WHERE ID = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getNumberOfRecords(){
        try {
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER;");
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getHistoricalRecords(Integer page){
        customerList = new ArrayList();
        int limit = getNumberOfRecords();
        int offset = (page - 1) * getNumberOfRecords();
        try {
            resultSet = statement.executeQuery("SELECT * FROM CUSTOMER_HISTORY LIMIT " + limit + " OFFSET " + offset + ";");
            while (resultSet.next()){
                customerList.add(new Customer(resultSet.getInt(1),resultSet.getString(2),resultSet.getByte(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    public void truncate(){
        try {
            statement.executeUpdate("ALTER SEQUENCE customer_id RESTART WITH 1;");
            statement.executeUpdate("TRUNCATE TABLE CUSTOMER, CUSTOMER_HISTORY;");
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
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}