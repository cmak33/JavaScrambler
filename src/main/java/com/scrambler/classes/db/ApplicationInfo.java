package com.scrambler.classes.db;

import com.scrambler.classes.db.models.Profile;

public class ApplicationInfo {
    private static Profile currentProfile;
    private static final DbQueriesExecutor connector = new DbQueriesExecutor("jdbc:mysql://localhost:3306/javascrambler","root","");
    private static final ProfileDbOperations profileDbOperations = new ProfileDbOperations(connector,"profiles");
    private static final EncryptionDbOperations encryptionsDbOperations = new EncryptionDbOperations(connector,"encryptions");

    private ApplicationInfo(){}

    public static Profile getCurrentProfile() {
        return currentProfile;
    }

    public static void setCurrentProfile(Profile currentProfile) {
        ApplicationInfo.currentProfile = currentProfile;
    }

    public static DbQueriesExecutor getConnector() {
        return connector;
    }

    public static ProfileDbOperations getProfileDbOperations() {
        return profileDbOperations;
    }

    public static EncryptionDbOperations getEncryptionsDbOperations() {
        return encryptionsDbOperations;
    }
}
