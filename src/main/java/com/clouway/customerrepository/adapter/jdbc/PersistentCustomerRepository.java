package com.clouway.customerrepository.adapter.jdbc;

import com.clouway.customerrepository.core.CustomerRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentCustomerRepository implements CustomerRepository {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public PersistentCustomerRepository(ConnectionProvider provider) {
        connection = provider.get();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String name, Integer age){
        try {
            statement.executeUpdate("INSERT INTO CUSTOMER(Name, Age) VALUES('" + name + "'," + age + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Integer id, String name, Integer age){
        try {
            statement.executeUpdate("UPDATE CUSTOMER SET Name = '" + name + "', Age = " + age + " WHERE ID = " + id + ";");
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

    public void getCustomerRecords(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM CUSTOMER;");
            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getInt(1) + " Name: " + resultSet.getString(2) + " Age: " + resultSet.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getHistoricalRecords(Integer page){
        int limit = getNumberOfRecords();
        int offset = (page - 1) * getNumberOfRecords();
        try {
            resultSet = statement.executeQuery("SELECT * FROM CUSTOMER_HISTORY LIMIT " + limit + " OFFSET " + offset + ";");
            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getInt(1) + " Name: " + resultSet.getString(2) + " Age: " + resultSet.getInt(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void truncateAllRecords(){
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