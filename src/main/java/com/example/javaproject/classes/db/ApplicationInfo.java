package com.example.javaproject.classes.db;

import com.example.javaproject.classes.loginAndRegister.Profile;

public class ApplicationInfo {
    private static Profile currentProfile;
    private static final DbConnector connector = new DbConnector("jdbc:mysql://localhost:3306/javascrambler","root","");
    private static final ProfileDbOperations profileDbOperations = new ProfileDbOperations(connector,"profiles");
    private static final EncryptionsDbOperations encryptionsDbOperations = new EncryptionsDbOperations(connector,"encryptions");

    private ApplicationInfo(){}

    public static Profile getCurrentProfile() {
        return currentProfile;
    }

    public static void setCurrentProfile(Profile currentProfile) {
        ApplicationInfo.currentProfile = currentProfile;
    }

    public static DbConnector getConnector() {
        return connector;
    }

    public static ProfileDbOperations getProfileDbOperations() {
        return profileDbOperations;
    }

    public static EncryptionsDbOperations getEncryptionsDbOperations() {
        return encryptionsDbOperations;
    }
}
