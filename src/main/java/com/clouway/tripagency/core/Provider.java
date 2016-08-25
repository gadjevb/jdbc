package com.clouway.tripagency.core;

import java.sql.Connection;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public interface Provider<T> {
    T get();
}
