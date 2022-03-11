package com.example.javaproject.classes.db;

import com.example.javaproject.classes.loginAndRegister.Encryption;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record EncryptionsDbOperations(DbConnector connector, String tableName) {

    public void addEncryption(Encryption encryption){
        connector.executeNonQuery(getAddEncryptionQuery(encryption));
    }

    private String getAddEncryptionQuery(Encryption encryption){
        Timestamp timestamp = Timestamp.valueOf(encryption.getPublishDateTime());;
        return String.format("INSERT INTO %s ('profileId','text','publishDate') VALUES ('%d','%s','%s')",tableName,encryption.getProfileId(),encryption.getEncryptedText(),timestamp);
    }

    public List<Encryption> getEncryptionsByProfileId(int profileId){
        ArrayList<Encryption> encryptions = new ArrayList<>();
        ResultSetOperation operation = (ResultSet resultSet) -> {
            try {
                while(resultSet.next()){
                    String text = resultSet.getString(0);
                    LocalDateTime date = resultSet.getTimestamp(1).toLocalDateTime();
                    encryptions.add(new Encryption(profileId,text,date));
                }
            } catch (SQLException exception) {
                System.err.println(exception.getMessage());
            }
        };
        String query = getEncryptionsQuery(profileId);
        connector.executeQuery(query, operation);
        return encryptions;
    }

    private String getEncryptionsQuery(int profileId){
        return String.format("SELECT text,publishDate FROM %s WHERE profileId='%s'",tableName,profileId);
    }

}
