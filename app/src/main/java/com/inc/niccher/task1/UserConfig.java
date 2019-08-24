package com.inc.niccher.task1;

/**
 * Created by niccher on 04/06/19.
 */

public class UserConfig {
    String aUid, aEmail, aUsername, aPhone, aProfile, aProfilethumb;

    public UserConfig() {
    }

    public UserConfig(String aUid, String aEmail, String aUsername, String aPhone, String aProfile, String aProfilethumb) {
        this.aUid = aUid;
        this.aEmail = aEmail;
        this.aUsername = aUsername;
        this.aPhone = aPhone;
        this.aProfile = aProfile;
        this.aProfilethumb = aProfilethumb;
    }

    public String getaUid() {
        return aUid;
    }

    public void setaUid(String aUid) {
        this.aUid = aUid;
    }

    public String getaEmail() {
        return aEmail;
    }

    public void setaEmail(String aEmail) {
        this.aEmail = aEmail;
    }

    public String getaUsername() {
        return aUsername;
    }

    public void setaUsername(String aUsername) {
        this.aUsername = aUsername;
    }

    public String getaPhone() {
        return aPhone;
    }

    public void setaPhone(String aPhone) {
        this.aPhone = aPhone;
    }

    public String getaProfile() {
        return aProfile;
    }

    public void setaProfile(String aProfile) {
        this.aProfile = aProfile;
    }

    public String getaProfilethumb() {
        return aProfilethumb;
    }

    public void setaProfilethumb(String aProfilethumb) {
        this.aProfilethumb = aProfilethumb;
    }
}