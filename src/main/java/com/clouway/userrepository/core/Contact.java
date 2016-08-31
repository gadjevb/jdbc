package com.clouway.userrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Contact {

    public final Integer id;
    public final String gsm;

    public Contact(Integer id, String gsm) {
        this.id = id;
        this.gsm = gsm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != null ? !id.equals(contact.id) : contact.id != null) return false;
        return gsm != null ? gsm.equals(contact.gsm) : contact.gsm == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gsm != null ? gsm.hashCode() : 0);
        return result;
    }
}
