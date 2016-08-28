package com.clouway.customerrepository.adapter.jdbc;

import com.clouway.customerrepository.core.Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class ConnectionProvider implements Provider<Connection> {

    private Connection connection;
    private String database;
    private String user;
    private String password;

    public ConnectionProvider(String database, String user, String password) {
        this.database = database;
        this.user = user;
        this.password = password;
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
