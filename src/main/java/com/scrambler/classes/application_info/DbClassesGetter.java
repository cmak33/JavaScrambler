package com.scrambler.classes.application_info;

import com.scrambler.classes.db.DbQueriesExecutor;
import com.scrambler.classes.db.EncryptionDbOperations;
import com.scrambler.classes.db.ProfileDbOperations;
import com.scrambler.classes.db.models.Profile;
import com.scrambler.classes.file_operations.FileOperations;
import java.util.Properties;

public class DbClassesGetter {
    private static DbClassesGetter dbClassesGetter;
    private static final String DB_PROPERTIES_PATH = "db.properties";
    private final DbQueriesExecutor executor;
    private final ProfileDbOperations profileOperations;
    private final EncryptionDbOperations encryptionOperations;
    private Profile currentProfile;

    private DbClassesGetter(){
        Properties properties=FileOperations.loadProperties(DB_PROPERTIES_PATH);
        executor = createExecutor(properties);
        profileOperations = createProfileDbOperations(properties);
        encryptionOperations = createEncryptionDbOperations(properties);
    }


    public static DbClassesGetter getDbClassesGetter(){
        if(dbClassesGetter==null){
            dbClassesGetter = new DbClassesGetter();
        }
        return dbClassesGetter;
    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }


    public DbQueriesExecutor getExecutor(){
        return executor;
    }

    public ProfileDbOperations getProfileOperations(){
        return profileOperations;
    }

    public EncryptionDbOperations getEncryptionOperations(){
        return encryptionOperations;
    }

    private DbQueriesExecutor createExecutor(Properties properties){
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return new DbQueriesExecutor(url,username,password);
    }

    private ProfileDbOperations createProfileDbOperations(Properties properties){
        String tableName = properties.getProperty("profilesTable");
        return new ProfileDbOperations(executor,tableName);
    }

    private EncryptionDbOperations createEncryptionDbOperations(Properties properties){
        String tableName = properties.getProperty("enctyptionsTable");
        return new EncryptionDbOperations(executor,tableName);
    }
}
