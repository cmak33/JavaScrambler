package com.scrambler.classes.db;

import com.scrambler.classes.db.models.Profile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public record ProfileDbOperations(DbQueriesExecutor executor, String tableName) {
    private static final Logger LOGGER = Logger.getLogger(ProfileDbOperations.class.getName());

    public boolean isProfileExisting(String nickname, String password) {
        var wrapper = new Object() {
            boolean isExisting = false;
        };
        ResultSetOperation operation = (ResultSet resultSet) -> {
            try {
                resultSet.next();
                int profilesCount = resultSet.getInt(1);
                wrapper.isExisting = profilesCount==1 ;
            } catch (SQLException exception) {
                LOGGER.log(Level.WARNING,exception.getMessage(),exception);
            }
        };
        String query = createProfileExistenceSqlQuery(nickname, password);
        executor.executeQuery(query, operation);
        return wrapper.isExisting;
    }

    private String createProfileExistenceSqlQuery(String nickname, String password) {
        return String.format("SELECT COUNT(*) FROM %s WHERE nickname='%s' AND password='%s'", tableName, nickname, password);
    }

    public boolean isNicknameAvailable(String nickname) {
        var wrapper = new Object() {
            boolean isAvailable = false;
        };
        ResultSetOperation operation = (ResultSet resultSet) -> {
            try {
                resultSet.next();
                wrapper.isAvailable =resultSet.getInt(1)==0 ;
            } catch (SQLException exception) {
                LOGGER.log(Level.WARNING,exception.getMessage(),exception);
            }
        };
        String query = createNicknameAvailabilityQuery(nickname);
        executor.executeQuery(query, operation);
        return wrapper.isAvailable;
    }

    private String createNicknameAvailabilityQuery(String nickname) {
        return String.format("SELECT COUNT(*) FROM %s WHERE nickname='%s'", tableName, nickname);
    }

    public Profile createProfile(String nickname, String password){
        executor.executeNonQuery(createProfileCreationQuery(nickname,password));
        int id = findIdByNickname(nickname);
        return new Profile(nickname,password,id);
    }

    private String createProfileCreationQuery(String nickname, String password){
        return String.format("INSERT INTO %s (nickname,password) VALUES ('%s','%s')",tableName,nickname,password);
    }

    public int findIdByNickname(String nickname){
        var wrapper = new Object(){int id;};
        ResultSetOperation operation = (ResultSet resultSet) -> {
            try {
                resultSet.next();
                wrapper.id =resultSet.getInt(1);
            } catch (SQLException exception) {
                LOGGER.log(Level.WARNING,"no profiles with such nickname",exception);
            }
        };
        String query = createNicknameIdSearchQuery(nickname);
        executor.executeQuery(query, operation);
        return wrapper.id;
    }

    private String createNicknameIdSearchQuery(String nickname){
        return String.format("SELECT id FROM %s WHERE nickname='%s'",tableName,nickname);
    }

    public void updateProfile(int id,String newNickname,String newPassword){
        executor.executeNonQuery(createProfileUpdateQuery(id,newNickname,newPassword));
    }

    private String createProfileUpdateQuery(int id, String newNickname, String newPassword){
        return String.format("UPDATE %s SET nickname='%s',password='%s' WHERE id='%d'",tableName,newNickname,newPassword,id);
    }

}
