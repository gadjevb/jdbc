package com.clouway.customerrepository;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.customerrepository.adapter.jdbc.PersistentCustomerRepository;
import com.clouway.customerrepository.core.Customer;
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
import static org.junit.Assert.assertTrue;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentCustomerRepositoryTest {

    private ConnectionProvider provider = new ConnectionProvider("task4","postgres","123");
    private PersistentCustomerRepository customerRepository = new PersistentCustomerRepository(provider);
    private List<Customer> history;

    @Before
    public void setUp() throws Exception {
        String sequence = "ALTER SEQUENCE customer_id RESTART WITH 1;";
        String truncate = "TRUNCATE TABLE CUSTOMER, CUSTOMER_HISTORY;";
        try (Connection connection = provider.get();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sequence);
            statement.executeUpdate(truncate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void happyPath(){
        customerRepository.register(new Customer(null,"Jon",18));
        customerRepository.register(new Customer(null,"Bob",19));
        customerRepository.update(1,new Customer(null,"Jon Doe",20));
        customerRepository.update(2,new Customer(null,"Bob Doe",20));
        history = customerRepository.getHistoricalRecords(1);

        assertThat(history, is(equalTo(Lists.newArrayList(new Customer(1,"Jon",18), new Customer(2,"Bob",19)))));
    }

    @Test
    public void registerLargeAmountOfData(){
        customerRepository.registerLargeNumberOfRecords(new Customer(null,"Jon",18));

        assertTrue(customerRepository.getNumberOfRecords() == 2000000);
    }
}
