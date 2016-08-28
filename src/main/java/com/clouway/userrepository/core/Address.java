package com.clouway.userrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Address {

    public final Integer id;
    public final String address;

    public Address(Integer id, String address) {
        this.id = id;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address1 = (Address) o;

        if (!id.equals(address1.id)) return false;
        return address.equals(address1.address);

    }
}
