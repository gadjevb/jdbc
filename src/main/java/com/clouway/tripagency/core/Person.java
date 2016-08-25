package com.clouway.tripagency.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Person implements PersonRepository {

    private String name;//todo public final
    private Long egn;// wrap in UID
    private Integer age;
    private String email;

    public Person(String name, Long egn, Integer age, String email) {
        this.name = name;
        this.egn = egn;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public Long getEgn() {
        return egn;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEgn(Long egn) {
        this.egn = egn;
    }

    @Override
    public String toString() {
        return name + " {" +
                "age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
