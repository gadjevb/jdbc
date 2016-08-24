package com.clouway.userrepository.adapter.jdbc;

import com.clouway.userrepository.core.Address;
import com.clouway.userrepository.core.Contact;
import com.clouway.userrepository.core.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentUserRepository {

    private List<Users> usersList;
    private List<Contact> contactList;
    private List<Address> addressList;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public PersistentUserRepository(ConnectionProvider provider,String database, String username, String password)  {
        connection = provider.getConnection(database,username,password);
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getUsersContent() throws Exception {
        usersList = new ArrayList();
        resultSet = statement.executeQuery("SELECT * FROM USERS;");
        while (resultSet.next()){
            usersList.add(new Users(resultSet.getInt(1),resultSet.getString(2)));
        }
    }

    public void getContactContent() throws Exception {
        contactList = new ArrayList();
        resultSet = statement.executeQuery("SELECT * FROM Contact;");
        while (resultSet.next()){
            contactList.add(new Contact(resultSet.getInt(1),resultSet.getLong(2)));
        }
    }

    public void getAddressContent() throws Exception {
        addressList = new ArrayList();
        resultSet = statement.executeQuery("SELECT * FROM ADDRESS;");
        while (resultSet.next()){
            addressList.add(new Address(resultSet.getInt(1),resultSet.getString(2)));
        }
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }
}
