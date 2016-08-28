package com.clouway.userrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Contact {

    public final Integer id;
    public final Long gsm;

    public Contact(Integer id, Long gsm) {
        this.id = id;
        this.gsm = gsm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (id != null ? !id.equals(contact.id) : contact.id != null) return false;
        return gsm != null ? gsm.equals(contact.gsm) : contact.gsm == null;

    }
}
