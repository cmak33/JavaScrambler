package com.scrambler.classes.db;

import com.scrambler.classes.db.models.Encryption;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public record EncryptionDbOperations(DbQueriesExecutor executor, String tableName) {
    private static final Logger LOGGER = Logger.getLogger(EncryptionDbOperations.class.getName());

    public void addEncryption(Encryption encryption){
        String addQuery = createEncryptionAddQuery(encryption);
        executor.executeNonQuery(addQuery);
    }

    private String createEncryptionAddQuery(Encryption encryption){
        Timestamp timestamp = convertLocalDateTimeToTimestamp(encryption.getPublishDateTime());
        return String.format("INSERT INTO %s (profileId,text,publishDate) VALUES ('%d','%s','%s')",tableName,encryption.getProfileId(),encryption.getEncryptedText(),timestamp);
    }

    private Timestamp convertLocalDateTimeToTimestamp(LocalDateTime dateTime){
        return Timestamp.valueOf(dateTime);
    }

    public List<Encryption> getProfileEncryptionList(int profileId){
        List<Encryption> encryptionList = new ArrayList<>();
        ResultSetOperation operation = (ResultSet resultSet) -> {
            try {
                while(resultSet.next()){
                    String text = resultSet.getString(1);
                    LocalDateTime date = resultSet.getTimestamp(2).toLocalDateTime();
                    Encryption encryption = new Encryption(profileId,text,date);
                    encryptionList.add(encryption);
                }
            } catch (SQLException exception) {
                LOGGER.log(Level.WARNING,exception.getMessage(),exception);
            }
        };
        String query = createProfileEncryptionSelectQuery(profileId);
        executor.executeQuery(query, operation);
        return encryptionList;
    }

    private String createProfileEncryptionSelectQuery(int profileId){
        return String.format("SELECT text,publishDate FROM %s WHERE profileId='%d'",tableName,profileId);
    }

}
