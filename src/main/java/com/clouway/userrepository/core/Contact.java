package com.clouway.userrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Contact implements ContactRepository {

    private Integer id;
    private Long gsm;

    public Contact(Integer id, Long gsm) {
        this.id = id;
        this.gsm = gsm;
    }

    public Integer getId() {
        return id;
    }

    public Long getGsm() {
        return gsm;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGsm(Long gsm) {
        this.gsm = gsm;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", gsm=" + gsm +
                '}';
    }
}
