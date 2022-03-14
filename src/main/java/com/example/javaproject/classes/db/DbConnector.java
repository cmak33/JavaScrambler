package com.example.javaproject.classes.db;


import java.sql.*;


public record DbConnector(String url, String name, String password) {

    public void executeQuery(String query, ResultSetOperation operation) {
        try (Connection connection = openConnection(); Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            operation.invoke(result);
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.err.println(exception.getMessage());
        }
    }

    public void executeNonQuery(String query) {
        try (Connection connection = openConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.err.println(exception.getMessage());
        }
    }

    private Connection openConnection() throws SQLException {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return DriverManager.getConnection(url, name, password);
    }

}
