package com.clouway.task1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Demo {

    public static void main(String args[]) throws Exception {
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/task1", "postgres", "123");
        System.out.println("Opened database successfully!");

        statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE USERS(ID SMALLINT PRIMARY KEY NOT NULL, Name VARCHAR(30) NOT NULL, Age SMALLINT NOT NULL, Status VARCHAR(20) NOT NULL);");
        statement.executeUpdate("INSERT INTO USERS VALUES(1001,'David',25,'Premium');");
        statement.executeUpdate("INSERT INTO USERS VALUES(1002,'Bob',16,'Normal');");
        statement.executeUpdate("INSERT INTO USERS VALUES(1003,'Brendon',22,'Golden');");
        statement.executeUpdate("INSERT INTO USERS VALUES(1004,'Tim',18,'Normal');");
        statement.executeUpdate("UPDATE USERS SET Status = 'Premium' WHERE ID = 1002;");
        System.out.println("Records created!");
        System.out.println();

        resultSet = statement.executeQuery("SELECT * FROM USERS WHERE Name::text LIKE 'B%';");

        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt(1));
            System.out.println("Name: " + resultSet.getString(2));
            System.out.println("Age: " + resultSet.getInt(3));
            System.out.println("Status: " + resultSet.getString(4));
            System.out.println();
        }
        System.out.println("Information received!");
        System.out.println();

        statement.executeUpdate("ALTER TABLE USERS ADD Gender char(1);");
        statement.executeUpdate("UPDATE USERS SET Gender = 'M' WHERE ID = 1001;");
        statement.executeUpdate("UPDATE USERS SET Gender = 'M' WHERE ID = 1002;");
        statement.executeUpdate("UPDATE USERS SET Gender = 'M' WHERE ID = 1003;");
        statement.executeUpdate("UPDATE USERS SET Gender = 'M' WHERE ID = 1004;");
        System.out.println("Gender information added!");
        System.out.println();

        resultSet = statement.executeQuery("SELECT * FROM USERS;");

        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt(1));
            System.out.println("Name: " + resultSet.getString(2));
            System.out.println("Age: " + resultSet.getInt(3));
            System.out.println("Status: " + resultSet.getString(4));
            System.out.println("Gender: " + resultSet.getString(5));
            System.out.println();
        }
        System.out.println("Information received!");

        statement.executeUpdate("DROP TABLE USERS;");
    }

}
