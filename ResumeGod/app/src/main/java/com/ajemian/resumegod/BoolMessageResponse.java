package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public class BoolMessageResponse extends Response{
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private String message;
    private boolean success;
}
