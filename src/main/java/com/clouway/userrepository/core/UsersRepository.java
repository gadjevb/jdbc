package com.clouway.userrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface UsersRepository {

    Integer getId();
    String getName();
    void setId(Integer id);
    void setName(String name);

}
