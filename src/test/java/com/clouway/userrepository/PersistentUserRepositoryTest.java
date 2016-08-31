package com.clouway.userrepository;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.userrepository.adapter.jdbc.PersistentUserRepository;
import com.clouway.userrepository.core.Address;
import com.clouway.userrepository.core.Contact;
import com.clouway.userrepository.core.Users;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentUserRepositoryTest {

    private List<Users> usersList;
    private List<Contact> contactList;
    private List<Address> addressList;
    private ConnectionProvider provider = new ConnectionProvider("task3","postgres","123");
    private PersistentUserRepository userRepository = new PersistentUserRepository(provider);
    FakeDatabaseOperator operator = new FakeDatabaseOperator();

    @Before
    public void setUp() throws Exception {
        try {
            Connection connection = provider.get();
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE USERS, CONTACT, ADDRESS;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class FakeDatabaseOperator{

        private Connection connection;
        private Statement statement;

        public FakeDatabaseOperator(){
            connection = provider.get();
            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Integer registerUser(Users user){
            try {
                statement.executeUpdate("INSERT INTO Users VALUES(" + user.id + ",'" + user.name + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return user.id;
        }

        Integer registerContact(Contact contact){
            try {
                statement.executeUpdate("INSERT INTO Contact VALUES(" + contact.id + "," + contact.gsm + ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return contact.id;
        }

        Integer registerAddress(Address address){
            try {
                statement.executeUpdate("INSERT INTO Address VALUES(" + address.id + ",'" + address.address + "');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return address.id;
        }

    }

    @Test
    public void happyPath() {
        operator.registerUser(new Users(101,"Jon"));
        operator.registerUser(new Users(102,"Bob"));
        operator.registerContact(new Contact(101,"878881213"));
        operator.registerContact(new Contact(102,"123845273"));
        operator.registerAddress(new Address(101,"str.Ams №123"));
        operator.registerAddress(new Address(102,"str.Ulver №37"));
        usersList = userRepository.getUsersContent();
        contactList = userRepository.getContactContent();
        addressList = userRepository.getAddressContent();

        assertThat(usersList, is(equalTo(Lists.newArrayList(new Users(101,"Jon"), new Users(102,"Bob")))));
        assertThat(contactList, is(equalTo(Lists.newArrayList(new Contact(101,"878881213"), new Contact(102,"123845273")))));
        assertThat(addressList, is(equalTo(Lists.newArrayList(new Address(101,"str.Ams №123"), new Address(102,"str.Ulver №37")))));
    }
}
