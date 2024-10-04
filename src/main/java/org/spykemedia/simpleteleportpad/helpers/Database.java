package org.spykemedia.simpleteleportpad.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection connection;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:teleportpads.db");
        Statement statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS teleportpads (name TEXT, world TEXT, x INTEGER, y INTEGER, z INTEGER)");
        statement.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}
