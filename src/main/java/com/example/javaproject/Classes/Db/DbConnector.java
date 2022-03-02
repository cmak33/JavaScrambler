package com.example.javaproject.Classes.Db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private final String url ;
    private final String name;
    private final String password;
    private Connection connection;

    public DbConnector(String url,String name,String password){
        this.url=url;
        this.name=name;
        this.password = password;
    }

    public boolean tryOpenConnection(){
        boolean isCorrect = true;
        try {
            connection = DriverManager.getConnection(url, name, password);
        }catch(SQLException exception){
            isCorrect = false;
        }
        return isCorrect;
    }

    public boolean tryCloseConnection(){
        boolean isCorrect = connection!=null;
        if(isCorrect){
            try {
                connection.close();
            }catch(SQLException exception){
                isCorrect = false;
            }
        }
        return isCorrect;
    }

    public Connection getConnection() {
        return connection;
    }


}
