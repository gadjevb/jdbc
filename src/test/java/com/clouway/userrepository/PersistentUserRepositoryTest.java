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

    @Test
    public void happyPath() {
        userRepository.registerUser(new Users(103,"Jon"));
        userRepository.registerUser(new Users(104,"Bob"));
        userRepository.registerContact(new Contact(103,"878881213"));
        userRepository.registerContact(new Contact(104,"123845273"));
        userRepository.registerAddress(new Address(103,"str.Ams №123"));
        userRepository.registerAddress(new Address(104,"str.Ulver №37"));
        usersList = userRepository.getUsersContent();
        contactList = userRepository.getContactContent();
        addressList = userRepository.getAddressContent();
        List<Users> expectedUsers = Lists.newArrayList(new Users(103,"Jon"), new Users(104,"Bob"));
        List<Contact> expectedContacts = Lists.newArrayList(new Contact(103,"878881213"), new Contact(104,"123845273"));
        List<Address> expectedAddress = Lists.newArrayList(new Address(103,"str.Ams №123"), new Address(104,"str.Ulver №37"));

        assertThat(usersList, is(equalTo(expectedUsers)));
        assertThat(contactList, is(equalTo(expectedContacts)));
        assertThat(addressList, is(equalTo(expectedAddress)));
    }
}
