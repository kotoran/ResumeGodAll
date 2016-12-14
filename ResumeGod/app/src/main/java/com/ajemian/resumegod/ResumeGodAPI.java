package com.ajemian.resumegod;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kudo on 10/8/16.
 */

public class ResumeGodAPI {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public String getAPIAddress() {
        return APIAddress;
    }

    public void setAPIAddress(String APIAddress) {
        this.APIAddress = APIAddress;
    }

    private String APIAddress;

    public ResumeGodAPI(String APIAddress){
        this.APIAddress = APIAddress;
    }


    public void auth(final OnRequestListener listener, final String email){
        new Thread(new Runnable(){
            public void run(){
                try{
                    URL url = new URL(getAPIAddress() + "/auth/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-type", "application/json");
                    OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
                    os.write("{ \"email\" : \"" + email + "\"}");
                    os.flush();
                    os.close();


                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    int resCode = connection.getResponseCode();
                    if(resCode == 200){
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        BoolMessageTokenDesignationResponse response = gson.fromJson(sb.toString(), BoolMessageTokenDesignationResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.Auth, response);

                    }else{
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        BoolMessageResponse response = gson.fromJson(sb.toString(), BoolMessageResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.Auth, response);

                    }


                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with auth " + e.getMessage());
                }
            }
        }).start();

    }

    public void uploadImage(final OnRequestListener listener, final Bitmap image, final String email){
        new Thread(new Runnable(){
            public void run(){
                try {
                    URL url = new URL(getAPIAddress() + "/upload/?token=" + getToken() + "&email="+email);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    String boundary = "*****";
                    connection.setUseCaches(false);
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty(
                            "Content-Type", "multipart/form-data;boundary=" + boundary);
                    //connection.setFixedLengthStreamingMode(4096);
                    DataOutputStream request = new DataOutputStream(
                            connection.getOutputStream());

                    request.writeBytes("--" + boundary + "\r\n");
                    request.writeBytes("Content-Disposition: form-data; name=\"" +
                            "test" + "\"\r\n\r\n");
                    request.writeBytes("hi\r\n");
                    request.writeBytes("--" + boundary + "\r\n");
                    request.writeBytes("Content-Disposition: form-data; name=\"" +
                            "file" + "\";filename=\"" +
                            "nothing_important.png" + "\"" + "\r\n");
                    request.writeBytes("Content-Type: image/png\r\n\r\n");



                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
                    request.write(bos.toByteArray());

                    int length = bos.toByteArray().length;

                    request.writeBytes("\r\n");
                    request.writeBytes("--" +boundary + "--" + "\r\n");
                    request.flush();
                    request.close();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = new Gson();

                    int resCode = connection.getResponseCode();
                    if(resCode == 200){
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        UploadResponse response = gson.fromJson(sb.toString(), UploadResponse.class);
                        response.setResponseCode(connection.getResponseCode());
                        listener.onResponseReceived(OnRequestListener.RequestType.UploadImage, response);

                    }else{
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        BoolMessageResponse response = new BoolMessageResponse();
                        response.setResponseCode(400);
                        listener.onResponseReceived(OnRequestListener.RequestType.UploadImage, response);

                    }



                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with uploading an image " + e.getMessage());
                }
            }
        }).start();
    }

    public void createJobseekerProfile(final OnRequestListener listener, final JobseekerResponse jobseeker){
        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = new Gson();
        new Thread(new Runnable(){
            public void run(){
                try{
                    URL url = new URL(getAPIAddress() + "/jobseekers/create/?token=" + getToken());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-type", "application/json");
                    OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
                    os.write(gson.toJson(jobseeker).toString());
                    os.flush();
                    os.close();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    int resCode = connection.getResponseCode();
                    if(resCode == 200){
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        JobseekerResponse response = gson.fromJson(sb.toString(), JobseekerResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.CreateJobSeekerProfile, response);

                    }else{
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        BoolMessageResponse response = gson.fromJson(sb.toString(), BoolMessageResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.CreateJobSeekerProfile, response);

                    }


                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with creating employer " + e.getMessage());
                }
            }
        }).start();

    }

    public void createEmployerProfile(final OnRequestListener listener, final EmployerResponse employer){
        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = new Gson();
        Log.d("ResumeGod", gson.toJson(employer).toString());

        new Thread(new Runnable(){
            public void run(){
                try{
                    URL url = new URL(getAPIAddress() + "/employers/create/?token=" + getToken());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-type", "application/json");
                    OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
                    os.write(gson.toJson(employer).toString());
                    os.flush();
                    os.close();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    int resCode = connection.getResponseCode();
                    if(resCode == 200){
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        EmployerResponse response = gson.fromJson(sb.toString(), EmployerResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.CreateEmployerProfile, response);

                    }else{
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        BoolMessageResponse response = gson.fromJson(sb.toString(), BoolMessageResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.CreateEmployerProfile, response);

                    }


                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with creating employer " + e.getMessage());
                }
            }
        }).start();

    }

    public void createDesignation(final OnRequestListener listener, final String email, final int designation){
        final EmailDesignation emailDesignation = new EmailDesignation();
        emailDesignation.setEmail(email);
        emailDesignation.setDesignation(designation);
        GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = new Gson();

        new Thread(new Runnable(){
            public void run(){
                try{
                    URL url = new URL(getAPIAddress() + "/designation");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-type", "application/json");
                    OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
                    os.write(gson.toJson(emailDesignation).toString());
                    os.flush();
                    os.close();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    int resCode = connection.getResponseCode();
                    if(resCode == 200){
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        BoolMessageResponse response = gson.fromJson(sb.toString(), BoolMessageResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.CreateDesignation, response);

                    }else{
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        BoolMessageResponse response = gson.fromJson(sb.toString(), BoolMessageResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.CreateDesignation, response);

                    }


                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with creating designation " + e.getMessage());
                }
            }
        }).start();



    }
    public void getJobseekerProfile(final OnRequestListener listener, final String email){
        new Thread(new Runnable(){
            public void run(){
                try {
                    URL url = new URL(getAPIAddress() + "/jobseekers/?token=" + getToken() + "&email=" + email);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-length", "0");
                    Log.d("Bulletin API", Integer.toString(connection.getResponseCode()));
                    BufferedReader br = null;
                    if(connection.getResponseCode() == 200) {
                        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    }else {
                        br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    }
                    String readLine = null;
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    if(connection.getResponseCode() == 200){
                        JobseekerResponse response = gson.fromJson(sb.toString(), JobseekerResponse.class);
                        response.setResponseCode(connection.getResponseCode());
                        listener.onResponseReceived(OnRequestListener.RequestType.GetJobSeekerProfile, response);


                    }else{
                        BoolMessageResponse response = gson.fromJson(sb.toString(), BoolMessageResponse.class);
                        response.setResponseCode(connection.getResponseCode());
                        listener.onResponseReceived(OnRequestListener.RequestType.GetJobSeekerProfile, response);

                    }


                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with getting items " + e.getMessage());
                }
            }
        }).start();
    }

    public void getAllJobseekers(final OnRequestListener listener){
        new Thread(new Runnable(){
            public void run(){
                try {
                    URL url = new URL(getAPIAddress() + "/jobseekers/all/?token=" + getToken());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-length", "0");
                    Log.d("Bulletin API", Integer.toString(connection.getResponseCode()));
                    BufferedReader br = null;
                    if(connection.getResponseCode() == 200) {
                        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    }else {
                        br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    }
                    String readLine = null;
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    if(connection.getResponseCode() == 200){
                        JobseekerResponse[] responses = gson.fromJson(sb.toString(), JobseekerResponse[].class);

                        for(int i=0; i<responses.length; i++){
                            responses[i].setResponseCode(connection.getResponseCode());
                        }

                        listener.onResponsesReceived(OnRequestListener.RequestType.GetAllJobSeekerProfiles, connection.getResponseCode(), responses);


                    }else{
                        BoolMessageResponse response = gson.fromJson(sb.toString(), BoolMessageResponse.class);
                        response.setResponseCode(connection.getResponseCode());
                        listener.onResponseReceived(OnRequestListener.RequestType.GetAllJobSeekerProfiles, response);

                    }


                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with getting items " + e.getMessage());
                }
            }
        }).start();
    }

}


/*
new Thread(new Runnable(){
            public void run(){
                try{
                    Log.d("Bulletin API", "Logging in with " + email + " and password " + password);
                    URL url = new URL(getAPIAddress() + "/auth/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-type", "application/json");
                    OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
                    os.write("{ \"email\" : \"" + email + "\", \"password\" : \"" + password + "\"}");
                    Log.d("Bulletin API" , "Logging in with JSON: { \"email\" : \"" + email + "\", \"password\" : \"" + password + "\"}");
                    os.flush();
                    os.close();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    int resCode = connection.getResponseCode();
                    if(resCode == 200){
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        SuccessMessageTokenResponse response = gson.fromJson(sb.toString(), SuccessMessageTokenResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.Login, response);

                    }else{
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) != null){
                            sb.append(line);
                        }
                        SuccessMessageResponse response = gson.fromJson(sb.toString(), SuccessMessageResponse.class);
                        response.setResponseCode(resCode);
                        listener.onResponseReceived(OnRequestListener.RequestType.Login, response);

                    }


                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with login " + e.getMessage());
                }
            }
        }).start();

 */

/*
 new Thread(new Runnable(){
            public void run(){
                try {
                    URL url = new URL(getAPIAddress() + "/items/all/?token=" + getToken());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-length", "0");
                    Log.d("Bulletin API", Integer.toString(connection.getResponseCode()));
                    BufferedReader br = null;
                    if(connection.getResponseCode() == 200) {
                        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    }else {
                        br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    }
                    String readLine = null;
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    if(connection.getResponseCode() == 200){
                        ItemResponse[] responses = gson.fromJson(sb.toString(), ItemResponse[].class);

                        for(int i=0; i<responses.length; i++){
                            responses[i].setResponseCode(connection.getResponseCode());
                        }

                        listener.onResponsesReceived(OnRequestListener.RequestType.GetItems, connection.getResponseCode(), responses);


                    }else{
                        SuccessMessageResponse response = gson.fromJson(sb.toString(), SuccessMessageResponse.class);
                        response.setResponseCode(connection.getResponseCode());
                        listener.onResponseReceived(OnRequestListener.RequestType.GetItems, response);

                    }


                }catch(Exception e){
                    Log.d("Bulletin API", "Something went wrong with getting items " + e.getMessage());
                }
            }
        }).start();

 */