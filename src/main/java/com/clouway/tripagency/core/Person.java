package com.clouway.tripagency.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Person {

    public final String name;
    public final UID egn;
    public final Integer age;
    public final String email;

    public Person(String name, UID egn, Integer age, String email) {
        this.name = name;
        this.egn = egn;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", egn=" + egn +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (!name.equals(person.name)) return false;
        if (!egn.id.equals(person.egn.id)) return false;
        if (!age.equals(person.age)) return false;
        return email.equals(person.email);

    }
}
