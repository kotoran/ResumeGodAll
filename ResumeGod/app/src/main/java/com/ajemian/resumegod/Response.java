package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public abstract class Response {
        public Response(){

        }
        public int responseCode;
        public void setResponseCode(int responseCode){
            this.responseCode = responseCode;
        }
        public int getResponseCode(){
            return this.responseCode;
        }
    }
