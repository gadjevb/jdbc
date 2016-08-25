package com.clouway.userrepository.core;

/**
 * Created by borislav on 24.08.16.
 */
public interface UserRepository {

    void getUsersContent();
    void getContactContent();
    void getAddressContent();
    void close();

}
