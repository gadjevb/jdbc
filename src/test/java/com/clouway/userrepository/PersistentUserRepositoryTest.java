package com.clouway.userrepository;

import com.clouway.userrepository.adapter.jdbc.ConnectionProvider;
import com.clouway.userrepository.adapter.jdbc.PersistentUserRepository;
import com.clouway.userrepository.core.Address;
import com.clouway.userrepository.core.Contact;
import com.clouway.userrepository.core.Users;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentUserRepositoryTest {

    private Connection connection;
    private Statement statement;
    private List<Users> usersList;
    private List<Contact> contactList;
    private List<Address> addressList;
    private ConnectionProvider provider = new ConnectionProvider();
    private PersistentUserRepository userRepository = new PersistentUserRepository(provider,"task3","postgres","123");

    @Test
    public void happyPath() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/task3", "postgres", "123");
        statement = connection.createStatement();
        statement.executeUpdate("TRUNCATE TABLE Users, Contact, Address;");
        statement.executeUpdate("INSERT INTO Users VALUES(100,'Jon');");
        statement.executeUpdate("INSERT INTO Contact VALUES(100,878540123);");
        statement.executeUpdate("INSERT INTO Address VALUES(100,'str.Ams №123');");

        userRepository.getUsersContent();
        userRepository.getContactContent();
        userRepository.getAddressContent();
        usersList = userRepository.getUsersList();
        contactList = userRepository.getContactList();
        addressList = userRepository.getAddressList();
        assertTrue(usersList.get(0).toString().equals("User{id=100, name='Jon'}"));
        assertTrue(contactList.get(0).toString().equals("Contact{id=100, gsm=878540123}"));
        assertTrue(addressList.get(0).toString().equals("Address{id=100, address='str.Ams №123'}"));
    }
}
