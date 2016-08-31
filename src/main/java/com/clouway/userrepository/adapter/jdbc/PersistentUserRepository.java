package com.clouway.userrepository.adapter.jdbc;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.connectionprovider.core.Provider;
import com.clouway.userrepository.core.Address;
import com.clouway.userrepository.core.Contact;
import com.clouway.userrepository.core.UserRepository;
import com.clouway.userrepository.core.Users;
import com.google.common.collect.Lists;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentUserRepository implements UserRepository {

    private Provider<Connection> provider;

    public PersistentUserRepository(Provider<Connection> provider)  {
        this.provider = provider;
    }

    public List<Users> getUsersContent() {
        return get("Users");
    }

    public List<Contact> getContactContent() {
        return get("Contact");
    }

    public List<Address> getAddressContent() {
        return get("Address");
    }

    public Integer registerUser(Users user){
        String register = "INSERT INTO Users VALUES(" + user.id + ",'" + user.name + "');";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(register)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user.id;
    }

    public Integer registerContact(Contact contact){
        String register = "INSERT INTO Contact VALUES(" + contact.id + ",'" + contact.gsm + "');";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(register)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact.id;
    }

    public Integer registerAddress(Address address){
        String register = "INSERT INTO Address VALUES(" + address.id + ",'" + address.address + "');";
        try (Connection connection = provider.get();
             PreparedStatement statement = connection.prepareStatement(register)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address.id;
    }

    private List get(String tableName){
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + ";");
            if(tableName.equals("Users")){
                List<Users> list = new ArrayList();
                while (resultSet.next()) {
                    list.add(new Users(resultSet.getInt(1), resultSet.getString(2)));
                }
                return list;
            }else if(tableName.equals("Address")){
                List<Address> list = new ArrayList();
                while (resultSet.next()) {
                    list.add(new Address(resultSet.getInt(1), resultSet.getString(2)));
                }
                return list;
            }else{
                List<Contact> list = new ArrayList();
                while (resultSet.next()) {
                    list.add(new Contact(resultSet.getInt(1), resultSet.getString(2)));
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
