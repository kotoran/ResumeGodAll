package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public class JobSeekerDatabaseModel {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    private String email;
    private String skills;
    private String first_name;
    private String last_name;

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    private String profile_picture;

    public JobSeekerDatabaseModel(String email, String skills, String first_name, String last_name, String profile_picture){
        this.email = email;
        this.skills = skills;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_picture = profile_picture;
    }
}
