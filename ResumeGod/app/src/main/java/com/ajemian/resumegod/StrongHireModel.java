package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public class StrongHireModel {
    private String email;

    public int getStrong_hire() {
        return strong_hire;
    }

    public void setStrong_hire(int strong_hire) {
        this.strong_hire = strong_hire;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private int strong_hire;

    public StrongHireModel(String email, int strong_hire){
        this.email = email;
        this.strong_hire = strong_hire;
    }
}
