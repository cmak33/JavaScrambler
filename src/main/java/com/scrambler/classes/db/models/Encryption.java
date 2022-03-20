package com.scrambler.classes.db.models;


import java.time.LocalDateTime;


public class Encryption {
    private final int profileId;
    private final String encryptedText;
    private final LocalDateTime publishDateTime;

    public Encryption(int profileId, String encryptedText, LocalDateTime publishDateTime){
        this.publishDateTime =publishDateTime;
        this.encryptedText=encryptedText;
        this.profileId=profileId;
    }

    public Encryption(int profileId,String encryptedText){
        publishDateTime =LocalDateTime.now();
        this.encryptedText=encryptedText;
        this.profileId=profileId;
    }

    public int getProfileId() {
        return profileId;
    }

    public String getEncryptedText() {
        return encryptedText;
    }

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

}
