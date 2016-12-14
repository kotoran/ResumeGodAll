package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public class JobseekerResponse extends Response{
    private String email;
    private String first_name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getSkill_1() {
        return skill_1;
    }

    public void setSkill_1(String skill_1) {
        this.skill_1 = skill_1;
    }

    public int getSkill_1_rating() {
        return skill_1_rating;
    }

    public void setSkill_1_rating(int skill_1_rating) {
        this.skill_1_rating = skill_1_rating;
    }

    public String getSkill_2() {
        return skill_2;
    }

    public void setSkill_2(String skill_2) {
        this.skill_2 = skill_2;
    }

    public int getSkill_2_rating() {
        return skill_2_rating;
    }

    public void setSkill_2_rating(int skill_2_rating) {
        this.skill_2_rating = skill_2_rating;
    }

    public String getSkill_3() {
        return skill_3;
    }

    public void setSkill_3(String skill_3) {
        this.skill_3 = skill_3;
    }

    public int getSkill_3_rating() {
        return skill_3_rating;
    }

    public void setSkill_3_rating(int skill_3_rating) {
        this.skill_3_rating = skill_3_rating;
    }

    private String last_name;
    private String phone_number;
    private String profile_picture;
    private String skill_1;
    private int skill_1_rating;
    private String skill_2;
    private int skill_2_rating;
    private String skill_3;
    private int skill_3_rating;

    public Double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(Double location_lat) {
        this.location_lat = location_lat;
    }

    public Double getLocation_long() {
        return location_long;
    }

    public void setLocation_long(Double location_long) {
        this.location_long = location_long;
    }

    private Double location_lat;
    private Double location_long;

}
