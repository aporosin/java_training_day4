package com.aptitude.training.jdbc;

import org.h2.jdbc.JdbcConnection;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;

public class SetupDb {

    public static final String H2_URL = "jdbc:h2:./employees";

    //public static void main(String[] args) throws SQLException {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.h2.Driver"); // magia zeby zarejestrowac drive rw systemie jdbc
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // polaczenie
        Connection singleConnection = DriverManager.getConnection(H2_URL);

        // Pula
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create(H2_URL, "", "");
        Connection connection = connectionPool.getConnection();

        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS people(id int auto_increment, name varchar(255))");

        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO people(name) VALUES (?)",
                        new String[]{"id"}); // dzikei temu mozemy wywolac generate keys ponizej
        statement.setString(1, "Ola Porosinska");
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();

        if(generatedKeys.next())
            System.out.println("Wygenerowano klucze " + generatedKeys.getLong(1));

        return connection;
    }
}
