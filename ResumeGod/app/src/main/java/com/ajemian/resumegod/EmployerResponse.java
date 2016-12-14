package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public class EmployerResponse extends Response{
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    private String email;
    private String company_name;
}
