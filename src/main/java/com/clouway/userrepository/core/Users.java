package com.clouway.userrepository.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class Users {

    public final Integer id;
    public final String name;

    public Users(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;

        Users users = (Users) o;

        if (!id.equals(users.id)) return false;
        return name.equals(users.name);

    }
}
