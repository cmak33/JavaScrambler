package com.example.javaproject.classes.db;

import com.example.javaproject.classes.loginAndRegister.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;

public record ProfileDbOperations(DbConnector connector,
                                  String tableName) {

    public boolean profileExists(String nickname, String password) {
        var wrapper = new Object() {
            boolean result = false;
        };
        ResultSetOperation operation = (ResultSet resultSet) -> {
            try {
                wrapper.result = resultSet.next();
            } catch (SQLException exception) {
                wrapper.result = false;
            }
        };
        String query = getProfileExistsSqlQuery(nickname, password);
        connector.executeQuery(query, operation);
        return wrapper.result;
    }

    private String getProfileExistsSqlQuery(String nickname, String password) {
        return String.format("SELECT COUNT(*) FROM %s WHERE nickname='%s' AND password='%s'", tableName, nickname, password);
    }

    public boolean isNicknameAvailable(String nickname) {
        var wrapper = new Object() {
            boolean result = false;
        };
        ResultSetOperation operation = (ResultSet resultSet) -> {
            try {
                wrapper.result = resultSet.next();
            } catch (SQLException exception) {
                wrapper.result = false;
            }
        };
        String query = getIsNicknameAvailableQuery(nickname);
        connector.executeQuery(query, operation);
        return wrapper.result;
    }

    private String getIsNicknameAvailableQuery(String nickname) {
        return String.format("SELECT COUNT(*) FROM %s WHERE nickname='%s'", tableName, nickname);
    }

    public Profile createProfile(String nickname,String password){
        connector.executeNonQuery(getCreateProfileQuery(nickname,password));
        int id = getIdByNickname(nickname);
        return  new Profile(nickname,password,id);
    }

    private String getCreateProfileQuery(String nickname,String password){
        return String.format("INSERT INTO %s (nickname,password) VALUES ('%s','%s')",tableName,nickname,password);
    }

    private int getIdByNickname(String nickname){
        var wrapper = new Object(){int id;};
        ResultSetOperation operation = (ResultSet resultSet) -> {
            try {
                resultSet.next();
                wrapper.id =resultSet.getInt(0) ;
            } catch (SQLException exception) {
                wrapper.id=-1;
            }
        };
        String query =getIdByNicknameQuery(nickname);
        connector.executeQuery(query, operation);
        return wrapper.id;
    }

    private String getIdByNicknameQuery(String nickname){
        return String.format("SELECT id FROM %s WHERE nickname='%s'",tableName,nickname);
    }

}
