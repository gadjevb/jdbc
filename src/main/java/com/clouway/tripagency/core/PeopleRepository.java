package com.clouway.tripagency.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface PeopleRepository {

    String getName();
    Long getEgn();
    Integer getAge();
    String getEmail();
    void setAge(Integer age);
    void setEmail(String email);
    void setName(String name);
    void setEgn(Long egn);

}
