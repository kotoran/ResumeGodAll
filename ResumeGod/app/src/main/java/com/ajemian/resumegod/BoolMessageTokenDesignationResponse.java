package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public class BoolMessageTokenDesignationResponse extends BoolMessageResponse{
    public int getDesignation() {
        return designation;
    }

    public void setDesignation(int designation) {
        this.designation = designation;
    }

    private int designation;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
}
