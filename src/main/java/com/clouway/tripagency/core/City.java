package com.clouway.tripagency.core;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class City {
    public final String name;

    public City(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return name.equals(city.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
