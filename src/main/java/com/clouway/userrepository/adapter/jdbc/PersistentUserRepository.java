package com.clouway.userrepository.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.userrepository.core.Address;
import com.clouway.userrepository.core.Contact;
import com.clouway.userrepository.core.UserRepository;
import com.clouway.userrepository.core.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentUserRepository implements UserRepository {

    private ConnectionProvider provider;

    public PersistentUserRepository(Provider provider)  {
        this.provider = (ConnectionProvider) provider;
    }

    public List getUsersContent() {
        List<Users> usersList = new ArrayList();
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS;");
            while (resultSet.next()) {
                usersList.add(new Users(resultSet.getInt(1), resultSet.getString(2)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public List getContactContent() {
        List<Contact> contactList = new ArrayList();
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Contact;");
            while (resultSet.next()) {
                contactList.add(new Contact(resultSet.getInt(1), resultSet.getLong(2)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public List getAddressContent() {
        List<Address> addressList = new ArrayList();
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ADDRESS;");
            while (resultSet.next()) {
                addressList.add(new Address(resultSet.getInt(1), resultSet.getString(2)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressList;
    }
}
