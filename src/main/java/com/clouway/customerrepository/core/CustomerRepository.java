package com.clouway.customerrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface CustomerRepository {

    void insert(String name, Integer age);
    void update(Integer id, String name, Integer age);
    Integer getNumberOfRecords();
    void getCustomerRecords();
    void getHistoricalRecords(Integer page);
    void truncateAllRecords();
    void close();

}
