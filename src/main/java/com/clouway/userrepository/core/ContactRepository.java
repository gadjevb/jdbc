package com.clouway.userrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface ContactRepository {

    Integer getId();
    Long getGsm();
    void setId(Integer id);
    void setGsm(Long gsm);

}
