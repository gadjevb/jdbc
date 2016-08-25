package com.clouway.customerrepository.core;

import com.clouway.customerrepository.adapter.jdbc.ConnectionProvider;
import com.clouway.customerrepository.adapter.jdbc.PersistentCustomerRepository;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Demo {

    public static void main(String[] args){

        ConnectionProvider provider = new ConnectionProvider();
        PersistentCustomerRepository customerRepository = new PersistentCustomerRepository(provider,"task4","postgres","123");

        customerRepository.truncateAllRecords();
        customerRepository.insert("Jon",21);
        customerRepository.insert("Bob",25);
        customerRepository.update(1,"Jon",25);
        customerRepository.update(2,"Bob",30);
        customerRepository.getHistoricalRecords(2);
    }

}
