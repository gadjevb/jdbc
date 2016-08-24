package com.clouway.tripagency.adapter.jdbc;

import com.clouway.userrepository.core.Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ConnectionProvider implements Provider {

    private Connection connection;

    @Override
    public Connection getConnection(String database, String user, String password) {
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
