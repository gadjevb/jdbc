package com.clouway.userrepository;

import com.clouway.userrepository.adapter.jdbc.ConnectionProvider;
import com.clouway.userrepository.adapter.jdbc.PersistentUserRepository;
import com.clouway.userrepository.core.Address;
import com.clouway.userrepository.core.Contact;
import com.clouway.userrepository.core.Users;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentUserRepositoryTest {

    private List<Users> usersList;
    private List<Contact> contactList;
    private List<Address> addressList;
    private ConnectionProvider provider = new ConnectionProvider();
    private PersistentUserRepository userRepository = new PersistentUserRepository(provider,"task3","postgres","123");

    @Test
    public void happyPath() throws Exception {
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
