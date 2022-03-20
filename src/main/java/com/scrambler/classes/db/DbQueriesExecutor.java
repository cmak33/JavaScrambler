package com.scrambler.classes.db;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public record DbQueriesExecutor(String url, String username, String password) {
    private static final Logger LOGGER = Logger.getLogger(DbQueriesExecutor.class.getName());

    public void executeQuery(String query, ResultSetOperation operation) {
        try (Connection connection = openConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            operation.invoke(resultSet);
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING,exception.getMessage(),exception);
        }
    }

    public void executeNonQuery(String query) {
        try (Connection connection = openConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING,exception.getMessage(),exception);
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
