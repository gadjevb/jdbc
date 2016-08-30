package com.clouway.customerrepository;

import com.clouway.connectionprovider.adapter.jdbc.ConnectionProvider;
import com.clouway.customerrepository.adapter.jdbc.PersistentCustomerRepository;
import com.clouway.customerrepository.core.Customer;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class PersistentCustomerRepositoryTest {

    private ConnectionProvider provider = new ConnectionProvider("task4","postgres","123");
    private PersistentCustomerRepository customerRepository = new PersistentCustomerRepository(provider);
    private List<Customer> history;

    @Test
    public void happyPath(){
        customerRepository.truncate();
        customerRepository.register(new Customer(null,"Jon",18));
        customerRepository.register(new Customer(null,"Bob",19));
        customerRepository.update(1,new Customer(null,"Jon Doe",20));
        customerRepository.update(2,new Customer(null,"Bob Doe",20));
        history = customerRepository.getHistoricalRecords(1);

        assertThat(history, is(equalTo(Lists.newArrayList(new Customer(1,"Jon",18), new Customer(2,"Bob",19)))));
    }
}
