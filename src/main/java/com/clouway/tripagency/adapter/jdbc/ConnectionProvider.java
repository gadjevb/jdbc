package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ConnectionProvider implements Provider<Connection> {

    private Connection connection;

    public ConnectionProvider() {
    }

    @Override
    public Connection get() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + database, user, password);
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
