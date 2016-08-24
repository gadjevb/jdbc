package com.clouway.userrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface AddressRepository {

    Integer getId();
    String getAddress();
    void setId(Integer id);
    void setAddress(String address);

}
